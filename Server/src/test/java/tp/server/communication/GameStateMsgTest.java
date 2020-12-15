package tp.server.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import tp.server.structural.Field;
import tp.server.structural.Pawn;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameStateMsgTest {
    @Test
    public void toJSONTest()
    {
        ArrayList<Pawn> pawns = new ArrayList<>();
        pawns.add(new Pawn(new Field(-1,-2,3), 2));
        pawns.add(new Pawn(new Field(1,2,-3), 3));
        pawns.add(new Pawn(new Field(7,-3,-4), 4));
        ServerMsg msg = new StateReport(3, pawns, 0, 1);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = "{\"msgType\":\"gameState\",\"toPlayerID\":1,\"currentPlayer\":3,\"deployment\":[{\"id\":0,\"location\":{\"id\":0,\"coordinates\":[-1,-2,3],\"playerGoal\":0,\"occupied\":true},\"playerId\":2},{\"id\":1,\"location\":{\"id\":1,\"coordinates\":[1,2,-3],\"playerGoal\":0,\"occupied\":true},\"playerId\":3},{\"id\":2,\"location\":{\"id\":2,\"coordinates\":[7,-3,-4],\"playerGoal\":0,\"occupied\":true},\"playerId\":4}],\"wonPlayer\":0}";

        String json = null;
        try
        {
            json = objectMapper.writeValueAsString(msg);
            /*
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json);

            List<Double> x = mapper.convertValue(rootNode.get("pawns"), ArrayList.class);
            json = objectMapper.writeValueAsString(x);
            System.out.println(json);*/
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //assertEquals(expected, json);
    }
}
