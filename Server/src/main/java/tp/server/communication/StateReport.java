package tp.server.communication;

import tp.server.structural.Pawn;

import java.util.ArrayList;

public class StateReport extends ServerMsg {
    public int currentPlayer;
    public Pawn[] deployment;
    public int wonPlayer = 0;

    public StateReport(int currentPlayer, ArrayList<Pawn> pawns, int wonPlayer) {
        msgType = "gameState";
        deployment = new Pawn[pawns.size()];
        deployment = pawns.toArray(deployment);
        this.currentPlayer = currentPlayer;
        this.wonPlayer = wonPlayer;
    }
}
