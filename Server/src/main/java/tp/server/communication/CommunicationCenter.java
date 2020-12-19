package tp.server.communication;

import tp.server.logic.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicationCenter {
    private ServerSocket serverSocket;
    private boolean shouldListenForNewClients;
    private boolean active;
    private ArrayList<ClientConnector> clientConnectors = new ArrayList<>();

    private Object locker = new Object();
    private final Game game;

    public CommunicationCenter(final int port, final Game game) throws IOException {
        serverSocket = new ServerSocket(port);
        this.game = game;
        active = true;
    }

    public int establishConnections() {
        shouldListenForNewClients = true;
        final int[] numberOfClients = {0};

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                while (true) {
                    try {
                        socket = serverSocket.accept();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    numberOfClients[0]++;
                    clientConnectors.add(new ClientConnector(socket, numberOfClients[0]));
                    clientConnectors.get(numberOfClients[0] - 1).start();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();

        while (true) {
            synchronized (locker) {
                if(!(shouldListenForNewClients && numberOfClients[0] <= 6)) {
                   break;
                }
            }
        }
        return numberOfClients[0];
    }

    public void sendMessage(final String msg, int receiverId) {
        if (clientConnectors.get(receiverId - 1) != null) {
            clientConnectors.get(receiverId - 1).send(msg);
        }
    }

    public void stopListeningForNewClients() {
        synchronized (locker) {
            shouldListenForNewClients = false;
        }
    }

    private class ClientConnector extends Thread {
        private Socket clientSocket;
        private volatile PrintWriter output = null;
        private volatile BufferedReader input = null;
        private final int id;

        public ClientConnector(Socket socket, int id) {
            this.clientSocket = socket;
            this.id = id;
            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            String inputLine = "";
            StringBuilder msg = new StringBuilder();
            while(active) {
                try {
                    msg = new StringBuilder();
                    //inputLine = input.readUTF();
                    while ((inputLine = input.readLine()) != null && !inputLine.equals("MessageTerminated")) {
                        msg.append(inputLine);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (locker) {
                    game.processMessage(msg.toString(), id);
                }
            }

            try {
                input.close();
                output.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(final String msg) {
            output.print(msg + "\r\nMessageTerminated\r\n");
            output.flush();
        }
    }
}
