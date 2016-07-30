package tree.algorithms;

import scheduling.Job;
import scheduling.WorkList;
import tree.structure.Path;

import java.util.ArrayList;

/**
 * Created by maheedhar on 6/15/16.
 */
public interface Algorithm {

    public ArrayList<Path> findOptimalCompositions(WorkList workList, ArrayList<Job> orderedList);

    public boolean exploreNextLevel(Path toBeExpandedPath, ArrayList<Job> orderedList, WorkList workList);

    public Path chooseNextToExpand(ArrayList<Job> jobList);

}
