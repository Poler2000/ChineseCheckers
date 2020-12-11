package tp.server;

public class Pawn {
    private Field location;
    private final int owner;
    private final int id;

    private static final Object locker = new Object();
    private static int id_counter = 0;

    public Pawn(final Field field, final int owner) {
        id = assignId();
        location = field;
        this.owner = owner;
        location.setOccupied(true);
    }

    public Field getLocation() {
        return location;
    }

    public int getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public void move(Field newLocation) {
        location.setOccupied(false);
        location = newLocation;
        location.setOccupied(true);
    }

    private static int assignId() {
        synchronized (locker) {
            return id_counter++;
        }
    }
}
