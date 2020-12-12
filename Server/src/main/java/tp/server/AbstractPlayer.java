package tp.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractPlayer {
    private ArrayList<Pawn> pawns = new ArrayList<Pawn>();
    private MoveBuilder moveBuilder;

    public AbstractPlayer(ArrayList<Pawn> pawns) {
        this.pawns = pawns;
    }
    public abstract Move proposeMove();

    // executes validated move
    public void makeMove(final Move move) {
        Iterator<Step> steps = move.getSteps().iterator();

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

    public void setMove(final String move) {
    }
}
