package tp.server.map;

import tp.server.structural.Pawn;

import java.util.ArrayList;

/**
 * Used to create different maps and spawn pawns
 */
public interface MapFactory {
    public Map createMap(final int numOfPLayers);
    public Map createEmptyMap();
    public ArrayList<Pawn> createPawns(final int playerId, final int numOfPlayers);
}
