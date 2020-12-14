package tp.server;

import tp.server.structural.Coordinates;

public enum Corners {
    NONE,
    TOP,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    BOTTOM;

    public static Corners determineCorner(Coordinates coord) {
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
}
