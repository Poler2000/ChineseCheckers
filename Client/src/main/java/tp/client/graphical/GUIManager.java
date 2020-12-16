package tp.client.graphical;


import java.util.*;
import javax.swing.JFrame;

import tp.client.game.UserEventsHandler;
import tp.client.structural.*;

import java.awt.BorderLayout;
import java.awt.Color;

public class GUIManager implements PawnMovementHandler{

    private Map<FieldGUI, Field> tileMap;
    private Map<Field, FieldGUI> tileMapRev;
    private Map<PawnGUI, Pawn> pawnMap;
    private BoardGUI mainView;
    private JFrame mainWindow;
    private UserEventsHandler upstream;
    private ActionsGUI userBar;
    private NetworkGUI networkBar;
    
    private Object pawnLock = new Object();
    private Object tileLock = new Object();



    public GUIManager(){
        mainWindow = new JFrame("Chineese checkers");
        mainWindow.setSize(1000,1000);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);

        mainView = new BoardGUI(new FieldGUI[]{}, 1);
        mainView.setPawns(new PawnGUI[]{});
        mainWindow.add(mainView, BorderLayout.CENTER);

        userBar = new ActionsGUI(this);
        mainWindow.add(userBar, BorderLayout.PAGE_START);

        networkBar = new NetworkGUI(this);
        mainWindow.add(networkBar, BorderLayout.PAGE_END);


        mainWindow.revalidate();
        mainWindow.repaint();
    }

    private double getOrthoX(int cubic[]){
        return (Math.sqrt(3)*(cubic[0] + (cubic[2]/2.0)));
    }

    private double getOrthoY(int cubic[]){
        return (1.5 * cubic[2]);
    }

    public void setMap(Field[] map){
    	synchronized(tileLock) {
	        tileMap = new HashMap<FieldGUI, Field>();
	        tileMapRev = new HashMap<Field, FieldGUI>();
	        for (Field tile : map){
	            FieldGUI adapted = new FieldGUI(getOrthoX(tile.coordinates),getOrthoY(tile.coordinates));
	            tileMap.put(adapted, tile);
	            tileMapRev.put(tile,adapted);
	        }
    	}

        int maxspan = 1;
        for (Field f : tileMap.values()){
            for (int i = 0; i < 3; i++){
                maxspan = f.coordinates[i] > maxspan ? f.coordinates[i] : maxspan;
                maxspan = -f.coordinates[i] > maxspan ? -f.coordinates[i] : maxspan;
            }

        }
        maxspan += 2;

        BoardGUI board = new BoardGUI(tileMap.keySet().toArray(new FieldGUI[tileMap.keySet().size()]), maxspan);
        board.setPawns(new PawnGUI[]{});
        mainWindow.remove(mainView);
        mainView = board;
        mainView.setMovementHandler(this);
        mainWindow.add(mainView);
        mainWindow.revalidate();
        mainWindow.repaint();

    }

    public void setPawns(Pawn[] pawns, int myPID){
        PawnGUI[] ret = new PawnGUI[pawns.length];
    	synchronized(pawnLock) {
    		synchronized(tileLock) {
		        pawnMap = new HashMap<PawnGUI, Pawn>();
		        for(int i = 0; i < pawns.length; i++){
		            PawnGUI temp = new PawnGUI(tileMapRev.get(pawns[i].location).getAdjustedX(1), tileMapRev.get(pawns[i].location).getAdjustedY(1));
		            temp.setColor(Color.getHSBColor((0.37f * pawns[i].playerid), 1.0f, 1.0f));
		            if (myPID != pawns[i].playerid) {
		            	temp.untouchable = true;
		            }
		            pawnMap.put(temp,pawns[i]);
		            ret[i] = temp;
		        }
    		}
    	}
        mainView.setPawns(ret);
    }

    public void setUserEventsHandler(UserEventsHandler han){
        upstream = han;
    }

    
    public boolean handlePawnMovement(PawnGUI piece, FieldGUI dest){
        if (upstream != null){
            Step newSte = new Step();
            synchronized(upstream.getMapLock()) {
	        	synchronized(pawnLock) {
	        		synchronized(tileLock) {
			            newSte.actor =pawnMap.get( piece);
			            newSte.destination = tileMap.get(dest);
			            if (newSte.actor != null && newSte.destination != null) {
			            	return upstream.handleMovement(newSte);
			            }
			            else {
			            	return false;
			            }
	        		}
	        	}
            }
        }
        else{
            return true;
        }
    }

    public void handleStartGame(){
        if (upstream != null){
            upstream.handleGameStartReq();
        }
    }

    public void handleEndTurn(){
        if (upstream != null){
            upstream.handleTurnEndReq();
        }
    }

    public void handleConnectServer(String addr){
        if (upstream != null){
            upstream.handleServerConnReq(addr);
        }
    }

    public void disableTurn(boolean iff){
        mainView.pickUpDisabled = iff;
        userBar.enableEndTurn(!iff);
    }

    public void disableGameStart(boolean iff){
        userBar.enableStartGame(!iff);
    }

    public void setGameLabel(String text){
        userBar.setLabel(text);
    }

    public void setNetworkLabel(String text){
        networkBar.setConnStatus(text);
    }
}
