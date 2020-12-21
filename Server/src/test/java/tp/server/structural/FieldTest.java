package tp.server.structural;

import org.junit.Test;
import static org.junit.Assert.*;

public class FieldTest {

    @Test
    public void testTestClone() {
        Field f = new Field(1, 2, 3);
        Field g = f.clone();
        assertEquals(f, g);
    }

    @Test
    public void testTestEquals() {
        Field f = new Field(1, 2, -3);
        Field g = new Field(1, 2, -3);
        assertEquals(f, g);
        Field h = new Field(-1, -2, 3);
        assertNotEquals(f, h);
        g.setPlayerGoal(1);
        assertNotEquals(f, g);
        f.setPlayerGoal(1);
    }
}