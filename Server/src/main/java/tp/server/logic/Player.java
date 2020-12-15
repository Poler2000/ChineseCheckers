package tp.server.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tp.server.structural.Move;
import tp.server.structural.Pawn;

import java.util.ArrayList;

public class Player extends AbstractPlayer {
    private Move move = null;

    public Player(ArrayList<Pawn> pawns) {
        super(pawns);
    }

    @Override
    public Move proposeMove() {
        while (move == null) ;

        Move copy = move;
        move = null;
        return copy;
    }

    @Override
    public void setMove(final Move move) {
        this.move = move;
    }
}
