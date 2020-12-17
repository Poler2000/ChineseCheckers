package tp.client.network;

import tp.client.game.NetworkEventsHandler;
import tp.client.structural.*;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class NetworkManager {
    private NetworkEventsHandler upstream = null;
    private ConnMan sock;
    private HashMap<Integer, Field> lastrefs = new HashMap<Integer, Field>();

    public NetworkManager(){
        sock = new ConnMan(this);
    }
    
    public void setNetworkEventsHandler(NetworkEventsHandler handl) {
    	upstream = handl;
    }
    
    private void updateRefs(Field[] map) {
    	lastrefs = new HashMap<Integer, Field>();
    	for (Field elem : map) {
    		lastrefs.put(elem.id, elem);
    	}
    }

    public void sendGameStart(){
    	SetupMsg msg = new SetupMsg();
    	sock.send(msg.toString());
    }
    public void sendMove(Step[] move){
    	MoveMsg msg = new MoveMsg(move);
    	sock.send(msg.toString());
    }
    private void sendRegister() {
    	RegistrationMsg msg = new RegistrationMsg();
    	sock.send(msg.toString());
    }
    
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
    	}
    	}
    	catch (JSONException ex) {
    		
    	}
    }
    
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
    
    public void connect(String addr){
    	sock.connect(addr);
    }

}
