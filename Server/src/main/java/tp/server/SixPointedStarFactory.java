package tp.server;

import java.util.ArrayList;
import java.util.Iterator;

public class SixPointedStarFactory implements MapFactory{
    Map map;
    @Override
    public Map createMap(int numOfPLayers) {
        map = new Map();

        for (int x = -4; x <= 8; x++) {
            for (int y = -4; y <= 4 - x; y++) {
                map.addField(new Field(x, y, -(x + y)));
            }
        }
        for (int x = -8; x <= 4; x++) {
            for (int y = 4; y >= -4 - x; y--) {
                if ((Math.abs(x) + Math.abs(y)) > 4) {
                    map.addField(new Field(x, y, -(x + y)));
                }
            }
        }
        setupCorners(numOfPLayers);
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

        if ((playerId == 4 && numOfPlayers == 4) || (playerId == 5 && numOfPlayers == 6)) {
            for (int x = -8; x < -4; x++) {
                for (int y = 4; y > -4 - x; y--) {
                    pawns.add(new Pawn(map.getField(x, y, -(x + y)), playerId));
                }
            }
            return pawns;
        }

        if ((playerId == 2 && numOfPlayers == 4) || (playerId == 6 && numOfPlayers == 6)) {
            for (int x = -4; x < 0; x++) {
                for (int y = 5; y < 4 - x; y++) {
                    pawns.add(new Pawn(map.getField(x, y, -(x + y)), playerId));
                }
            }
            return pawns;
        }

        return null;
    }

    private void setupCorners(final int numOfPLayers) {

        Iterator<Field> it;
        switch (numOfPLayers) {
            case 2:
                it = map.getMatchingFields(new Filter<Field> () {
                    @Override
                    public boolean match(Field field) {
                        return field.getCorner() == Field.Corners.TOP ||
                                field.getCorner() == Field.Corners.BOTTOM;
                    }
                });

                while(it.hasNext()) {
                    Field f = it.next();
                    f.setPlayerHome(f.getCorner() == Field.Corners.TOP ? 1 : 2);
                    f.setPlayerGoal(f.getCorner() == Field.Corners.TOP ? 2 : 1);
                }
                break;
            case 3:
                it = map.getFields().iterator();

                while(it.hasNext()) {
                    Field f = it.next();
                    f.setPlayerHome(f.getCorner() == Field.Corners.TOP  ? 1 :
                            (f.getCorner() == Field.Corners.BOTTOM_RIGHT ? 2 : 3));
                    f.setPlayerGoal(f.getCorner() == Field.Corners.TOP_LEFT  ? 2 :
                            (f.getCorner() == Field.Corners.TOP_RIGHT ? 3 : 1));
                }

                break;
            case 4:
                it = map.getMatchingFields(new Filter<Field> () {
                    public boolean match(Field field) {
                        return field.getCorner() != Field.Corners.BOTTOM &&
                                field.getCorner() != Field.Corners.TOP;
                    }
                });

                while(it.hasNext()) {
                    Field f = it.next();
                    f.setPlayerHome(f.getCorner() == Field.Corners.TOP_LEFT  ? 1 :
                            (f.getCorner() == Field.Corners.TOP_RIGHT ? 2 :
                                    (f.getCorner() == Field.Corners.BOTTOM_RIGHT ? 3 : 4)));
                    f.setPlayerGoal(f.getCorner() == Field.Corners.TOP_LEFT  ? 3 :
                            (f.getCorner() == Field.Corners.TOP_RIGHT ? 4 :
                                    (f.getCorner() == Field.Corners.BOTTOM_RIGHT ? 1 : 2)));
                }
                break;
            case 6:
                it = map.getFields().iterator();

                while(it.hasNext()) {
                    Field f = it.next();
                    f.setPlayerHome(f.getCorner() == Field.Corners.TOP  ? 1 :
                            (f.getCorner() == Field.Corners.TOP_RIGHT ? 2 :
                                    (f.getCorner() == Field.Corners.BOTTOM_RIGHT ? 3 :
                                            (f.getCorner() == Field.Corners.BOTTOM_LEFT ? 4 :
                                                    (f.getCorner() == Field.Corners.BOTTOM ? 5 : 6)))));

                    f.setPlayerGoal(f.getPlayerHome() + 3 % 6);
                }
                break;
        }
    }


}
