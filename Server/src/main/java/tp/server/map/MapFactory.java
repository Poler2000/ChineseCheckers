package tp.server.map;

import tp.server.structural.Pawn;

import java.util.ArrayList;

/**
 * Used to create different maps and spawn pawns
 */
public interface MapFactory {
    Map createMap(final int numOfPLayers);
    Map createEmptyMap();
    ArrayList<Pawn> createPawns(final int playerId, final int numOfPlayers);
}
