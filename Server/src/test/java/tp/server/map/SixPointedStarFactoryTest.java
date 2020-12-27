package tp.server.map;

import org.junit.Test;
import tp.server.structural.Pawn;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SixPointedStarFactoryTest {
    @Test
    public void createMapTest() {
        MapFactory mapFactory = new SixPointedStarFactory();

        Map map = mapFactory.createMap(2);
        ArrayList<Pawn> pawns = new ArrayList<>();

        assertEquals(121, map.numOfFields());
    }

    @Test
    public void createPawnsTest() {
        MapFactory mapFactory = new SixPointedStarFactory();

        for (int i = 2; i < 7; i++) {
            if (i != 5) {
                Map map = mapFactory.createMap(i);
                ArrayList<Pawn> pawns = new ArrayList<>();
                for (int j = 1; j <= i; j++) {
                    pawns.addAll(mapFactory.createPawns(j, i));
                }
                assertEquals(i * 10, pawns.size());
            }
        }
    }
}
