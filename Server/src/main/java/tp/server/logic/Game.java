package tp.server.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import tp.server.*;
import tp.server.communication.CommunicationCenter;
import tp.server.communication.GameStateMsg;
import tp.server.communication.ServerMsg;
import tp.server.structural.Move;
import tp.server.structural.Pawn;

import java.io.IOException;
import java.util.ArrayList;

public class Game
{
    public enum gameStates {
        NO_PLAYERS,
        READY_TO_START,
        ONGOING,
        ENDED
    }
    private boolean isRunning;
    private MoveValidator moveValidator;
    private ArrayList<AbstractPlayer> players;
    private CommunicationCenter communicationCenter;
    private Map map;
    private int numOfPlayers;
    private int currentPlayer = 1;
    private gameStates gameState = gameStates.NO_PLAYERS;


    public static void main(String[] args)
    {
        Game game = new Game();

        game.init();
        game.run();
        game.end();
    }

    private void init() {

        try
        {
            communicationCenter = new CommunicationCenter(5000, this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        int numOfBots = 0;
        numOfPlayers = communicationCenter.establishConnections();
        if (numOfPlayers == 1 || numOfPlayers == 5) {
            numOfPlayers++;
            numOfBots++;
        }
        MapFactory mapFactory = new SixPointedStarFactory();
        map = mapFactory.createMap(numOfPlayers);
        for(int i = 1; i <= numOfPlayers - numOfBots; i++) {
            players.add(new Player(mapFactory.createPawns(i, numOfPlayers)));
        }
        if (numOfBots > 0) {
            players.add(new Bot(mapFactory.createPawns(numOfPlayers, numOfPlayers)));
        }

    }

    private void run() {
        while(isRunning) {
            Move move = null;
            do {
                communicationCenter.sendMessageToAll(getCurrentGameInfo());
                move = players.get(currentPlayer).proposeMove();
            } while (!moveValidator.Validate(move));
            players.get(currentPlayer).makeMove(move);
            checkForWinner();
            currentPlayer = (currentPlayer % numOfPlayers) + 1;
        }
    }

    private String getCurrentGameInfo() {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<Pawn> pawns = new ArrayList<Pawn>();

        for (AbstractPlayer p : players) {
            pawns.addAll(p.getPawns());
        }

        ServerMsg msg = new GameStateMsg(currentPlayer, pawns);
        try
        {
            return objectMapper.writeValueAsString(msg);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkForWinner() {
    }

    // TODO implement
    private void end() {
    }

    public void processMessage(final String msg) {


        final ObjectNode node;
        String type = null;
        try {
            node = new ObjectMapper().readValue(msg, ObjectNode.class);
            if (node.has("type")) {
                type =  node.get("type").asText();
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        switch (type) {
            case "registerMsg":
                if (gameState == gameStates.NO_PLAYERS) {
                    gameState = gameStates.READY_TO_START;
                }
                break;
            case "setupMsg":
                gameState = gameStates.ONGOING;
                communicationCenter.stopListeningForNewClients();
                break;
            case "playerMove":
                break;
            default:
                break;
        }

    }
}