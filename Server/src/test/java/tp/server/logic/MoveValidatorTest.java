package tp.server.logic;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tp.server.map.Map;
import tp.server.map.MapFactory;
import tp.server.map.SixPointedStarFactory;
import tp.server.structural.*;

@RunWith(JUnit4.class)
public class MoveValidatorTest extends TestCase {
    private Map map;
    private MapFactory mapFactory;
    private MoveValidator mv;
    private Player p1;
    private Player p2;

    @Before
    public void setup() {
        mapFactory = new SixPointedStarFactory();

        map = mapFactory.createMap(2);
        p1 = new Player(mapFactory.createPawns(1, 2));
        p2 = new Player(mapFactory.createPawns(2, 2));
        mv = new MoveValidator(map);
    }

    @Test
    public void testValidateTrue() {
        Step s1 = new Step(p1.getPawns().get(0), p1.getPawns().get(0).getLocation());
        Move move1 = new Move();
        move1.addStep(s1);
        assertTrue(mv.Validate(move1));

        Step s2 = new Step(p1.getPawns().get(0), map.getField(0, 4, -4));

        Move move2 = new Move();
        move2.addStep(s2);
        assertTrue(mv.Validate(move2));
        move2.addStep(s1);
        assertFalse(mv.Validate(move2));

        Move move3 = new Move();
        move3.addStep(new Step(p2.getPawns().get(0), map.getField(0, 4, -4)));
        p2.makeMove(move3);

        Move move4 = new Move();
        move4.addStep(new Step(p1.getPawns().get(0), map.getField(-1, 4, -3)));
        assertTrue(mv.Validate(move4));
    }

    @Test
    public void testValidateFalse() {
        Step s1 = new Step(p1.getPawns().get(0), p1.getPawns().get(1).getLocation());
        Step s2 = new Step(p1.getPawns().get(0), map.getField(0, 4, -4));
        Step s3 = new Step(p1.getPawns().get(0), map.getField(1, 4, -5));
        Move move1 = new Move();
        move1.addStep(s1);
        assertFalse(mv.Validate(move1));

        Move move2 = new Move();
        move2.addStep(s2);
        move2.addStep(s3);
        assertFalse(mv.Validate(move2));
    }
}