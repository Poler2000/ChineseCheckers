package tp.server.structural;

/**
 * Represents single change in pawn's position
 */
public class Step {
    private final Pawn pawn;
    private final Field newlocation;

    public Step(final Pawn pawn, final Field field) {
        this.pawn = pawn;
        this.newlocation = field;
    }

    public Step() {
        this.pawn = null;
        this.newlocation = null;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public Field getNewlocation() {
        return newlocation;
    }
}
