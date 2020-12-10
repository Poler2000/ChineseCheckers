package tp.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Game
{
    enum gameStates {
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
       /* ObjectMapper objectMapper = new ObjectMapper();
        Field field1 = new Field(1,2,3,1,3,true);
        Field field2 = new Field(-1,7,5,1,4,false);

        String json1 = null;
        String json2 = null;
        try {
            json1 = objectMapper.writeValueAsString(field1);
            json2 = objectMapper.writeValueAsString(field2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json1);
        System.out.println(json2);*/

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
        map = new Map();
        map.createMap();
        numOfPlayers = communicationCenter.establishConnections();

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
                    players.add(new Player());
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
