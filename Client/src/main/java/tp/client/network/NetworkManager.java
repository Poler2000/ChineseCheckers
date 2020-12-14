package tp.client.network;

import tp.client.game.NetworkEventsHandler;
import tp.client.structural.*;
import java.io.*;
import java.net.*;

public class NetworkManager {
    private NetworkEventsHandler upstream;
    private volatile PrintWriter netOut = null;

    public NetworkManager(NetworkEventsHandler handl){
        upstream = handl;
    }

    public void sendGameStart(){

    }
    public void sendMove(Step[] move){

    }
    
    protected void handleIncoming(String message) {
    	
    }
    
    protected boolean sendMessage(String message) {
    	if (netOut != null) {
    		netOut.print(message);
    		netOut.flush();
    	}
    	return false;
    }
    public boolean connect(String addr){
    	if (netOut != null) {
    		netOut.close();
    	}
    	try (
			Socket serverSock = new Socket(addr, 1410);
			BufferedReader netIn = new BufferedReader(new InputStreamReader(serverSock.getInputStream()));
    			
		){
			netOut = new PrintWriter(serverSock.getOutputStream(), true);
    		(new Thread() {
    			public void run() {
    				String completeMessage = "";
    				String inputReceived;
    				try {
    				while ((inputReceived = netIn.readLine()) != null) {
    					if (inputReceived.equals("MessageTerminated")) {
    						handleIncoming(completeMessage);
    						completeMessage = "";
    					}
    					else {
    						completeMessage += inputReceived;
    					}
    				}
    				}
    				catch (IOException ex) {
    					
    				}
    			}
    		}).start();
    		
    	}
    	catch (UnknownHostException e) {
    		
    	}
    	catch (IOException e) {
    		
    	}
    	finally {
    		netOut.close();
    	}
        return false;
    }

}
