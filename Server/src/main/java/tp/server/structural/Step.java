package tp.server.structural;

/**
 * Represents single change in pawn's position
 */
public class Step {
    private final Pawn pawn;
    private final Field destination;

    public Step(final Pawn pawn, final Field field) {
        this.pawn = pawn;
        this.destination = field;
    }

    public Step() {
        this.pawn = null;
        this.destination = null;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public Field getDestination() {
        return destination;
    }
}
