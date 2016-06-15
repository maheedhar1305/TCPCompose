package tree.bdfs;

import reasoner.MockPreferenceReasoner;
import scheduling.Alternatives;
import scheduling.Job;
import scheduling.WorkList;
import tree.structure.Frontier;
import tree.structure.Node;
import tree.structure.ResultSet;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by maheedhar on 6/9/16.
 */
public class BreadthFirstSearch {

    private HashSet<ArrayList<Node>> resultSet = new HashSet();

    public void findOptimalCompositions(WorkList workList, ArrayList<Job> orderedList){
        Alternatives<String> startalt = new Alternatives<>("StartNode",6);
        Node<String> startNode = new Node(startalt,0);
        startNode.setStartNode();
//        Alternatives<String> goalalt = new Alternatives<>("GoalNode",6);
//        Node<String> goalNode = new Node(goalalt,orderedList.size()+1);
//        goalNode.setEndNode();
        Frontier frontier = new Frontier();
        ArrayList<Node> temp = new ArrayList<Node>();
        temp.add(startNode);
        //frontier.addElement(temp);todo remove comment
        //todo is there any other condition than empty frontier to end the loop?
        while(!frontier.isEmpty()){
            ArrayList<Node> candidate = chooseNextToExpand(frontier);
            //frontier.removeFromFrontier(candidate); todo remove comment
            if(!exploreNextLevel(frontier,candidate,orderedList,workList)){
                resultSet.add(candidate);
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
            //todo need to add code if we need to skip some levels
            //some kind of flexible code that can skip levels or decide which is the job that needs to be expanded next based on some criteris
            //for example : if coverage is a parameter and it covers some layers then we need not expand those layers
            ArrayList<Alternatives> newAlternatives = workList.getAlternativesList(newJob);
            //creating new frontier entries with expandedPath
            for(Alternatives alternative : newAlternatives){
                Node newNode = new Node(alternative,depthOfExpandedPath+1);
                ArrayList<Node> newPath = new ArrayList<>(toBeExpandedPath);
                newPath.add(newNode);
                //frontier.addElement(newPath); todo remove comment
            }
            return true;
        }
        else
            return false;
    }

    public ArrayList chooseNextToExpand(Frontier frontier){
        //return frontier.getCurrentFrontier()[0]; //choose the best one to expand,so first one is chosen todo remove comment
        return null;
    }

}
