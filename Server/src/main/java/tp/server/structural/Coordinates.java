package tp.server.structural;

/**
 * Simple data structure to represent cube coordinates
 */
public class Coordinates {
    public final int x;
    public final int y;
    public final int z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinates() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Coordinates)obj).x == x &&
                ((Coordinates)obj).y == y &&
                ((Coordinates)obj).z == z;
    }
}
