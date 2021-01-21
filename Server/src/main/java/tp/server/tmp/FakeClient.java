package tp.server.tmp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tp.server.map.Map;
import tp.server.logic.AbstractPlayer;
import tp.server.structural.Move;
import tp.server.structural.Step;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class FakeClient {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private int id;
    private static int idCounter = 0;
    private AbstractPlayer p;
    private Map map;
    private boolean receivedMsg = false;

    public void init(String address, int port) {
        try
        {
            socket = new Socket(address, port);

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        id = ++idCounter;
    }

    public String sendMove() {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = null;
        try {
            Move move = new Move();
            move.addStep(new Step(p.getPawns().get(0), p.getPawns().get(0).getLocation()));
            json = objectMapper.writeValueAsString(move);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String sendRegister() {
        return "{ \"msgType\": \"register\"}";
    }

    public String sendSetup() {
        return "{ \"msgType\": \"setup\"}";
    }

    public boolean isReceivedMsg() {
        return receivedMsg;
    }

    public void setReceivedMsg(boolean receivedMsg) {
        this.receivedMsg = receivedMsg;
    }
}