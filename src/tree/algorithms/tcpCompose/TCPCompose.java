package tree.algorithms.tcpCompose;

import reasoner.MockPreferenceReasoner;
import scheduling.Alternatives;
import scheduling.Job;
import scheduling.WorkList;
import thirdParty.TcpComposeCommunicator;
import tree.algorithms.AbstractAlgorithm;
import tree.structure.Frontier;
import tree.structure.Node;
import tree.structure.Path;
import tree.structure.ResultSet;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by maheedhar on 5/23/16.
 */
public class TCPCompose extends AbstractAlgorithm{

    private TcpComposeCommunicator tcpComposeCommunicator;
    private ResultSet resultSet ;


    public TCPCompose(TcpComposeCommunicator tcpComposeCommunicator){
        super();
        resultSet = new ResultSet();
        this.tcpComposeCommunicator = tcpComposeCommunicator;
    }

    //todo make sure you start from a start node and end in a goal node..must have some way of defining them
    public void findOptimalCompositions(WorkList workList, ArrayList<Job> orderedList){
        Alternatives<String> startalt = new Alternatives<>("StartNode",6);
        Node<String> startNode = new Node(startalt,0);
        startNode.setStartNode();
        ArrayList<Node> temp = new ArrayList<Node>();
        temp.add(startNode);
        frontier.addElement(new Path(temp,0));
        while(!frontier.isEmpty()){
            Path candidate = chooseNextToExpand();
            updateCoverage(candidate);
            frontier.removeFromFrontier(candidate); //todo check if the correct reference comes to this block
            if(!exploreNextLevel(candidate,orderedList,workList)){
                //we have reached goal node in one of the frontiers
                if(resultSet.addElementToResultSet(candidate)){
                    System.out.println("#####The following solution was added to result set####");
                }else{
                    System.out.println("####The following solution was NOT added to result set since it was not a non dominating set####");
                }
                candidate.printPath(orderedList);
            }
        }
    }

    /*It makes sense to have covered Jobs for each Path in the frontier because they may vary from branch to branch
    *as well as how far and deep each branch are expanded. They may vary from path to path
    */
    public void updateCoverage(Path candidate){
        Node current = candidate.getCurrentNode();
        if(current.getLevel()!=0){//because start node does not fall under coverage category
            Alternatives response = (Alternatives) current.getNode_representation();
            candidate.addToCoverage(tcpComposeCommunicator.getCoverage(response),response);//todo implement this method
        }
    }

    public boolean exploreNextLevel(Path toBeExpandedPath,ArrayList<Job> orderedList,WorkList workList){
        /*because start node is level 0 and actual job starts from level 1.
         * but the first job in ordered list will be in index 0
         */
        int levelToExplore = toBeExpandedPath.getWorkingLevel();
        if(orderedList.size() > levelToExplore){
            Job newJob = orderedList.get(levelToExplore);
            if (toBeExpandedPath.getCovered().contains(newJob)){
                //we create a new path and add it to the frontier which says that the level was covered by previous selection
                createNewPath(toBeExpandedPath,toBeExpandedPath.getCoveredWorkList(newJob));
                return true;
            }
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
        return MockPreferenceReasoner.returnOrder(frontier.getCurrentFrontier()).get(0); //choose the best one to expand,so first one is chosen
    }
}
