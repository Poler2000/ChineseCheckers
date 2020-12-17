package tp.client.network;

import java.net.*;
import java.io.*;

public class FakeServer {
	public volatile String lastmsgs = "";
	public volatile boolean end = false;

	volatile ServerSocket serverSocket;
	volatile Socket clientSocket;
	volatile PrintWriter out;
	volatile BufferedReader in;
	
	public void write(String msg) {
		out.print(msg);
		out.flush();
	}
	
	public FakeServer() {
		(new Thread() {
			public void run() {
				try {
					serverSocket = new ServerSocket(1410);
					while (!end) {
						clientSocket = serverSocket.accept();
						out = new PrintWriter(clientSocket.getOutputStream(), true);
						in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						
						String inLine;
						
						while((inLine = in.readLine()) != null) {
							lastmsgs += (inLine + "\n");
						}
					}
				}
				catch (Exception ex) {
					
				}
			}
		}).start();
	}


}
