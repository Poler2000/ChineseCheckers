package tp.server;

public abstract class AbstractPlayer {
    private static final int numOfPawns = 10;
    private Pawn[] pawns = new Pawn[numOfPawns];
    private MoveBuilder moveBuilder;

    public Move proposeMove() {
        return new Move();
    }

    public void MakeMove(final Move move) {

    }
}
