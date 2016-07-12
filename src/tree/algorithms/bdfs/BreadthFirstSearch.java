package tree.algorithms.bdfs;

import reasoner.CrisnerPathReasoner;
import scheduling.Alternatives;
import scheduling.Job;
import scheduling.WorkList;
import tree.algorithms.AbstractAlgorithm;
import tree.structure.Node;
import tree.structure.Path;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by maheedhar on 6/9/16.
 */
public class BreadthFirstSearch extends AbstractAlgorithm{

    HashSet<Path> resultSet;

    public BreadthFirstSearch(){
        super(null);
        resultSet = new HashSet<>();
    }

    public void findOptimalCompositions(WorkList workList, ArrayList<Job> orderedList){
        Alternatives<String> startalt = new Alternatives<>("StartNode",6);
        Node<String> startNode = new Node(startalt,0);
        startNode.setStartNode();
        ArrayList<Node> temp = new ArrayList<Node>();
        temp.add(startNode);
        frontier.addElement(new Path(temp,0));
        while(!frontier.isEmpty()){
            Path candidate = chooseNextToExpand();
            frontier.removeFromFrontier(candidate);
            if(!exploreNextLevel(candidate,orderedList,workList)){
                resultSet.add(candidate);
            }
        }
        ArrayList<Path> orderedBaseCaseResults = CrisnerPathReasoner.returnPathOrder(resultSet);
        for(Path path : orderedBaseCaseResults){
            System.out.println("#####The following solution was added to result set####");
            path.printPath(orderedList);
        }
    }

    public boolean exploreNextLevel(Path toBeExpandedPath,ArrayList<Job> orderedList,WorkList workList){
        // to get the level of the last node in the path
        int levelToExplore = toBeExpandedPath.getWorkingLevel(); //because start node is level 0 and actual job starts from level 1.
        // but the first job in ordered list will be in index 0
        if(orderedList.size() > levelToExplore){
            Job newJob = orderedList.get(levelToExplore);
            ArrayList<Alternatives> newAlternatives = workList.getAlternativesList(newJob);
            //creating new frontier entries with expandedPath
            for(Alternatives alternative : newAlternatives){
                createNewPath(toBeExpandedPath,alternative);
            }
            return true;
        }
        else
            return false;
    }

    public Path chooseNextToExpand(){
        return frontier.getCurrentFrontier()[0];
        //choose the best one to expand,so first one is chosen because this is breadth first search
    }

}
