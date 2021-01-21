package tp.server.structural;

import java.util.Arrays;

/**
 * Single pawn with it's location id and ownerId
 */
public class Pawn implements Cloneable {
    private final int id;
    private Field location;
    private final int owner;

    private static final Object locker = new Object();
    private static int id_counter = 0;

    public Pawn(final Field field, final int playerId) {
        location = field;
        this.owner = playerId;
        location.setOccupied(true);
        id = assignId();
    }

    public Field getLocation() {
        return location;
    }

    public int getOwner() {
        return owner;
    }

    /**
     * changes current location to another
     * @param newLocation new location to move to
     */
    public void move(Field newLocation) {
        location.setOccupied(false);
        location = newLocation;
        location.setOccupied(true);
    }

    public int getId() {
        return id;
    }

    private static int assignId() {
        synchronized (locker) {
            return id_counter++;
        }
    }

    public Pawn clone() {
        try {
            return (Pawn)super.clone();
        }
        catch (CloneNotSupportedException ex){
            return null;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        Pawn p = (Pawn)obj;
        return location.equals(p.location) &&
                id == p.getId() &&
                owner == p.getOwner();
    }

    public static void resetIdCounter() {
        id_counter = 0;
    }
}
