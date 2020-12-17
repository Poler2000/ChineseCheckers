package tp.client.network;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import tp.client.game.GameManager;
import tp.client.structural.*;

public class NetworkCoordinatorTest {

	@Test
	public void upstreamFunctionalityTest() {
		GameManager callb = Mockito.mock(GameManager.class);
		NetworkManager undertest = new NetworkManager(callb);
		
		undertest.connStateChanged(true);
		undertest.sendGameStart();
		undertest.sendMove(new Step[0]);
		
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
		
		String testJSON2 =
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
		
		undertest.handleIncoming(testJSON);
		undertest.handleIncoming(testJSON2);
		undertest.handleIncoming(testJSONBadField);
		undertest.connStateChanged(false);
		
		Mockito.verify(callb).handleNewServerCfg(Mockito.any());
		Mockito.verify(callb).handleNewGameState(Mockito.any());
		Mockito.verify(callb).handleServerConnect();
		Mockito.verify(callb).handleServerDisconnect();
		
	}
}
