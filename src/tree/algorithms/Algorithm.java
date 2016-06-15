package tree.algorithms;

import scheduling.Alternatives;
import scheduling.Job;
import scheduling.WorkList;
import tree.structure.Frontier;
import tree.structure.Path;

import java.util.ArrayList;

/**
 * Created by maheedhar on 6/15/16.
 */
public interface Algorithm {

    public void findOptimalCompositions(WorkList workList, ArrayList<Job> orderedList);

    public boolean exploreNextLevel(Path toBeExpandedPath, ArrayList<Job> orderedList, WorkList workList);

    public Path chooseNextToExpand();

}
