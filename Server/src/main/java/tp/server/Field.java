package tp.server;

/**
 * Representation of single field on the map
 */
public class Field {
    private final Coordinates coord;
    private int playerHome;
    private int playerGoal;
    private final int id;
    private boolean occupied;

    private static final Object locker = new Object();
    private static int id_counter = 0;

    public Field(final int x, final int y, final int z) {
        id = assignId();
        coord = new Coordinates(x, y, z);
    }

    public Field(final int x, final int y, final int z,
                 final int playerHome, final int playerGoal, final boolean occupied) {
        id = assignId();
        coord = new Coordinates(x, y, z);
        this.playerHome = playerHome;
        this.playerGoal = playerGoal;
        this.occupied = occupied;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public int getPlayerHome() {
        return playerHome;
    }

    public int getPlayerGoal() {
        return playerGoal;
    }

    public int getId() {
        return id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(final boolean status) {
        occupied = status;
    }

    private static int assignId() {
        synchronized (locker) {
            return id_counter++;
        }
    }
}
