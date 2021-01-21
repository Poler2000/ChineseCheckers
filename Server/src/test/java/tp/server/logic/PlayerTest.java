package tp.server.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import tp.server.logic.AbstractPlayer;
import tp.server.logic.Player;
import tp.server.structural.Field;
import tp.server.structural.Move;
import tp.server.structural.Pawn;
import tp.server.structural.Step;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void proposeMoveTest() {
        ArrayList<Pawn> pawns = new ArrayList<>();
        pawns.add(new Pawn(new Field(-1,-2,3), 1));
        pawns.add(new Pawn(new Field(1,2,-3), 1));
        pawns.add(new Pawn(new Field(7,-3,-4), 1));
        AbstractPlayer player = new Player(pawns);
        Move move = new Move();
        move.addStep(new Step(pawns.get(1), pawns.get(2).getLocation()));
        player.setMove(move);

        assertEquals(move, player.proposeMove());
    }
}
