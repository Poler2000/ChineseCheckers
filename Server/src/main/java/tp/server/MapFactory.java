package tp.server;

import tp.server.structural.Pawn;

import java.util.ArrayList;

public interface MapFactory {
    public Map createMap(final int numOfPLayers);
    public ArrayList<Pawn> createPawns(final int playerId, final int numOfPlayers);
}
