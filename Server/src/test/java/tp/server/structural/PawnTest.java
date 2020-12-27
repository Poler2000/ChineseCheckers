package tp.server.structural;

import org.junit.Test;

import static org.junit.Assert.*;

public class PawnTest {

    @Test
    public void testMove() {
        Pawn p = new Pawn(new Field(0, 0, 0), 1);
        Field dest = new Field(1, 1, 0);
        p.move(dest);

        assertArrayEquals(dest.getCoords(), p.getLocation().getCoords());
    }

    @Test
    public void testTestClone() {
        Pawn p = new Pawn(new Field(0, 0, 0), 1);
        Pawn q = p.clone();
        assertEquals(p, q);
    }
}