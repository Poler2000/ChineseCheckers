package tp.server.logic;

import tp.server.Map;
import tp.server.structural.Coordinates;
import tp.server.structural.Move;
import tp.server.structural.Step;

import java.util.Iterator;

public class MoveValidator {
    private Map map;

    public MoveValidator(final Map map) {
        this.map = map;
    }

    public boolean Validate(final Move move) {
        Iterator<Step> steps = move.getSteps().iterator();
        
        if (steps.hasNext()) {
            if (isNeighbourValid(steps.next())) {
                if (!steps.hasNext()) {
                    return true;
                }
                while (steps.hasNext()) {
                    Step step = steps.next();
                    if (!isJumpValid(step)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean isNeighbourValid(Step step) {
        final Coordinates pCoord = step.getActor().getLocation().coordinatesAsXYZ();
        final Coordinates lCoord = step.getDestination().coordinatesAsXYZ();
        return (Math.abs(pCoord.x - lCoord.x) + Math.abs(pCoord.y - lCoord.y) +
                Math.abs(pCoord.z - lCoord.z)) == 2 && (!step.getDestination().isOccupied());
    }

    private boolean isJumpValid(Step step) {
        final Coordinates pCoord = step.getActor().getLocation().coordinatesAsXYZ();
        final Coordinates lCoord = step.getDestination().coordinatesAsXYZ();
        if ((Math.abs(pCoord.x - lCoord.x) + Math.abs(pCoord.y - lCoord.y) +
                Math.abs(pCoord.z - lCoord.z)) == 4) {
            if ((Math.abs(pCoord.x - lCoord.x) == 0) || (Math.abs(pCoord.y - lCoord.y) == 0) ||
                    (Math.abs(pCoord.z - lCoord.z) == 0)) {
                return (!map.getField(lCoord).isOccupied() && map.getField(
                        (pCoord.x + lCoord.x) / 2,
                        (pCoord.y + lCoord.y) / 2,
                        (pCoord.z + lCoord.z) / 2
                ).isOccupied());
            }
        }
        return false;
    }
}
