package tp.client.graphical;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import tp.client.game.UserEventsHandler;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.util.AbstractMap;

import javax.swing.JFrame;
import tp.client.structural.*;

public class GUIManagerTest {
	@Test
	public void GUIManagerCommsTest() {
		GUICreator fakeCreator = Mockito.mock(GUICreator.class);
		BoardGUI fakeBoard = Mockito.mock(BoardGUI.class);
		NetworkGUI fakeNet = Mockito.mock(NetworkGUI.class);
		ActionsGUI fakeAct = Mockito.mock(ActionsGUI.class);
		JFrame fakeWindow = Mockito.mock(JFrame.class);
		
		Mockito.when(fakeCreator.createBoard(Mockito.any(), Mockito.anyInt())).thenReturn(fakeBoard);
		Mockito.when(fakeCreator.createNetwork(Mockito.any())).thenReturn(fakeNet);
		Mockito.when(fakeCreator.createActions(Mockito.any())).thenReturn(fakeAct);
		Mockito.when(fakeCreator.createWindow(Mockito.any())).thenReturn(fakeWindow);
		
		GUIManager undertest = new GUIManager(fakeCreator);
		
		UserEventsHandler fakeup = Mockito.mock(UserEventsHandler.class);
		Object samplelock = new Object();
		Mockito.when(fakeup.getMapLock()).thenReturn(samplelock);
		undertest.setUserEventsHandler(fakeup);
		
		Mockito.verify(fakeWindow).setSize(1000,1000);
		Mockito.verify(fakeWindow).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Mockito.verify(fakeWindow).setVisible(true);
		Mockito.verify(fakeWindow).add(fakeBoard, BorderLayout.CENTER);
		Mockito.verify(fakeWindow).add(fakeNet, BorderLayout.PAGE_END);
		Mockito.verify(fakeWindow).add(fakeAct, BorderLayout.PAGE_START);
		Mockito.verify(fakeWindow).revalidate();
		
		undertest.handleStartGame();
		Mockito.verify(fakeup).handleGameStartReq();
		
		undertest.handleEndTurn();
		Mockito.verify(fakeup).handleTurnEndReq();
		
		undertest.handleConnectServer("localhost");
		Mockito.verify(fakeup).handleServerConnReq("localhost");
		
		undertest.disableTurn(true);
		assertTrue(fakeBoard.pickUpDisabled);
		Mockito.verify(fakeAct).enableEndTurn(false);
		
		undertest.disableGameStart(true);
		Mockito.verify(fakeAct).enableStartGame(false);
		
		undertest.setGameLabel("test1");
		Mockito.verify(fakeAct).setLabel("test1");
		
		undertest.setNetworkLabel("test2");
		Mockito.verify(fakeNet).setConnStatus("test2");
		
		ArgumentCaptor<FieldGUI[]> guimapcaptor = ArgumentCaptor.forClass(FieldGUI[].class);
		
		Field[] map = new Field[2];
		Field t1 = new Field();
		Field t2 = new Field();
		t1.id = 1;
		t2.id = 2;
		t1.playerGoal = 0;
		t2.playerGoal = 0;
		t1.coordinates = new int[]{0,0,0};
		t2.coordinates = new int[]{0,-1,1};
		map[0] = t1;
		map[1] = t2;
		undertest.setMap(map);
		Mockito.verify(fakeCreator).createBoard(guimapcaptor.capture(), Mockito.eq(3));
		Mockito.verify(fakeBoard, Mockito.times(2)).setPawns(new PawnGUI[0]);
		Mockito.verify(fakeWindow).remove(fakeBoard);
		Mockito.verify(fakeWindow).add(fakeBoard);
		Mockito.verify(fakeBoard).setMovementHandler(undertest);
		Mockito.verify(fakeWindow, Mockito.atLeastOnce()).revalidate();
		assertEquals(guimapcaptor.getAllValues().get(0)[0].getAdjustedX(1), 0, 0.001);
		assertEquals(guimapcaptor.getAllValues().get(0)[0].getAdjustedY(1), 0, 0.001);
		
		ArgumentCaptor<PawnGUI[]> guipawncaptor = ArgumentCaptor.forClass(PawnGUI[].class);
		
		Pawn[] pieces = new Pawn[1];
		Pawn testpawn = new Pawn();
		testpawn.id = 1;
		testpawn.playerid = 2;
		testpawn.location = t1;
		pieces[0] = testpawn;
		undertest.setPawns(pieces, 1);
		Mockito.verify(fakeBoard, Mockito.times(3)).setPawns(guipawncaptor.capture());
		assertEquals(guipawncaptor.getAllValues().get(2)[0].getAdjustedX(1), 0, 0.001);
		assertEquals(guipawncaptor.getAllValues().get(2)[0].getAdjustedY(1), 0, 0.001);
		
		undertest.handlePawnMovement(guipawncaptor.getAllValues().get(2)[0], guimapcaptor.getAllValues().get(0)[1]);
		undertest.handlePawnMovement(new PawnGUI(30,50), new FieldGUI(24, 87));
		
		Mockito.verify(fakeup, Mockito.times(1)).handleMovement(Mockito.any());
		
		undertest.setUserEventsHandler(null);
		assertTrue(undertest.handlePawnMovement(null, null));
		
		
	}
	
	@Test
	public void creatorTest() {
		GUICreator undertest = new GUICreator();
		assertTrue(undertest.createBoard(new FieldGUI[0], 3) instanceof BoardGUI);
		assertTrue(undertest.createActions(null) instanceof ActionsGUI);
		assertTrue(undertest.createNetwork(null) instanceof NetworkGUI);
		assertTrue(undertest.createWindow("test") instanceof JFrame);
	}
}
