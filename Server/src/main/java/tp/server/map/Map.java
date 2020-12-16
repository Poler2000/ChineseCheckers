package tp.server.map;


import tp.server.structural.Coordinates;
import tp.server.structural.Field;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Wrapper over array of fields.
 * Allows to add new fields and get existing ones
 */
public class Map {
    private ArrayList<Field> fields = new ArrayList<Field>();

    public Iterator<Field> getMatchingFields(final Filter<Field> filter) {
        ArrayList<Field> result = new ArrayList<Field>();
        for (Field field : fields)
            if (filter.match(field))
                result.add(field);
        return result.iterator();
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public Field getField(final int x, final int y, final int z) {
        for(Field field: fields) {
            if(field.coordinatesAsXYZ().x == x &&
                field.coordinatesAsXYZ().y == y &&
                field.coordinatesAsXYZ().z == z) {
                return field;
            }
        }
        return null;
    }

    public Field getField(final Coordinates coord) {
        for(Field field: fields) {
            if(field.coordinatesAsXYZ().equals(coord)) {
                return field;
            }
        }
        return null;
    }

    public void addField(final Field field) {
        fields.add(field);
    }

    public int numOfFields() {
        return fields.size();
    }
}
