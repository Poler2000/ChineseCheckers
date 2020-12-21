package tp.server.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tp.server.structural.Move;
import tp.server.structural.Pawn;

import java.util.ArrayList;

/**
 * Player is controlled by client's app
 */
public class Player extends AbstractPlayer {
    private volatile Move move;

    public Player(ArrayList<Pawn> pawns) {
        super(pawns);
    }

    @Override
    public Move proposeMove() {
        while (!checkPrepared()) ;

        Move copy = move;
        move = null;
        return copy;
    }

    @Override
    public synchronized void setMove(final Move move) {
        this.move = move;
    }

    public synchronized boolean checkPrepared() {
        return !(move == null);
    }
}
