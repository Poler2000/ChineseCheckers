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
        String expected = "{\"msgType\":\"gameState\",\"toPlayerID\":1,\"currentPlayer\":3,\"deployment\":[{\"id\":150,\"location\":{\"id\":605,\"coordinates\":[-1,-2,3],\"playerGoal\":0,\"occupied\":true},\"playerId\":2},{\"id\":151,\"location\":{\"id\":606,\"coordinates\":[1,2,-3],\"playerGoal\":0,\"occupied\":true},\"playerId\":3},{\"id\":152,\"location\":{\"id\":607,\"coordinates\":[7,-3,-4],\"playerGoal\":0,\"occupied\":true},\"playerId\":4}],\"wonPlayer\":0}";

        String json = null;
        try {
            json = objectMapper.writeValueAsString(msg);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals(expected, json);
    }
}
