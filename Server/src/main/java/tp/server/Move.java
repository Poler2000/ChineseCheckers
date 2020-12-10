package tp.server;

import java.util.ArrayList;
import java.util.Iterator;

public class Move {
    private ArrayList<Step> steps = new ArrayList<Step>();

    public void addStep(final Step step) {
        steps.add(step);
    }

    public Iterator<Step> getStepsIterator() {
        return steps.iterator();
    }
}
