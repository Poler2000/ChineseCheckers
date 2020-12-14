package tp.server.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import tp.server.Map;
import tp.server.SixPointedStarFactory;
import tp.server.communication.ServerCfg;
import tp.server.communication.ServerMsg;
import tp.server.logic.Game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerCfgMsgTest {
    @Test
    public void toJSONTest()
    {
        Map map = (new SixPointedStarFactory()).createMap(3);
        ServerMsg msg = new ServerCfg(3, Game.gameStates.ONGOING, map, 3);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try
        {
            json = objectMapper.writeValueAsString(msg);
            JsonNode rootNode = objectMapper.readTree(json);

           // System.out.println(rootNode.path("map").equals("") ? "null" : "not null");
            Map newMap = objectMapper.convertValue(rootNode.path("map"), Map.class);
            assertEquals(121, newMap.numOfFields());

        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
