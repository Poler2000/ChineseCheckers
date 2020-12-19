package tp.client.game;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import tp.client.structural.*;
import tp.client.graphical.GUIManager;
import tp.client.network.NetworkManager;

public class GameTest {

	@Test
	public void gameManagerTest() {
		GUIManager guimock = Mockito.mock(GUIManager.class);
		NetworkManager netmock = Mockito.mock(NetworkManager.class);
		
		GameManager undertest = new GameManager(guimock, netmock);
		
		undertest.handleServerConnReq("github.com");
		Mockito.verify(netmock).connect("github.com");
		
		undertest.handleServerConnect();
		
		ServerConfig sconf = new ServerConfig();
		sconf.gamestate = GameState.READY;
		sconf.playersInGame = 2;
		sconf.toPlayerID = 1;
		sconf.map = new Field[2];
		sconf.map[0] = new Field();
		sconf.map[0].id = 1;
		sconf.map[0].playerGoal = 0;
		sconf.map[0].coordinates = new int[] {0,0,0};
		sconf.map[1] = new Field();
		sconf.map[1].id = 2;
		sconf.map[1].playerGoal = 0;
		sconf.map[1].coordinates = new int[] {0,1,-1};
		
		undertest.handleNewServerCfg(sconf);
		Mockito.verify(guimock).disableGameStart(false);
		
		undertest.handleGameStartReq();
		Mockito.verify(netmock).sendGameStart();
		
		sconf.gamestate = GameState.INPROGRESS;
		undertest.handleNewServerCfg(sconf);
		Mockito.verify(guimock).disableGameStart(true);
		
		StateReport srep = new StateReport();
		srep.currentPlayer = 1;
		srep.wonPlayer = 0;
		srep.toPlayerID = 1;
		srep.deployment = new Pawn[1];
		srep.deployment[0] = new Pawn();
		srep.deployment[0].id = 1;
		srep.deployment[0].playerid = 1;
		srep.deployment[0].location = sconf.map[0];
		
		undertest.handleNewGameState(srep);
		undertest.getMapLock();
		Mockito.verify(guimock).setPawns(Mockito.any(), Mockito.anyInt());
		
		List<Step> expectedMove = new ArrayList<Step>();
		
		Step newstep = new Step();
		newstep.actor = srep.deployment[0];
		newstep.destination = sconf.map[1];
		expectedMove.add(newstep);
		undertest.handleMovement(newstep);
		
		Step newstep2 = new Step();
		newstep2.actor = srep.deployment[0];
		newstep2.destination = sconf.map[0];
		undertest.handleMovement(newstep2);
		
		undertest.handleTurnEndReq();
		Mockito.verify(netmock).sendMove(expectedMove.toArray(new Step[0]));
		
		
		srep.currentPlayer = 2;
		undertest.handleNewGameState(srep);
		undertest.handleMovement(newstep);
		undertest.handleTurnEndReq();
		
		srep.wonPlayer = 2;
		undertest.handleNewGameState(srep);
		undertest.handleMovement(newstep);
		undertest.handleTurnEndReq();
		
		Mockito.verify(netmock, Mockito.times(1)).sendMove(Mockito.any());
		
		undertest.handleServerDisconnect();
		Mockito.verify(guimock).setNetworkLabel("Rozłączony");
		
		
	}
}
