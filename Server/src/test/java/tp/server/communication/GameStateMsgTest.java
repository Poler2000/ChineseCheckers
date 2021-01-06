package tp.server.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import tp.server.structural.Field;
import tp.server.structural.Pawn;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GameStateMsgTest {
    @Test
    public void toJSONTest()
    {
        Pawn.resetIdCounter();
        Field.resetIdCounter();
        ArrayList<Pawn> pawns = new ArrayList<>();
        pawns.add(new Pawn(new Field(-1,-2,3), 2));
        pawns.add(new Pawn(new Field(1,2,-3), 3));
        pawns.add(new Pawn(new Field(7,-3,-4), 4));
        ServerMsg msg = new StateReport(3, pawns, 0, 1);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = "{\"type\":\"gameState\",\"yourPlayerID\":1,\"turnOf\":3,\"pawns\":[{\"id\":0,\"owner\":2,\"location\":0},{\"id\":1,\"owner\":3,\"location\":1},{\"id\":2,\"owner\":4,\"location\":2}],\"won\":0}";
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