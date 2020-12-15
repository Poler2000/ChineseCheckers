package tp.server.communication;

import tp.server.logic.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        System.out.println("Center Created");
    }

    public int establishConnections() {
        shouldListenForNewClients = true;
        final int[] numberOfClients = {0};
        System.out.println("Center Up and running");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                while (true) {
                    try {
                        System.out.println("Waiting for new client");
                        socket = serverSocket.accept();
                        System.out.println("Houston, we have connection");

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    numberOfClients[0]++;
                    System.out.println(numberOfClients[0]);
                    clientConnectors.add(new ClientConnector(socket, numberOfClients[0]));
                    clientConnectors.get(numberOfClients[0] - 1).start();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();

        while (true) {
            synchronized (locker) {
                if(!(shouldListenForNewClients && numberOfClients[0] < 6)) {
                   break;
                }
            }
        }
        return numberOfClients[0];
    }

    public void sendMessageToAll(final String msg) {
        for (ClientConnector c : clientConnectors) {
            c.send(msg);
        }
    }

    public void sendMessage(final String msg, int receiverId) {
        if (clientConnectors.get(receiverId) != null) {
            clientConnectors.get(receiverId).send(msg);
        }
    }

    public void stopListeningForNewClients() {
        synchronized (locker) {
            shouldListenForNewClients = false;
        }
    }

    public void deactivate() {
        active = false;
    }

    private class ClientConnector extends Thread {
        private Socket clientSocket;
        private DataInputStream input = null;
        private DataOutputStream output = null;
        private final int id;

        public ClientConnector(Socket socket, int id) {
            this.clientSocket = socket;
            this.id = id;
            try {
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            String inputLine = null;
            while(active) {
                try {
                    inputLine = input.readUTF();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (locker) {
                    game.processMessage(inputLine, id);
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
            try {
                output.writeUTF(msg);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
