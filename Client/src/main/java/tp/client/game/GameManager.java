package tp.client.game;

import java.util.*;
import tp.client.structural.*;
import tp.client.graphical.GUIManager;
import tp.client.network.NetworkManager;

/**
 * The central game logic coordinator class
 * Handles interactions between the network and GUI subsystem
 * @author anon
 *
 */
public class GameManager implements UserEventsHandler, NetworkEventsHandler{

    private GUIManager gui;
    private NetworkManager network;


    private volatile int myPID = -1;
    private volatile int currentPlayer = 0;
    private volatile GameState currentState = GameState.UNKNOWN;
    private Field[] currentMap = new Field[]{};
    private volatile Pawn[] currentDeployment = new Pawn[]{};

    private List<Step> moveInProgress;
    ///A lock on the internal map state
    public Object stateLock = new Object();

    /**
     * The normal constructor
     * @param guiman GUIManager to use
     * @param netman NetworkManager to use
     */
    public GameManager(GUIManager guiman, NetworkManager netman){
        gui = guiman;
        network = netman;
        gui.setUserEventsHandler(this);
        network.setNetworkEventsHandler(this);
    }


    /**
     * Handle a step
     * Add it to the move queue if it passes validation
     * @param movement step to process
     * @return if the step passed validation
     */
    public boolean handleMovement(Step movement){
        if (currentPlayer == myPID && currentState == GameState.INPROGRESS){
            if (moveInProgress == null){
                moveInProgress = new ArrayList<Step>();
            }
            moveInProgress.add(movement);


            if (MoveValidator.validate(moveInProgress, currentDeployment)){
                return true;
            }
            else{
                moveInProgress.remove(movement);
                return false;
            }


        }
        else{
            return false;
        }
    }

    /**
     * Start the game
     * (Pass request to the server)
     */
    public void handleGameStartReq(){
        if (currentState == GameState.READY){
            network.sendGameStart();
        }
    }

    /**
     * Commit the current list of steps and send it to the server
     * Lock player controls until a new state is received
     */
    public void handleTurnEndReq(){
        if (currentPlayer == myPID && currentState == GameState.INPROGRESS){
        	gui.disableTurn(true);
            if (moveInProgress == null){
                moveInProgress = new ArrayList<Step>();
            }
            Step[] moveToSend = moveInProgress.toArray(new Step[0]);
            network.sendMove(moveToSend);
            moveInProgress = null;
        }
    }

    /**
     * Connect to a server
     * @param addr the servers hostname/ip
     */
    public void handleServerConnReq(String addr){
        gui.setNetworkLabel("Connecting");
        network.connect(addr);
    }

    /**
     * Update game rules, map and lobby state based
     * on new information
     * Update the GUI to showcase new settings
     * @param recv the new config
     */
    public void handleNewServerCfg(ServerConfig recv){
        myPID = recv.toPlayerID;
        currentMap = recv.map;
        currentState = recv.gamestate;
        gui.setMap(currentMap);
        if (currentState == GameState.UNSTARTABLE){
            gui.disableGameStart(true);
            gui.setGameLabel("Czekam. Liczba graczy: " + recv.playersInGame);
        }
        if (currentState == GameState.READY){
            gui.disableGameStart(false);
            gui.setGameLabel("Gotowy");
        }
        if (currentState == GameState.INPROGRESS){
            gui.disableGameStart(true);
        }
    }

    /**
     * Update game state (pawn positions) based on
     * new information
     * Update the GUI
     * @param recv the new report
     */
    public void handleNewGameState(StateReport recv){
        //myPID = recv.toPlayerID;
        currentPlayer = recv.currentPlayer;

        synchronized(stateLock) {
	        gui.setPawns(recv.deployment, myPID);
	    	currentDeployment = recv.deployment;
        }


        if (recv.wonPlayer != 0){
            gui.setGameLabel("Wygrał gracz " + recv.wonPlayer);
            gui.disableTurn(true);
        }
        else{
            if (recv.currentPlayer == recv.toPlayerID){
                gui.setGameLabel("Twoja tura (Gracz " + recv.toPlayerID + ")");
                gui.disableTurn(false);
            }
            else{
                gui.setGameLabel("Tura gracza " + recv.currentPlayer);
                gui.disableTurn(true);
            }
        }
    }

    /**
     * Disconnect from the server 
     * if currently connected
     */
    public void handleServerDisconnect(){
        gui.setNetworkLabel("Disconnected");
        gui.disableTurn(true);
        currentState = GameState.UNKNOWN;
    }
    
    /**
     * Update GUI to show connected status
     */
    public void handleServerConnect(){
        gui.setNetworkLabel("Connected");
    }
    
    /**
     * Get a lock on the internal map
     */
    public Object getMapLock() {
    	return stateLock;
    }
}
