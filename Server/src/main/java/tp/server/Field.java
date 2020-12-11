package tp.server;

/**
 * Representation of single field on the map
 */
public class Field {
    private final Coordinates coord;
    private int playerHome;
    private int playerGoal;
    enum Corners {
        NONE,
        TOP,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        BOTTOM
    }
    //private final int id;
    private boolean occupied;
    private Corners corner;

    private static final Object locker = new Object();
    private static int id_counter = 0;

    public Field(final int x, final int y, final int z) {
       // id = assignId();
        coord = new Coordinates(x, y, z);
        playerHome = 0;
        playerGoal = 0;
        corner = determineCorner();
        occupied = false;
    }
    /*
    public Field(final int x, final int y, final int z,
                 final int playerHome, final int playerGoal, final boolean occupied) {
       // id = assignId();
        coord = new Coordinates(x, y, z);
        this.playerHome = playerHome;
        this.playerGoal = playerGoal;
        corner = determineCorner();
        //this.occupied = occupied;
    }*/

    public Coordinates getCoord() {
        return coord;
    }

    public int getPlayerHome() {
        return playerHome;
    }

    public int getPlayerGoal() {
        return playerGoal;
    }

    public void setPlayerHome(final int home) {
        playerHome = home;
    }

    public void setPlayerGoal(final int goal) {
        playerGoal = goal;
    }

    /*public int getId() {
        return id;
    }*/

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(final boolean status) {
        occupied = status;
    }

   /* private static int assignId() {
        synchronized (locker) {
            return id_counter++;
        }
    }*/

    private Corners determineCorner() {
        if (coord.z < -4) {
            return Corners.TOP;
        }
        if (coord.z > 4) {
           return Corners.BOTTOM;
        }
        if (coord.x < -4) {
            return Corners.BOTTOM_LEFT;
        }
        if (coord.x > 4) {
            return Corners.TOP_RIGHT;
        }
        if (coord.y < -4) {
            return Corners.BOTTOM_RIGHT;
        }
        if (coord.y > 4) {
            return Corners.TOP_LEFT;
        }
        return Corners.NONE;
    }

    public Corners getCorner() {
        return corner;
    }
}
