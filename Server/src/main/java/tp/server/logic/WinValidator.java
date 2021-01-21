package tp.server.logic;

import tp.server.structural.Pawn;

import java.util.ArrayList;

/**
 * Checks if win conditions have been achieved by any player
 */
public class WinValidator {
    private ArrayList<AbstractPlayer> players;
    private int winner = 0;

    public WinValidator(ArrayList<AbstractPlayer> players) {
        this.players = players;
    }

    /**
     * if players's pawns are only on fields that are his destination -
     * he won
     * @return
     */
    public boolean isThereWinner() {
        boolean result = false;
        for (AbstractPlayer p : players) {
            for (Pawn pawn : p.getPawns()) {
                result = (pawn.getLocation().getGoalof() == p.getId());
                if (!result) {
                    break;
                }
            }
            if (result) {
                winner = p.getId();
                return result;
            }
        }
        return result;
    }

    public int getWinner() {
        return winner;
    }
}
