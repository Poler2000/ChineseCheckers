package tp.server.map;

import tp.server.structural.Field;
import tp.server.structural.Pawn;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Creates map with six printed star shape.
 * It supports 2,3,4 or 6 players
 */
public class SixPointedStarFactory implements MapFactory{
    private Map map;

    @Override
    public Map createMap(final int numOfPLayers) {
        map = createEmptyMap();

        setupCorners(numOfPLayers);
        return map;
    }

    @Override
    public Map createEmptyMap() {
        map = new Map();

        // center, top-left, top-right and bottom corner of map
        for (int x = -4; x <= 8; x++) {
            for (int y = -4; y <= 4 - x; y++) {
                map.addField(new Field(x, y, -(x + y)));
            }
        }

        // bottom,-left, bottom-right and top corner of map
        for (int x = -8; x <= 4; x++) {
            for (int y = 4; y >= -4 - x; y--) {
                if ((Math.abs(x) > 4 ||  Math.abs(y) > 4) || x + y > 4) {
                    map.addField(new Field(x, y, -(x + y)));
                }
            }
        }

        return map;
    }

    @Override
    public ArrayList<Pawn> createPawns(int playerId, int numOfPlayers) {
        ArrayList<Pawn> pawns = new ArrayList<>();

        if (playerId == 1 && numOfPlayers != 4) {
            for (int x = 1; x <= 4; x++) {
                for (int y = 4; y > 4 - x; y--) {
                    pawns.add(new Pawn(map.getField(x, y, -(x + y)), playerId));
                }
            }
            return pawns;
        }

        if (playerId == 2 && (numOfPlayers == 4 || numOfPlayers == 6)) {
            for (int x = 5; x <= 8; x++) {
                for (int y = -4; y <= 4 - x; y++) {
                    pawns.add(new Pawn(map.getField(x, y, -(x + y)), playerId));
                }
            }
            return pawns;
        }

        if ((playerId == 2 && numOfPlayers == 3) ||
                (playerId == 3 && (numOfPlayers == 4 || numOfPlayers == 6))) {
            for (int x = 1; x <= 4; x++) {
                for (int y = -5; y >= -4 - x; y--) {
                    pawns.add(new Pawn(map.getField(x, y, -(x + y)), playerId));
                }
            }
            return pawns;
        }

        if ((playerId == 2 && numOfPlayers == 2) || (playerId == 4 && numOfPlayers == 6)) {
            for (int x = -4; x < 0; x++) {
                for (int y = -4; y < -4 - x; y++) {
                    pawns.add(new Pawn(map.getField(x, y, -(x + y)), playerId));
                }
            }
            return pawns;
        }

        if ((playerId == 4 && numOfPlayers == 4) || (playerId == 5 && numOfPlayers == 6)
                || (playerId == 3 && numOfPlayers == 3)) {
            for (int x = -8; x < -4; x++) {
                for (int y = 4; y >= -4 - x; y--) {
                    pawns.add(new Pawn(map.getField(x, y, -(x + y)), playerId));
                }
            }
            return pawns;
        }

        if ((playerId == 1 && numOfPlayers == 4) || (playerId == 6 && numOfPlayers == 6)) {
            for (int x = -4; x < 0; x++) {
                for (int y = 5; y <= 4 - x; y++) {
                    pawns.add(new Pawn(map.getField(x, y, -(x + y)), playerId));
                }
            }
            return pawns;
        }

        return null;
    }

    /**
     * Sets correct goals for given number of players
     * @param numOfPLayers
     */
    private void setupCorners(final int numOfPLayers) {
        Iterator<Field> it;
        switch (numOfPLayers) {
            case 2:
                it = map.getMatchingFields(new Filter<Field>() {
                    @Override
                    public boolean match(Field field) {
                        return Corners.determineCorner(field.coordinatesAsXYZ()) == Corners.TOP ||
                                Corners.determineCorner(field.coordinatesAsXYZ()) == Corners.BOTTOM;
                    }
                });

                while(it.hasNext()) {
                    Field f = it.next();
                    f.setPlayerGoal(Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.TOP ? 2 : 1);
                }
                break;
            case 3:
                it = map.getMatchingFields(new Filter<Field>() {
                    @Override
                    public boolean match(Field field) {
                        return Corners.determineCorner(field.coordinatesAsXYZ()) == Corners.TOP_LEFT ||
                                Corners.determineCorner(field.coordinatesAsXYZ()) == Corners.BOTTOM ||
                                Corners.determineCorner(field.coordinatesAsXYZ()) == Corners.TOP_RIGHT;
                    }
                });

                while(it.hasNext()) {
                    Field f = it.next();
                    f.setPlayerGoal(Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.TOP_LEFT  ? 2 :
                            (Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.TOP_RIGHT ? 3 : 1));
                }

                break;
            case 4:
                it = map.getMatchingFields(new Filter<Field> () {
                    public boolean match(Field field) {
                        return Corners.determineCorner(field.coordinatesAsXYZ()) != Corners.BOTTOM &&
                                Corners.determineCorner(field.coordinatesAsXYZ()) != Corners.TOP;
                    }
                });

                while(it.hasNext()) {
                    Field f = it.next();
                    f.setPlayerGoal(Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.TOP_LEFT  ? 3 :
                            (Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.TOP_RIGHT ? 4 :
                            (Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.BOTTOM_RIGHT ? 1 : 2)));
                }
                break;
            case 6:
                it = map.getFields().iterator();

                while(it.hasNext()) {
                    Field f = it.next();
                    f.setPlayerGoal(Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.TOP ? 4 :
                            (Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.TOP_RIGHT ? 5 :
                            (Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.BOTTOM_RIGHT ? 6 :
                            (Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.BOTTOM ? 1 :
                            (Corners.determineCorner(f.coordinatesAsXYZ()) == Corners.BOTTOM_LEFT ? 2 : 3)))));
                }
                break;
        }
    }


}
