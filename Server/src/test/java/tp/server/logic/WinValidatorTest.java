package tp.server.logic;

import org.junit.Before;
import org.junit.Test;
import tp.server.map.Map;
import tp.server.map.MapFactory;
import tp.server.map.SixPointedStarFactory;
import tp.server.structural.Field;
import tp.server.structural.Pawn;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WinValidatorTest {
    @Before
    public void setup() {
        AbstractPlayer.resetIdCounter();
    }


    @Test
    public void isThereWinnerTest() {
        MapFactory mapFactory = new SixPointedStarFactory();
        Map map = mapFactory.createMap(3);
        ArrayList<AbstractPlayer> players = new ArrayList<>();

        players.add(new Player(mapFactory.createPawns(1, 3)));
        players.add(new Player(mapFactory.createPawns(2, 3)));
        players.add(new Player(mapFactory.createPawns(3, 3)));
        WinValidator wv = new WinValidator(players);

        assertFalse(wv.isThereWinner());

        for (Pawn p : players.get(0).getPawns()) {
            p.move(map.getField(-p.getLocation().coordinatesAsXYZ().x,
                    -p.getLocation().coordinatesAsXYZ().y , -p.getLocation().coordinatesAsXYZ().z));
        }

        assertTrue(wv.isThereWinner());
    }

    @Test
    public void getWinnerTest() {
        MapFactory mapFactory = new SixPointedStarFactory();
        Map map = mapFactory.createMap(3);
        ArrayList<AbstractPlayer> players = new ArrayList<>();

        players.add(new Player(mapFactory.createPawns(1, 3)));
        players.add(new Player(mapFactory.createPawns(2, 3)));
        players.add(new Player(mapFactory.createPawns(3, 3)));
        WinValidator wv = new WinValidator(players);

        for (Pawn p : players.get(0).getPawns()) {
            p.move(map.getField(-p.getLocation().coordinatesAsXYZ().x,
                    -p.getLocation().coordinatesAsXYZ().y , -p.getLocation().coordinatesAsXYZ().z));
        }
        assertTrue(wv.isThereWinner());
        assertEquals(1, wv.getWinner());
    }
}