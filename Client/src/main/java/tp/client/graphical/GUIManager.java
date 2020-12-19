package tp.client.graphical;


import java.util.*;
import javax.swing.JFrame;

import tp.client.game.UserEventsHandler;
import tp.client.structural.*;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 * The main GUI subsystem access class
 * Represents the entire client's window
 * @author anon
 *
 */
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

    private GUICreator creator;

    /**
     * The default constructor
     * @param arg the helper to use for creating children and the window
     */
    public GUIManager(GUICreator arg){
    	creator = new GUICreator();
    	if (arg != null) {
    		creator = arg;
    	}
    	
        mainWindow = creator.createWindow("Chineese checkers");
        mainWindow.setSize(1000,1000);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);

        mainView = creator.createBoard(new FieldGUI[]{}, 1);
        mainView.setPawns(new PawnGUI[]{});
        mainWindow.add(mainView, BorderLayout.CENTER);

        userBar = creator.createActions(this);
        mainWindow.add(userBar, BorderLayout.PAGE_START);

        networkBar = creator.createNetwork(this);
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

    /**
     * Set the game map in the play area
     * @param map the tiles making up the map
     */
    public void setMap(Field[] map){
        FieldGUI[] newmapgui = new FieldGUI[map.length];
    	synchronized(tileLock) {
	        tileMap = new HashMap<FieldGUI, Field>();
	        tileMapRev = new HashMap<Field, FieldGUI>();
	        for (int i = 0; i < map.length; i++){
	        	Field tile = map[i];
	            FieldGUI adapted = new FieldGUI(getOrthoX(tile.coordinates),getOrthoY(tile.coordinates));
	            tileMap.put(adapted, tile);
	            tileMapRev.put(tile,adapted);
	            newmapgui[i] = adapted;
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

        BoardGUI board = creator.createBoard(newmapgui, maxspan);
        board.setPawns(new PawnGUI[]{});
        mainWindow.remove(mainView);
        mainView = board;
        mainView.setMovementHandler(this);
        mainWindow.add(mainView);
        mainWindow.revalidate();
        mainWindow.repaint();

    }

    /**
     * Set the pawns currently in play
     * @param pawns the pawns
     * @param myPID your player id (other players' pawns get locked from interaction)
     */
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

    /**
     * Set user events handler
     * handleMovement pawn movement
     * handleGameStartReq game start
     * handleTurnEndReq turn end
     * handleServerConnReq server connect
     * getMapLock - lock to use when processing movement
     * @param han
     */
    public void setUserEventsHandler(UserEventsHandler han){
        upstream = han;
    }

    /**
     * Handle moves from BoardGUI and bubble them up
     */
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

    /**
     * Bubble up game start requests
     */
    public void handleStartGame(){
        if (upstream != null){
            upstream.handleGameStartReq();
        }
    }
    /**
     * Bubble up turn end requests
     */
    public void handleEndTurn(){
        if (upstream != null){
            upstream.handleTurnEndReq();
        }
    }
    /**
     * Bubble up server connect requests
     */
    public void handleConnectServer(String addr){
        if (upstream != null){
            upstream.handleServerConnReq(addr);
        }
    }

    /**
     * Disable user game interaction
     * @param iff disable/enable
     */
    public void disableTurn(boolean iff){
        mainView.pickUpDisabled = iff;
        userBar.enableEndTurn(!iff);
    }

    /**
     * Disable game start button
     * @param iff disable?
     */
    public void disableGameStart(boolean iff){
        userBar.enableStartGame(!iff);
    }

    /**
     * Set game status label
     * @param text text to display
     */
    public void setGameLabel(String text){
        userBar.setLabel(text);
    }

    /**
     * Set network status label
     * @param text text to display
     */
    public void setNetworkLabel(String text){
        networkBar.setConnStatus(text);
    }
}
