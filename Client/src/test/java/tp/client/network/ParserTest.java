package tp.client.network;

import org.junit.Test;
import static org.junit.Assert.*;

import org.json.JSONObject;
import java.util.Map;
import java.util.HashMap;
import tp.client.structural.*;

public class ParserTest {
	
	@Test
	public void ConfigParserTest() {
		String testJSON =
			"{" +
					 "\"type\": \"config\"," +
					 "\"players\": 1," +
					 "\"gamestate\": 2," +
					 "\"map\": [" +
					        "{\"id\": 1, \"coords\": [0,0,0], \"goalof\": 0}," +
					        "{\"id\": 2, \"coords\": [0,-1,1], \"goalof\": 0}," +
					        "{\"id\": 3, \"coords\": [0,1,-1], \"goalof\": 0}" +
					        "]," +
					 "\"yourPlayerID\": 1" +
			"}";
		
		JSONObject input = new JSONObject(testJSON);
		ServerConfig result = ConfigParser.parse(input);
		assertEquals(result.gamestate, GameState.INPROGRESS);
		assertEquals(result.playersInGame, 1);
		assertEquals(result.map.length, 3);
		assertEquals(result.map[1].id, 2);
		assertEquals(result.map[1].playerGoal, 0);
		assertEquals(result.map[1].coordinates[1], -1);
		
		String malformed = "{}";
		JSONObject empty = new JSONObject(malformed);
		ServerConfig failed = ConfigParser.parse(empty);
		assertEquals(failed, null);
				
	}

	@Test
	public void StateParserTest() {
		Map<Integer,Field> refs = new HashMap<Integer, Field>();
		Field f1 = new Field();
		Field f2 = new Field();
		f1.id = 1;
		f2.id = 2;
		refs.put(1, f1);
		refs.put(2, f2);
		
		String testJSON =
					"{" +
					 "\"type\": \"gameState\"," +
					 "\"turnOf\": 2," +
					 "\"pawns\": [" +
					          "{\"id\": 1, \"owner\": 1, \"location\": 1}," +
					          "{\"id\": 2, \"owner\": 2, \"location\": 2}" +
					          "]," +
					 "\"won\": 1," +
					 "\"yourPlayerID\": 1" +
					"}";
		
		String testJSONBadField =
"					{" +
"					 \"type\": \"gameState\"," +
"					 \"turnOf\": 2," +
"					 \"pawns\": [" +
"					          {\"id\": 1, \"owner\": 1, \"location\": 5}" +
"					          ]," +
"					 \"yourPlayerID\": 1" +
"					}";	
		
		JSONObject goodStatej = new JSONObject(testJSON);
		JSONObject badStatej = new JSONObject(testJSONBadField);
		
		StateReport goodState = StateParser.parse(goodStatej, refs);
		StateReport badState = StateParser.parse(badStatej, refs);
		
		assertEquals(badState, null);
		assertEquals(goodState.currentPlayer, 2);
		assertEquals(goodState.wonPlayer, 1);
		assertEquals(goodState.toPlayerID, 1);
		assertEquals(goodState.deployment[1].playerid, 2);
				
	}
	
	@Test
	public void ReplayParserTest() {
		String testJSON =
			"{" +
					 "\"type\": \"replayList\"," +
					 "\"games\": [" +
					        "{\"id\": 1, \"players\": 2, \"date\": \"2020-01-12 18:00\"}," +
					        "{\"id\": 5, \"players\": 6, \"date\": \"2020-05-11 07:11\"}," +
					        "{\"id\": 8, \"players\": 3, \"date\": \"2020-11-16 11:00\"}" +
					        "]," +
			"}";
		
		JSONObject input = new JSONObject(testJSON);
		Replay[] result = ReplayParser.parse(input);
		assertEquals(result.length, 3);
		assertEquals(result[1].id, 5);
		assertEquals(result[1].players, 6);
		assertEquals(result[1].date, "2020-05-11 07:11");
		
		String malformed = "{}";
		JSONObject empty = new JSONObject(malformed);
		Replay[] failed = ReplayParser.parse(empty);
		assertEquals(failed, null);
				
	}
}
