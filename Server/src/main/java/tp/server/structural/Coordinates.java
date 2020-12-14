package tp.server.structural;

/**
 * Simple data structure to represent cube coordinates
 */
public class Coordinates {
    public int x;
    public int y;
    public int z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinates() {}

    @Override
    public boolean equals(Object obj) {
        return ((Coordinates)obj).x == x &&
                ((Coordinates)obj).y == y &&
                ((Coordinates)obj).z == z;
    }
}
