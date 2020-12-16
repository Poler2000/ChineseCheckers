package tp.client.network;

import tp.client.game.NetworkEventsHandler;
import tp.client.structural.*;
import org.json.JSONObject;
import java.util.HashMap;

public class NetworkManager {
    private NetworkEventsHandler upstream;
    private ConnMan sock;
    private HashMap<Integer, Field> lastrefs = new HashMap<Integer, Field>();

    public NetworkManager(NetworkEventsHandler handl){
        upstream = handl;
        sock = new ConnMan(this);
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
    	JSONObject incoming = new JSONObject(message);
    	if (incoming.has("type")) {
    		if (incoming.getString("type").equals("config")) {
    			//System.out.println("parsing config");
    			ServerConfig newConfig = ConfigParser.parse(incoming);
    			updateRefs(newConfig.map);
    			upstream.handleNewServerCfg(newConfig);
    		}
    		if (incoming.getString("type").equals("gameState")) {
    			//System.out.println("parsing state");
    			StateReport newState = StateParser.parse(incoming, lastrefs);
    			upstream.handleNewGameState(newState);
    		}
    	}
    }
    
    protected void connStateChanged(boolean newstate) {
    	if (newstate) {
    		sendRegister();
    		upstream.handleServerConnect();
    	}
    	else {
    		upstream.handleServerDisconnect();
    	}
    }
    
    public void connect(String addr){
    	sock.connect(addr);
    }

}
