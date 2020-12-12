package tp.server.logic;

import tp.server.MoveBuilder;
import tp.server.structural.Move;
import tp.server.structural.Pawn;
import tp.server.structural.Step;

import java.util.ArrayList;

public class Bot extends AbstractPlayer {
    MoveBuilder builder;

    public Bot(ArrayList<Pawn> pawns) {
        super(pawns);
        builder = new MoveBuilder() {
            @Override
            public Move skipMove() {
                Move move = new Move();
                move.addStep(new Step(pawns.get(0), pawns.get(0).getLocation()));
                return move;
            }
        };
    }

    @Override
    public Move proposeMove() {
        return builder.skipMove();
    }
}
