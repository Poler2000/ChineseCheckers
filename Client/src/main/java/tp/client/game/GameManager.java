package tp.client.game;

import java.util.*;
import tp.client.structural.*;
import tp.client.graphical.GUIManager;
import tp.client.network.NetworkManager;


public class GameManager implements UserEventsHandler, NetworkEventsHandler{

    private GUIManager gui;
    private NetworkManager network;


    private int myPID = -1;
    private int currentPlayer = 0;
    private GameState currentState = GameState.UNKNOWN;
    private Field[] currentMap = new Field[]{};
    private Pawn[] currentDeployment = new Pawn[]{};

    private List<Step> moveInProgress;

    public GameManager(){
        gui = new GUIManager();
        network = new NetworkManager(this);
    }

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

    public void handleGameStartReq(){
        if (currentState == GameState.READY){
            network.sendGameStart();
        }
    }

    public void handleTurnEndReq(){
        if (currentPlayer == myPID && currentState == GameState.INPROGRESS){
            if (moveInProgress == null){
                moveInProgress = new ArrayList<Step>();
            }
            Step[] moveToSend = moveInProgress.toArray(new Step[0]);
            network.sendMove(moveToSend);
        }
    }

    public void handleServerConnReq(String addr){
        gui.setNetworkLabel("Connecting");
        network.connect(addr);
    }

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

    public void handleNewGameState(StateReport recv){
        myPID = recv.toPlayerID;
        currentPlayer = recv.currentPlayer;
        currentDeployment = recv.deployment;
        gui.setPawns(currentDeployment);

        if (recv.wonPlayer != 0){
            gui.setGameLabel("Wygrał gracz " + recv.wonPlayer);
            gui.disableTurn(true);
        }
        else{
            if (recv.currentPlayer == recv.toPlayerID){
                gui.setGameLabel("Twoja tura");
                gui.disableTurn(false);
            }
            else{
                gui.setGameLabel("Tura gracza " + recv.currentPlayer);
                gui.disableTurn(true);
            }
        }
    }

    public void handleServerDisconnect(){
        gui.setNetworkLabel("Disconnected");
        gui.disableTurn(true);
        currentState = GameState.UNKNOWN;
    }
    
    public void handleServerConnect(){
        gui.setNetworkLabel("Connected");
    }
}
