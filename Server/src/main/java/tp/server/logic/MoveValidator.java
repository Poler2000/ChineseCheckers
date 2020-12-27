package tp.server.logic;

import tp.server.map.Map;
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
            Step s = steps.next();
            if (!(isNeighbourValid(s) || isNoMove(s))) {
                if (isJumpValid(s)) {
                    while (steps.hasNext()) {
                        Step step = steps.next();
                        if (!isJumpValid(s.getDestination().coordinatesAsXYZ(), step)) {
                            return false;
                        }
                        s = step;
                    }
                    return true;
                }
                return false;
            }
            else if (steps.hasNext()) {
                return false;
            }
        }

        return true;
    }

    private boolean isJumpValid(Coordinates pCoord, Step step) {
        final Coordinates lCoord = step.getDestination().coordinatesAsXYZ();
        if (isBeyondGoal(pCoord, step, lCoord)) return false;
        return isDistanceCorrect(pCoord, lCoord);
    }

    private boolean isBeyondGoal(Coordinates pCoord, Step step, Coordinates lCoord) {
        if (map.getField(pCoord).getGoalof() == step.getPawn().getOwner()) {
            if (map.getField(lCoord).getGoalof() != step.getPawn().getOwner()) {
                return true;
            }
        }
        return false;
    }

    private boolean isNoMove(Step s) {
        return s.getPawn().getLocation().equals(s.getDestination());
    }

    private boolean isNeighbourValid(Step step) {
        final Coordinates pCoord = step.getPawn().getLocation().coordinatesAsXYZ();
        final Coordinates lCoord = step.getDestination().coordinatesAsXYZ();
        if (isBeyondGoal(pCoord, step, lCoord)) return false;
        return (Math.abs(pCoord.x - lCoord.x) + Math.abs(pCoord.y - lCoord.y) +
                Math.abs(pCoord.z - lCoord.z)) == 2 && (!step.getDestination().isOccupied());
    }

    private boolean isJumpValid(Step step) {
        final Coordinates pCoord = step.getPawn().getLocation().coordinatesAsXYZ();
        final Coordinates lCoord = step.getDestination().coordinatesAsXYZ();
        if (isBeyondGoal(pCoord, step, lCoord)) return false;
        return isDistanceCorrect(pCoord, lCoord);
    }

    private boolean isDistanceCorrect(Coordinates pCoord, Coordinates lCoord) {
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
