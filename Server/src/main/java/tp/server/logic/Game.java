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
    private WinValidator winValidator;
    private ArrayList<AbstractPlayer> players;
    private CommunicationCenter communicationCenter;
    private Map map;
    private int numOfPlayers;
    private int currentPlayer = 1;
    private GameState gameState;

    /**
     * Entry point.
     * Create new Game
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game();

        game.init();
        game.run();
    }

    public Game() {
        isRunning = true;
        players = new ArrayList<>();
        gameState = GameState.UNSTARTABLE;
    }

    /**
     * initialize needed components
     */
    private void init() {
        initCommunication();

        initPlayers();

        sendServerConfig();

        moveValidator = new MoveValidator(map);
    }

    /**
     * sends current server configuration info to all players
     */
    private void sendServerConfig() {
        for(int i = 1; i <= numOfPlayers; i++) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                communicationCenter.sendMessage(mapper.writeValueAsString(new ServerConfig(numOfPlayers, gameState, map.getFields(), i)), i);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates players that should controlled by clients.
     * Bots are not supported currently, but can be added
     */
    private void initPlayers() {
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

        winValidator = new WinValidator(players);
    }

    private void initCommunication() {
        try {
            communicationCenter = new CommunicationCenter(1410, this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Game's main loop.
     * Handles moves of players respectively to their
     * turn order and checks if there is a winner.
     */
    private void run() {
        while(isRunning) {
            Move move = null;
            do {
                for (int i = 1; i <= numOfPlayers; i++) {
                    communicationCenter.sendMessage(getCurrentGameInfo(i), i);
                }
                move = players.get(currentPlayer - 1).proposeMove();
            } while (!moveValidator.Validate(move));

            players.get(currentPlayer - 1).makeMove(move);
            checkForWinner();
            currentPlayer = (currentPlayer % numOfPlayers) + 1;
        }
        for (int i = 1; i <= numOfPlayers; i++) {
            communicationCenter.sendMessage(getCurrentGameInfo(i), i);
        }
    }

    /**
     * checks if there is a winner and, if so, ends game;
     */
    private void checkForWinner() {
        isRunning = !winValidator.isThereWinner();
    }

    /**
     * Creates json with current game state for specified player
     * @param playerId
     * @return
     */
    private String getCurrentGameInfo(int playerId) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<Pawn> pawns = new ArrayList<Pawn>();

        for (AbstractPlayer p : players) {
            pawns.addAll(p.getPawns());
        }

        ServerMsg msg = new StateReport(currentPlayer, pawns, winValidator.getWinner(), playerId);
        try {
            return objectMapper.writeValueAsString(msg);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Responsible for dealing with messages from client
     * @param msg message received
     * @param fromPlayer specify who sent the message
     */
    public void processMessage(final String msg, final int fromPlayer) {
        ObjectNode node = null;
        String type = null;

        if (msg.equals("")) {
            return;
        }
        try {
            node = new ObjectMapper().readValue(msg, ObjectNode.class);
            if (node.has("type")) {
                type =  node.get("type").asText();
            }
            else {
                return;
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        switch (type) {
            case "registerMsg":
                handleRegister(mapper);
                break;
            case "setupMsg":
                handleSetup(node);
                break;
            case "playerMove":
                handleMove(fromPlayer, node);
                break;
            default:
                break;
        }
    }

    /**
     * sets received move as client's proposal
     * @param fromPlayer specify, which player sent the message
     * @param node
     */
    private void handleMove(final int fromPlayer, final ObjectNode node) {
        ClientMessageParser parser = new ClientMessageParser();
        if (node.has("steps")) {;
            Move move = parser.getMove(node.get("steps"), map, players.get(fromPlayer - 1).getPawns());
            players.get(fromPlayer - 1).setMove(move);
        }
    }

    /**
     * handles request to start game
     * @param node
     */
    private void handleSetup(final ObjectNode node) {
        int state = 0;
        if (node.has("setState")) {
            state = node.get("setState").asInt();
        }
        gameState = GameState.fromInt(state);
        communicationCenter.stopListeningForNewClients();
    }

    /**
     * Registers new user and sends info about current server config
     * @param mapper
     */
    private void handleRegister(final ObjectMapper mapper) {
        numOfPlayers++;

        if (numOfPlayers > 1) {
            gameState = GameState.READY;
        }
        if (numOfPlayers > 6) {
            gameState = GameState.UNSTARTABLE;
        }

        for(int i = 1; i <= numOfPlayers; i++) {
            try {
                MapFactory mapFactory = new SixPointedStarFactory();
                map = mapFactory.createEmptyMap();
                communicationCenter.sendMessage(mapper.writeValueAsString(new ServerConfig(numOfPlayers, gameState, map.getFields(), i)), i);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
