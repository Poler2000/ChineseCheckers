package tp.server;


import java.util.ArrayList;
import java.util.Iterator;

public class Map {
    private ArrayList<Field> fields = new ArrayList<Field>();

    public void createMap(final int numOfPLayers) {
        for (int x = -4; x <= 8; x++) {
            for (int y = -4; y <= 4 - x; y++) {
                fields.add(new Field(x, y, -(x + y)));
            }
        }
        for (int x = -8; x <= 4; x++) {
            for (int y = 4; y >= -4 - x; y--) {
                if ((Math.abs(x) + Math.abs(y)) > 4) {
                    fields.add(new Field(x, y, -(x + y)));
                }
            }
        }
    }

    public Iterator<Field> getMatchingFields(Filter<Field> filter) {
        ArrayList<Field> result = new ArrayList<Field>();
        for (Field field : fields)
            if (filter.match(field))
                result.add(field);
        return result.iterator();
    }

    public Iterator<Field> getAllFields() {
        return fields.iterator();
    }

    public Field getField(final int x, final int y, final int z) {
        for(Field field: fields) {
            if(field.getCoord().x == x &&
                field.getCoord().y == y &&
                field.getCoord().z == z) {
                return field;
            }
        }
        return null;
    }

    public Field getField(Coordinates coord) {
        for(Field field: fields) {
            if(field.getCoord().equals(coord)) {
                return field;
            }
        }
        return null;
    }

    public void addField(Field field) {
        fields.add(field);
    }
}
