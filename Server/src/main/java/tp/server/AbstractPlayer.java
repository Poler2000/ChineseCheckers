package tp.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractPlayer {
    private ArrayList<Pawn> pawns = new ArrayList<Pawn>();
    private MoveBuilder moveBuilder;

    // TODO implement
    public Move proposeMove() {
        return new Move();
    }

    // executes validated move
    public void makeMove(final Move move) {
        Iterator<Step> steps = move.getStepsIterator();

        while (steps.hasNext()) {
            Step step = steps.next();
            for (Pawn pawn : pawns) {
                if(pawn == step.getPiece()) {
                    pawn.move(step.getDestination());
                    break;
                }
            }
        }
    }

    public ArrayList<Pawn> getPawns() {
        return pawns;
    }
}
