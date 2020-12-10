package tp.server;

import java.util.Iterator;

public class MoveValidator {
    public boolean Validate(final Move move) {
        Iterator<Step> steps = move.getStepsIterator();

        while (steps.hasNext()) {
            Step step = steps.next();
            if () {

            } else if () {

            }
            else {
                return false;
            }
        }
    }

    private boolean isNeighbour(Step step) {
        final Coordinates pCoord = step.getPiece().getLocation().getCoord();
        final Coordinates lCoord = step.getDestination().getCoord();
        return (Math.abs(pCoord.x - lCoord.x) + Math.abs(pCoord.y - lCoord.y) +
                Math.abs(pCoord.z - lCoord.z)) == 2;
    }
}
