package tree.traversal;

import reasoner.MockPreferenceReasoner;
import scheduling.Alternatives;
import scheduling.Job;
import scheduling.WorkList;
import tree.structure.Node;

import java.util.ArrayList;

/**
 * Created by maheedhar on 5/23/16.
 */
public class TCPCompose {

    //todo make sure you start from a start node and end in a goal node..must have some way of defining them
    public void findOptimalCompositions(WorkList workList, ArrayList<Job> orderedList){
        Alternatives<String> startalt = new Alternatives<>("StartNode",6);
        Node<String> startNode = new Node(startalt,0);
        startNode.setStartNode();
//        Alternatives<String> goalalt = new Alternatives<>("GoalNode",6);
//        Node<String> goalNode = new Node(goalalt,orderedList.size()+1);
//        goalNode.setEndNode();
        Frontier frontier = new Frontier();
        ResultSet<ArrayList<Node>> resultSet = new ResultSet();
        ArrayList<Node> temp = new ArrayList<Node>();
        temp.add(startNode);
        frontier.addElement(temp);
        //todo is there any other condition than empty frontier to end the loop?
        while(!frontier.isEmpty()){
            ArrayList<Node> candidate = chooseNextToExpand(frontier);
            frontier.removeFromFrontier(candidate);
            if(!exploreNextLevel(frontier,candidate,orderedList,workList)){
                //we have reached goal node in one of the frontiers
                if(resultSet.addElementToResultSet(candidate)){
                    System.out.println("The new solution was added to result set");
                }else{
                    System.out.println("The new solution was NOT added to result set since it was not a non dominating set");
                }

            }
        }
    }

    public boolean exploreNextLevel(Frontier frontier,ArrayList<Node> toBeExpandedPath,ArrayList<Job> orderedList,WorkList workList){
        //todo verify these operations
        int depthOfExpandedPath = toBeExpandedPath.get(toBeExpandedPath.size()-1).getLevel();// to get the level of the last node in the path
        int levelToExplore = depthOfExpandedPath; //because start node is level 0 and actual job starts from level 1.
        // but the first job in ordered list will be in index 0
        if(orderedList.size() > levelToExplore){
            Job newJob = orderedList.get(levelToExplore);
            ArrayList<Alternatives> newAlternatives = workList.getAlternativesList(newJob);
            //creating new frontier entries with expandedPath
            for(Alternatives alternative : newAlternatives){
                Node newNode = new Node(alternative,depthOfExpandedPath+1);
                ArrayList<Node> newPath = new ArrayList<>(toBeExpandedPath);
                newPath.add(newNode);
                frontier.addElement(newPath);
            }
            return true;
        }
        else
            return false;
    }

    public ArrayList chooseNextToExpand(Frontier frontier){
        return MockPreferenceReasoner.returnOrder(frontier.getCurrentFrontier()).get(0); //choose the best one to expand,so first one is chosen
    }
}
