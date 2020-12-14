package tp.server.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tp.server.structural.Move;
import tp.server.structural.Pawn;

import java.util.ArrayList;

public class Player extends AbstractPlayer {
    private String moveJSON = null;

    public Player(ArrayList<Pawn> pawns) {
        super(pawns);
    }

    @Override
    public Move proposeMove() {
        while (moveJSON == null) ;

        Move move = null;
        try
        {
            move = new ObjectMapper().readValue(moveJSON, Move.class);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        moveJSON = null;;
        return move;
    }

    @Override
    public void setMove(final String move) {
        moveJSON = move;
    }
}
