package tp.server;

import java.util.Iterator;

public abstract class AbstractPlayer {
    private static final int numOfPawns = 10;
    private Pawn[] pawns = new Pawn[numOfPawns];
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

}
