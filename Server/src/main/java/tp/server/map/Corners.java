package tp.server.map;

import tp.server.structural.Coordinates;

/**
 * Enum specifies corners of six pointed star map.
 * It is used during it's creation
 */
public enum Corners {
    NONE,
    TOP,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    BOTTOM;

    /**
     * determines corner based on given coordinates
     * @param coord coordinates to check
     * @return corner of map
     */
    public static Corners determineCorner(final Coordinates coord) {
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
