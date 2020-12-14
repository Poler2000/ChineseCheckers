package tp.server.structural;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinatesTest {
    @Test
    public void equalsTest() {
        Coordinates a = new Coordinates(1,-1,4);
        Coordinates b = new Coordinates(1,-1,3);
        Coordinates c = new Coordinates(1,-1,4);

        assertNotEquals(a, b);
        assertEquals(a, c);
    }
}
