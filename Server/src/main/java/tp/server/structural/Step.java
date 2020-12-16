package tp.server.structural;

/**
 * Represents single change in pawn's position
 */
public class Step {
    private final Pawn actor;
    private final Field destination;

    public Step(final Pawn pawn, final Field field) {
        this.actor = pawn;
        this.destination = field;
    }

    public Step() {
        this.actor = null;
        this.destination = null;
    }

    public Pawn getActor() {
        return actor;
    }

    public Field getDestination() {
        return destination;
    }
}
