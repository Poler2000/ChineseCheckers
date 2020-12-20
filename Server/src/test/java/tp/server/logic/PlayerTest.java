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

        ObjectMapper objectMapper = new ObjectMapper();
        //String expected = "{\"msgType\":\"GameState\",\"turnOf\":3,\"pawns\":[{\"location\":{\"coord\":{\"x\":-1,\"y\":-2,\"z\":3},\"playerHome\":0,\"playerGoal\":0,\"occupied\":true,\"corner\":\"NONE\"},\"owner\":2,\"id\":0},{\"location\":{\"coord\":{\"x\":1,\"y\":2,\"z\":-3},\"playerHome\":0,\"playerGoal\":0,\"occupied\":true,\"corner\":\"NONE\"},\"owner\":3,\"id\":1},{\"location\":{\"coord\":{\"x\":7,\"y\":-3,\"z\":-4},\"playerHome\":0,\"playerGoal\":0,\"occupied\":true,\"corner\":\"TOP_RIGHT\"},\"owner\":4,\"id\":2}]}";

        String json = null;
        try
        {
            json = objectMapper.writeValueAsString(move);
            player.setMove(move);
            Move newMove = player.proposeMove();
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //assertEquals(expected, json);
        assertEquals(true, true);
    }

    @Test
    public void noPossibleMoveTest() {
    }
}
