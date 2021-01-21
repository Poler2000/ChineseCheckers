package tp.client.network;

import tp.client.game.NetworkEventsHandler;
import tp.client.structural.*;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * The main network coordinator
 * mapping game logic to network actions
 * @author anon
 *
 */
public class NetworkManager {
    private NetworkEventsHandler upstream = null;
    private ConnMan sock;
    private HashMap<Integer, Field> lastrefs = new HashMap<Integer, Field>();

    /**
     * Constructor
     */
    public NetworkManager(){
        sock = new ConnMan(this);
    }
    
    /**
     * Set the object to handle network and
     * game events
     * @param handl the handler
     */
    public void setNetworkEventsHandler(NetworkEventsHandler handl) {
    	upstream = handl;
    }
    
    private void updateRefs(Field[] map) {
    	lastrefs = new HashMap<Integer, Field>();
    	for (Field elem : map) {
    		lastrefs.put(elem.id, elem);
    	}
    }

    /**
     * Send a game start request packet
     */
    public void sendGameStart(){
    	SetupMsg msg = new SetupMsg();
    	sock.send(msg.toString());
    }
    /**
     * Send a move to the server
     * @param move to send
     */
    public void sendMove(Step[] move){
    	MoveMsg msg = new MoveMsg(move);
    	sock.send(msg.toString());
    }
    /**
     * Send a registration packet
     */
    private void sendRegister() {
    	RegistrationMsg msg = new RegistrationMsg();
    	sock.send(msg.toString());
    }
    
    /**
     * Send request for available replays
     */
    public void getReplays() {
    	ReplayRequest msg = new ReplayRequest();
    	sock.send(msg.toString());
    }
    
    /**
     * Load a replay 
     * @param id replay id
     */
    public void loadReplay(Integer id) {
    	ReplayRequest msg = new ReplayRequest(id);
    	sock.send(msg.toString());
    }
    
    /**
     * Called to process a new message and
     * callback the handler
     * @param message
     */
    protected void handleIncoming(String message) {
    	try {
    	JSONObject incoming = new JSONObject(message);
    	if (incoming.has("type")) {
    		if (incoming.getString("type").equals("config")) {
    			//System.out.println("parsing config");
    			ServerConfig newConfig = ConfigParser.parse(incoming);
    			updateRefs(newConfig.map);
    			if (newConfig != null && upstream != null) {
    				upstream.handleNewServerCfg(newConfig);
    			}
    		}
    		if (incoming.getString("type").equals("gameState")) {
    			//System.out.println("parsing state");
    			StateReport newState = StateParser.parse(incoming, lastrefs);
    			if (newState != null && upstream != null) {
    				upstream.handleNewGameState(newState);
    			}
    		}
    		///Handle list of available replays
    		if (incoming.getString("type").equals("replayList")) {
    			Replay[] gotrepl = ReplayParser.parse(incoming);
    			if (gotrepl != null && upstream != null) {
    				upstream.handleReplayList(gotrepl);
    			}
    		}
    		
    	}
    	}
    	catch (JSONException ex) {
    		
    	}
    }
    
    /**
     * Process connection status change
     * and propagate it to handler
     * @param newstate
     */
    protected void connStateChanged(boolean newstate) {
    	if (newstate) {
    		sendRegister();
    		if (upstream != null) {
    			upstream.handleServerConnect();
    		}
    	}
    	else {
    		if (upstream != null) {
    			upstream.handleServerDisconnect();
    		}
    	}
    }
    
    /**
     * Connect to a server
     * @param addr hostname
     */
    public void connect(String addr){
    	sock.connect(addr);
    }

}
