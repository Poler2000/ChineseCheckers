package tp.client.network;

import java.io.*;
import java.net.*;

/**
 * The class responsible for managing the raw network socket
 * @author anon
 *
 */
public class ConnMan {
	private volatile Thread netThr = null;
	private volatile Socket netSock = null;
	private volatile PrintWriter netOut = null;
	private volatile BufferedReader netIn = null;
	
	
	private NetworkManager upstream = null;
	
	private volatile boolean connected = false;
	
	private Object connLock = new Object();
	private Object thrLock = new Object();
	
	
	/**
	 * The default constructor
	 * @param parent object to call back on (dis)connect / incoming message
	 */
	public ConnMan(NetworkManager parent) {
		upstream = parent;
	}
	
	private void onMessage(String msg) {
		upstream.handleIncoming(msg);
	}
	
	private void onStateChange(boolean connected) {
		upstream.connStateChanged(connected);
	}
	
	/**
	 * Send a string out the network
	 * Only works when connected
	 * @param msg payload
	 */
	public void send(String msg) {
		synchronized(connLock) {
			if (netSock != null && connected) {
				//System.out.println("NetOut: " + msg);
				netOut.print(msg + "\r\nMessageTerminated\r\n");
				netOut.flush();
			}
		}
	}
	
	/**
	 * Disconnect if currently connected
	 */
	public void disconnect() {
		synchronized(thrLock) {
			synchronized(connLock) {
				if (netSock != null) {
					try {
						netSock.close();
						netOut.close();
						netIn.close();
					}
					catch (IOException ex) {
						
					}
				}
			}
			
			if (netThr != null) {
				try {
					netThr.join();
				}
				catch (InterruptedException ex) {
					
				}
			}
		}
	}
	
	/**
	 * (Re)Connect to a server
	 * @param addr hostname
	 */
	public void connect(String addr) {
		disconnect();
		spawnThread(addr);
	}
	
	private void spawnThread(String addr) {
		synchronized(thrLock) {
			netThr = new Thread() {
				public void run() {
					try {
						synchronized(connLock) {
							netSock = new Socket(addr, 1410);
							netIn = new BufferedReader(new InputStreamReader(netSock.getInputStream()));
							netOut = new PrintWriter(netSock.getOutputStream(), true);
						}
						connected = true;
						onStateChange(true);
						
						String completeMessage = "";
						String inputReceived;
						
						while ((inputReceived = netIn.readLine()) != null) {
							//System.out.println("NetInput: " + inputReceived);
							if (inputReceived.equals("MessageTerminated")) {
								onMessage(completeMessage);
								completeMessage = "";
							}
							else {
								completeMessage += inputReceived;
							}
						}
						
						connected = false;
						onStateChange(false);
					}
					catch (IOException ex) {
						connected = false;
						onStateChange(false);
					}
				}
			};
			netThr.start();
		}
	}

}
