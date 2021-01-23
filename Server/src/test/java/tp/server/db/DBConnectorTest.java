package tp.server.db;

import junit.framework.TestCase;
import tp.server.structural.Field;
import tp.server.structural.Move;
import tp.server.structural.Pawn;
import tp.server.structural.Step;

public class DBConnectorTest extends TestCase {
    DBConnector connector = null;

    public void setUp() throws Exception {
        connector = new DBConnector();
    }

    public void testCreateGame() {
        assertTrue(connector.createGame(2));
        assertFalse(connector.createGame(1));
    }

    public void testAddMove() {
        Move move = new Move();
        move.addStep(new Step(
                new Pawn(new Field(1, 1, -2), 3), new Field(1, -1, 0)));

        assertTrue(connector.createGame(2));
        assertTrue(connector.addMove(1, move));
        assertFalse(connector.addMove(1, null));

    }

    public void testGetMovesForGame() {
        assertTrue(connector.getGames().size() > 0);
    }

    public void testGetGames() {
        assertTrue(connector.getMovesForGame(1).size() > 0);
        assertTrue(connector.getMovesForGame(-1).isEmpty());
    }
}