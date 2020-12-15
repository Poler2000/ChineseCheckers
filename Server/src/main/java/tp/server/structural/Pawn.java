package tp.server.structural;

public class Pawn implements Cloneable {
    private final int id;
    private Field location;
    private final int playerId;

    private static final Object locker = new Object();
    private static int id_counter = 0;

    public Pawn(final Field field, final int playerId) {
        location = field;
        this.playerId = playerId;
        location.setOccupied(true);
        id = assignId();
    }

    public Pawn() {
        playerId = 0;
        id = assignId();
    }

    public Field getLocation() {
        return location;
    }

    public int getPlayerId() {
        return playerId;
    }

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
}
