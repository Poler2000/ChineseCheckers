package tp.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Game
{
    private boolean isRunning;
    private MoveValidator moveValidator;
    ArrayList<AbstractPlayer> players;
    private ServerSocket serverSocket;

    public static void main(String[] args)
    {
        /*ObjectMapper objectMapper = new ObjectMapper();
        Field field1 = new Field(1,2,3,1,3,true);
        Field field2 = new Field(-1,7,5,1,4,false);

        String json1 = null;
        String json2 = null;
        try {
            json1 = objectMapper.writeValueAsString(field1);
            json2 = objectMapper.writeValueAsString(field2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json1);
        System.out.println(json2);*/

        Game game = new Game();


        try {
            game.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game.run();
        game.end();
    }

    private void init() throws IOException {
        serverSocket = new ServerSocket();


    }

    private void run() {
        while(isRunning) {

        }
    }

    private void end() {
    }
}
