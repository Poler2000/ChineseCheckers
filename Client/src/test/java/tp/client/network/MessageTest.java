package tp.client.network;

import org.junit.Test;
import static org.junit.Assert.*;

import tp.client.structural.*;

public class MessageTest {
	
	@Test
	public void RegistrationMsgTest() {
		RegistrationMsg undertest = new RegistrationMsg();
		assertEquals(undertest.toString(), "{ \"type\": \"registerMsg\"}");
	}
	
	@Test
	public void SetupMsgTest() {
		SetupMsg undertest = new SetupMsg();
		assertEquals(undertest.toString(), "{ \"type\": \"setupMsg\", \"setState\": 2}");
	}
	
	@Test
	public void MoveMsgTest() {
		Pawn user = new Pawn();
		Field f1 = new Field();
		Field f2 = new Field();
		Step[] move = new Step[2];
		Step[] emptyMove = new Step[0];
		Step s1 = new Step();
		Step s2 = new Step();
		
		user.id = 1;
		user.playerid = 2;
		user.location = f1;
		f1.id = 1;
		f2.id = 2;
		
		s1.actor = user;
		s2.actor = user;
		s1.destination = f2;
		s2.destination = f1;
		move[0] = s1;
		move[1] = s2;
		
		MoveMsg real = new MoveMsg(move);
		MoveMsg empty = new MoveMsg(emptyMove);
		
		assertEquals(real.toString(), "{\"type\":\"playerMove\",\"steps\":[{\"newlocation\":2,\"pawn\":1},{\"newlocation\":1,\"pawn\":1}]}");
		assertEquals(empty.toString(), "{\"type\":\"playerMove\",\"steps\":[]}");
		
	}

}
