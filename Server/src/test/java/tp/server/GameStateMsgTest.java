package tp.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        ServerMsg msg = new GameStateMsg(3, pawns);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = "{\"type\":\"GameState\",\"turnOf\":3,\"pawns\":[{\"location\":{\"coord\":{\"x\":-1,\"y\":-2,\"z\":3},\"playerHome\":0,\"playerGoal\":0,\"occupied\":true,\"corner\":\"NONE\"},\"owner\":2,\"id\":3},{\"location\":{\"coord\":{\"x\":1,\"y\":2,\"z\":-3},\"playerHome\":0,\"playerGoal\":0,\"occupied\":true,\"corner\":\"NONE\"},\"owner\":3,\"id\":4},{\"location\":{\"coord\":{\"x\":7,\"y\":-3,\"z\":-4},\"playerHome\":0,\"playerGoal\":0,\"occupied\":true,\"corner\":\"TOP_RIGHT\"},\"owner\":4,\"id\":5}]}";

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
        assertEquals(expected, json);
    }
}
