package tp.server.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tp.server.structural.Move;

public class ClientMessageParser {
    public Move getMove(String msg) {
        ObjectMapper objectMapper = new ObjectMapper();
        Move move = null;
        try
        {
            JsonNode rootNode = objectMapper.readTree(msg);

            move = objectMapper.convertValue(rootNode.path("steps"), Move.class);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return move;
    }
}
