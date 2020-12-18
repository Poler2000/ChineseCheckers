package tp.server.structural;

import java.util.Arrays;

/**
 * Representation of single field on the map
 */
public class Field implements Cloneable {
    //private final coords coord;
    private final int id;
    private final int coords[];
    private int playerGoal;

    private boolean occupied;

    private static final Object locker = new Object();
    private static int id_counter = 0;

    public Field(final int x, final int y, final int z) {
        id = assignId();
        coords = new int[] {x,y,z};
        playerGoal = 0;
        occupied = false;
    }

    public Field() {
       coords = new int[] {0,0,0};
       id = assignId();
    }

    public Coordinates coordinatesAsXYZ() {
        return new Coordinates(coords[0], coords[1], coords[2]);
    }

    public int[] getCoords() {
        return coords;
    }

    public int getGoalof() {
        return playerGoal;
    }

    public void setPlayerGoal(final int goal) {
        playerGoal = goal;
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

    public Field clone(){
        try{
            return (Field) super.clone();
        }
        catch (CloneNotSupportedException ex){
            return null;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        Field f = (Field)obj;
        return Arrays.equals(coords, f.coords) &&
                id == f.id &&
                this.playerGoal == f.playerGoal;
    }
}
