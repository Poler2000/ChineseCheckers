package tp.server.logic;

import tp.server.structural.Pawn;

import java.util.ArrayList;

public class WinValidator {
    private ArrayList<AbstractPlayer> players;

    public WinValidator(ArrayList<AbstractPlayer> players) {
        this.players = players;
    }

    public boolean isThereWinner() {
        boolean result = false;
        for (AbstractPlayer p : players) {
            for (Pawn pawn : p.getPawns()) {
                result = (pawn.getLocation().getGoalof() == p.getId());
                System.out.println(result);
                if (!result) {
                    break;
                }
            }
        }
        return result;
    }
}
