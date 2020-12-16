package tp.server.structural;

import tp.server.structural.Step;

import java.util.ArrayList;

/**
 * Sequence of connected steps
 */
public class Move {
    private ArrayList<Step> steps = new ArrayList<Step>();

    public void addStep(final Step step) {
        steps.add(step);
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }
}
