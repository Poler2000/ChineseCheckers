package tp.server;

public class Pawn {
    private int location;
    private final int owner;
    private final int id;

    private static final Object locker = new Object();
    private static int id_counter = 0;

    public Pawn(final int field, final int owner) {
        id = assignId();
        location = field;
        this.owner = owner;
    }

    public int getLocation() {
        return location;
    }

    public int getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public void move(int newLocation) {
        location = newLocation;
    }

    private static int assignId() {
        synchronized (locker) {
            return id_counter++;
        }
    }
}
