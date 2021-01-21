package tp.server.structural;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoveTest {
    @Test
    public void addStepTest() {
        Move move = new Move();
        Pawn pawn = new Pawn(new Field(1,2,3), 1);
        move.addStep(new Step(pawn, new Field(0,0,0)));
        move.addStep(new Step(pawn, new Field(1,-1,0)));
        move.addStep(new Step(pawn, new Field(2,-2,0)));

        assertEquals(3, move.getSteps().size());
    }
}
