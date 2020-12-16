package tp.server.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import tp.server.communication.*;
import tp.server.map.Map;
import tp.server.map.MapFactory;
import tp.server.map.SixPointedStarFactory;
import tp.server.structural.GameState;
import tp.server.structural.Move;
import tp.server.structural.Pawn;

import java.io.IOException;
import java.util.ArrayList;

public class Game  {
    private boolean isRunning;
    private MoveValidator moveValidator;
    private ArrayList<AbstractPlayer> players;
    private CommunicationCenter communicationCenter;
    private Map map;
    private int numOfPlayers;
    private int currentPlayer = 1;
    private GameState gameState;


    public static void main(String[] args) {
        Game game = new Game();

        game.init();
        game.run();
        game.end();
    }

    public Game() {
        isRunning = true;
        players = new ArrayList<>();
        gameState = GameState.UNSTARTABLE;
    }

    private void init() {

        try {
            communicationCenter = new CommunicationCenter(1410, this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        int numOfBots = 0;
        numOfPlayers = communicationCenter.establishConnections();

        MapFactory mapFactory = new SixPointedStarFactory();
        map = mapFactory.createMap(numOfPlayers);

        for(int i = 1; i <= numOfPlayers - numOfBots && i < 7; i++) {
            players.add(new Player(mapFactory.createPawns(i, numOfPlayers)));
        }

        if (numOfBots > 0) {
            players.add(new Bot(mapFactory.createPawns(numOfPlayers, numOfPlayers)));
        }

        moveValidator = new MoveValidator(map);
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

        ServerMsg msg = new StateReport(currentPlayer, pawns, 0, 1);
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

    public void processMessage(final String msg, final int fromPlayer) {
        ObjectNode node = null;
        String type = null;
        try {
            node = new ObjectMapper().readValue(msg, ObjectNode.class);
            if (node.has("msgType")) {
                type =  node.get("msgType").asText();
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        switch (type) {
            case "registerMsg":
                numOfPlayers++;

                for(int i = 1; i <= numOfPlayers; i++) {
                    try {
                        communicationCenter.sendMessage(mapper.writeValueAsString(new ServerConfig(numOfPlayers, gameState, map, i)), i);
                    }
                    catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "setupMsg":
                int state = -1;
                if (node.has("setState")) {
                    state = node.get("setState").asInt();
                }
                gameState = GameState.fromInt(state);
                communicationCenter.stopListeningForNewClients();
                break;
            case "playerMove":
                ClientMessageParser parser = new ClientMessageParser();
                Move move = parser.getMove(node.get("steps").asText());
                players.get(fromPlayer).setMove(move);
                break;
            default:
                break;
        }
    }
}
