package tp.server.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import tp.server.map.Map;
import tp.server.map.SixPointedStarFactory;
import tp.server.structural.Field;
import tp.server.structural.GameState;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerConfigMsgTest {
    @Test
    public void toJSONTest()
    {
        Map map = (new SixPointedStarFactory()).createMap(3);
        ServerMsg msg = new ServerConfig(3, GameState.INPROGRESS, map.getFields(), 3);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try
        {
            json = objectMapper.writeValueAsString(msg);
            JsonNode rootNode = objectMapper.readTree(json);

            ArrayList<Field> fields = objectMapper.convertValue(rootNode.path("map"), ArrayList.class);
            assertEquals(121, fields.size());

        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
