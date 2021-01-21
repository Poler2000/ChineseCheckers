package tp.server.logic;

import tp.server.structural.Move;
import tp.server.structural.Pawn;
import tp.server.structural.Step;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Player participating in game.
 * Has pawns and id
 */
public abstract class AbstractPlayer {
    private ArrayList<Pawn> pawns = new ArrayList<Pawn>();
    private MoveBuilder moveBuilder;
    private final int id;

    private static final Object locker = new Object();
    private static int id_counter = 0;

    public AbstractPlayer(ArrayList<Pawn> pawns) {
        id = assignId();
        this.pawns = pawns;
    }

    public int getId() {
        return id;
    }

    public abstract Move proposeMove();

    // executes validated move
    public void makeMove(final Move move) {
        Iterator<Step> steps = move.getSteps().iterator();

        while (steps.hasNext()) {
            Step step = steps.next();
            for (Pawn pawn : pawns) {
                if (pawn == step.getPawn()) {
                    pawn.move(step.getDestination());
                    break;
                }
            }
        }
    }

    private static int assignId() {
        synchronized (locker) {
            return ++id_counter;
        }
    }

    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    public void setMove(final Move move) {
    }

    public static void resetIdCounter() {
        id_counter = 0;
    }
}
