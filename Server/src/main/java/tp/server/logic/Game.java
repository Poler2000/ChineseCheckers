package tp.server.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tp.server.communication.*;
import tp.server.db.DBConnector;
import tp.server.map.Map;
import tp.server.map.MapFactory;
import tp.server.structural.GameState;
import tp.server.structural.Move;
import tp.server.structural.Pawn;

import java.util.ArrayList;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class Game  {
    @Value("false")
    private boolean isRunning;
    private MoveValidator moveValidator;
    private WinValidator winValidator;
    private DBConnector dbConnector;
    private Map map;
    private ArrayList<AbstractPlayer> players;
    private CommunicationCenter communicationCenter;
    private int numOfPlayers;
    private int currentPlayer = 1;
    private GameState gameState;
    private static BeanFactory beanFactory;

    /**
     * Entry point.
     * Create new Game
     * @param args
     */
    public static void main(String[] args) {
        Resource r = new ClassPathResource("spring-config.xml");
        beanFactory = new XmlBeanFactory(r);
        Game game = (Game)beanFactory.getBean("game");

        game.init();
        game.run();
    }

    public Game() {
        System.out.println(currentPlayer);
        System.out.println("Hello Game!");
        isRunning = true;
        players = new ArrayList<>();
        gameState = GameState.UNSTARTABLE;
    }

    /**
     * initialize needed components
     */
    private void init() {
        initDatabase();

        initCommunication();

        initPlayers();

        initGameInDB();

        sendServerConfig();

        moveValidator = (MoveValidator) beanFactory.getBean("mv");
    }

    private void initGameInDB() {
        dbConnector.createGame(numOfPlayers);
    }

    private void initDatabase() {
        dbConnector = new DBConnector();
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

        MapFactory mapFactory = (MapFactory) beanFactory.getBean("mapFactory");
        map = (Map) beanFactory.getBean("map");

        for(int i = 1; i <= numOfPlayers - numOfBots && i < 7; i++) {
            players.add(new Player(mapFactory.createPawns(i, numOfPlayers)));
        }

        if (numOfBots > 0) {
            players.add(new Bot(mapFactory.createPawns(numOfPlayers, numOfPlayers)));
        }

        winValidator = new WinValidator(players);
    }

    private void initCommunication() {
        communicationCenter = (CommunicationCenter) beanFactory.getBean("cc");
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
            } while (!moveValidator.validate(move));

            players.get(currentPlayer - 1).makeMove(move);
            insertMoveToDB(currentPlayer, move);
            checkForWinner();
            currentPlayer = (currentPlayer % numOfPlayers) + 1;
        }
        for (int i = 1; i <= numOfPlayers; i++) {
            communicationCenter.sendMessage(getCurrentGameInfo(i), i);
        }
    }

    private void insertMoveToDB(int currentPlayer, Move move) {
        dbConnector.addMove(currentPlayer, move);
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
     * Creates json with current game state using specified list of players
     * @param repPlayers
     * @return
     */
    private String getCurrentGameInfo(ArrayList<AbstractPlayer> repPlayers) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<Pawn> pawns = new ArrayList<Pawn>();

        for (AbstractPlayer p : repPlayers) {
            pawns.addAll(p.getPawns());
        }

        ServerMsg msg = new StateReport(currentPlayer, pawns, 0, 0);
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
        System.out.println(msg);
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
            case "replayRequest":
                sendReplayList(fromPlayer);
                break;
            case "loadReplay":
                loadReplayForPlayer(node, fromPlayer);
                break;
            default:
                break;
        }
    }

    /**
     * inits replay
     * @param node
     * @param forPlayer
     */
    private void loadReplayForPlayer(ObjectNode node, int forPlayer) {
        int id = 0;
        if (node.has("id")) {
            id =  node.get("id").asInt();
        }
        if (id < 1) {
            return;
        }

        performReplay(forPlayer, id);
    }

    /**
     * show replay to the player in 1s intervals
     * @param forPlayer
     * @param gameId
     */
    private void performReplay(int forPlayer, int gameId) {
        MapFactory mapFactory = (MapFactory) beanFactory.getBean("mapFactory");
        int playersNum = dbConnector.getGameById(gameId).players;
        Map repMap = (Map)beanFactory.getBean("map");
        ArrayList<AbstractPlayer> repPlayers = new ArrayList<>();

        for (int i = 1; i <= playersNum; i++) {
            repPlayers.add(new Player(mapFactory.createPawns(i, playersNum)));
        }
        ArrayList<Move> moves = MoveParser.parseMoves(dbConnector.getMovesForGame(gameId), repMap, repPlayers);

        int i = 1;
        try {
            communicationCenter.sendMessage(new ObjectMapper().writeValueAsString(new ServerConfig(playersNum, GameState.INPROGRESS, repMap.getFields(), forPlayer)), forPlayer);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (Move m : moves) {
            String msg = getCurrentGameInfo(repPlayers);
            communicationCenter.sendMessage(msg, forPlayer);
            repPlayers.get(i - 1).makeMove(m);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            i = (i % playersNum) + 1;
        }
        if (gameState == GameState.INPROGRESS) {
            communicationCenter.sendMessage(getCurrentGameInfo(forPlayer), forPlayer);
        }
    }




    /**
     * sends json with list of replays
     * @param forPlayer player to send list to
     */
    private void sendReplayList(int forPlayer) {
        ObjectMapper objectMapper = new ObjectMapper();

        ServerMsg msg = new ReplayList(dbConnector.getGames(), forPlayer);
        try {
            String msgString = objectMapper.writeValueAsString(msg);
            communicationCenter.sendMessage(msgString, forPlayer);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
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
        if (numOfPlayers > 6 || numOfPlayers == 5) {
            gameState = GameState.UNSTARTABLE;
        }

        for(int i = 1; i <= numOfPlayers; i++) {
            try {
                map = (Map)beanFactory.getBean("emptyMap");
                communicationCenter.sendMessage(mapper.writeValueAsString(new ServerConfig(numOfPlayers, gameState, map.getFields(), i)), i);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return current number of connected players
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }
}
