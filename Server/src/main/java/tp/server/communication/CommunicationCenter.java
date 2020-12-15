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
                    System.out.println(numberOfClients[0]);
                    clientConnectors.add(new ClientConnector(socket));
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

        public ClientConnector(Socket socket) {
            this.clientSocket = socket;
            try {
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());
                System.out.println("Client is BORN!");
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
                    game.processMessage(inputLine);
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
