package tp.server;

import java.util.ArrayList;

public class GameStateMsg extends ServerMsg {
    private int turnOf;
    private ArrayList<Pawn> pawns;

    public GameStateMsg(int currentPlayer, ArrayList<Pawn> pawns) {
        type = "GameState";
            turnOf = currentPlayer;
            this.pawns = pawns;
    }

    public int getTurnOf() {
        return turnOf;
    }

    public ArrayList<Pawn> getPawns() {
        return pawns;
    }
}
