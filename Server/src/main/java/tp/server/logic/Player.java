package tp.server.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tp.server.structural.Move;
import tp.server.structural.Pawn;

import java.util.ArrayList;

public class Player extends AbstractPlayer {
    private volatile Move move;

    public Player(ArrayList<Pawn> pawns) {
        super(pawns);
    }

    @Override
    public Move proposeMove() {
        System.out.println("waiting!");
        while (!checkPrepared()) {
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(checkPrepared());
        };
        //while (move == null)  ;
        System.out.println("Oh!");

        Move copy = move;
        move = null;
        return copy;
    }

    @Override
    public synchronized void setMove(final Move move) {
        this.move = move;
        System.out.println("Set!");
        System.out.println(checkPrepared());
    }

    public synchronized boolean checkPrepared() {
        return !(move == null);
    }
}
