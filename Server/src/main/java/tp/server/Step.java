package tp.server;

public class Step {
    private final Pawn piece;
    private final Field destination;

    public Step(final Pawn pawn, final Field field) {
        this.piece = pawn;
        this.destination = field;
    }

    public Pawn getPiece() {
        return piece;
    }

    public Field getDestination() {
        return destination;
    }
}
