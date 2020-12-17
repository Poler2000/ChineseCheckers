package tp.client.game;

import org.junit.Test;
import static org.junit.Assert.*;

import tp.client.structural.*;
import java.util.ArrayList;
import java.util.List;

public class ValidatorTest {
	
	private Pawn[] state;
	
	public ValidatorTest() {
		state = new Pawn[2];
		state[0] = new Pawn();
		state[1] = new Pawn();
		state[0].id = 1;
		state[1].id = 2;
		state[0].playerid = 1;
		state[1].playerid = 1;
		state[0].location = new Field();
		state[1].location = new Field();
		state[0].location.id = 1;
		state[1].location.id = 2;
		state[0].location.playerGoal = 0;
		state[1].location.playerGoal = 0;
		state[0].location.coordinates = new int[]{0,0,0};
		state[1].location.coordinates = new int[]{0,1,-1};
	}

	
	@Test
	public void simpleStepTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = new Field();
		s1.destination.id = 3;
		s1.destination.playerGoal = 0;
		s1.destination.coordinates = new int[] {1,0,-1};
		
		input.add(s1);
		assertTrue(MoveValidator.validate(input, state));
	}
	
	@Test
	public void simpleJumpTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = new Field();
		s1.destination.id = 3;
		s1.destination.playerGoal = 0;
		s1.destination.coordinates = new int[] {0,2,-2};
		
		input.add(s1);
		assertTrue(MoveValidator.validate(input, state));
	}
	
	@Test
	public void destOccupiedTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = state[1].location;
		
		input.add(s1);
		assertFalse(MoveValidator.validate(input, state));
	}
	
	@Test
	public void airJumpTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = new Field();
		s1.destination.id = 3;
		s1.destination.playerGoal = 0;
		s1.destination.coordinates = new int[] {2,0,-2};
		
		input.add(s1);
		assertFalse(MoveValidator.validate(input, state));
	}
	
	@Test
	public void farJumpTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = new Field();
		s1.destination.id = 3;
		s1.destination.playerGoal = 0;
		s1.destination.coordinates = new int[] {0,3,-3};
		
		input.add(s1);
		assertFalse(MoveValidator.validate(input, state));
	}
	
	@Test
	public void moveAfterJumpTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = new Field();
		s1.destination.id = 3;
		s1.destination.playerGoal = 0;
		s1.destination.coordinates = new int[] {0,2,-2};
		Step s2 = new Step();
		s2.actor = state[0];
		s2.destination = new Field();
		s2.destination.id = 4;
		s2.destination.playerGoal = 0;
		s2.destination.coordinates = new int[] {0,3,-3};
		
		input.add(s1);
		input.add(s2);
		assertFalse(MoveValidator.validate(input, state));
	}
	
	@Test
	public void jumpAfterMoveTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = new Field();
		s1.destination.id = 3;
		s1.destination.playerGoal = 0;
		s1.destination.coordinates = new int[] {1,0,-1};
		Step s2 = new Step();
		s2.actor = state[0];
		s2.destination = new Field();
		s2.destination.id = 4;
		s2.destination.playerGoal = 0;
		s2.destination.coordinates = new int[] {-1,2,-1};
		
		input.add(s1);
		input.add(s2);
		assertFalse(MoveValidator.validate(input, state));
	}
	
	@Test
	public void manyPieceTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = new Field();
		s1.destination.id = 3;
		s1.destination.playerGoal = 0;
		s1.destination.coordinates = new int[] {0,2,-2};
		Step s2 = new Step();
		s2.actor = state[1];
		s2.destination = new Field();
		s2.destination.id = 4;
		s2.destination.playerGoal = 0;
		s2.destination.coordinates = new int[] {0,3,-3};
		
		input.add(s1);
		input.add(s2);
		assertFalse(MoveValidator.validate(input, state));
	}
	
	@Test
	public void goalLeaveTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = new Field();
		s1.destination.id = 3;
		s1.destination.playerGoal = 0;
		s1.destination.coordinates = new int[] {1,0,-1};
		s1.actor.location.playerGoal = 1;
		
		input.add(s1);
		assertFalse(MoveValidator.validate(input, state));
	}
	
	@Test
	public void multiJumpTest(){
		List<Step> input = new ArrayList<Step>();
		Step s1 = new Step();
		s1.actor = state[0];
		s1.destination = new Field();
		s1.destination.id = 3;
		s1.destination.playerGoal = 0;
		s1.destination.coordinates = new int[] {0,2,-2};
		Step s2 = new Step();
		s2.actor = state[0];
		s2.destination = state[0].location;
		
		input.add(s1);
		input.add(s2);
		assertTrue(MoveValidator.validate(input, state));
	}
}
