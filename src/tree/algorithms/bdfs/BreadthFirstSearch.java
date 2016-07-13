package tree.algorithms.bdfs;

import reasoner.CrisnerReasoner;
import scheduling.Alternatives;
import scheduling.Job;
import scheduling.WorkList;
import tree.algorithms.AbstractAlgorithm;
import tree.algorithms.tcpCompose.helpers.WorstFrontierCalculator;
import tree.structure.Node;
import tree.structure.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by maheedhar on 6/9/16.
 */
public class BreadthFirstSearch extends AbstractAlgorithm{

    //The enormous result set size is justified, do a multiplication of all possible responses possible at each level
    private HashSet<Path> resultSet;
    private CrisnerReasoner crisnerPathReasoner;
    private HashMap<String, String> configValues;

    public BreadthFirstSearch(HashMap<String, String> configValues){
        super(new WorstFrontierCalculator(configValues.get("NegativeImpactValueOrderlocation")));
        this.configValues = configValues;
        crisnerPathReasoner = new CrisnerReasoner(configValues.get("NuSMVLocation"),configValues.get("NegativeImpactPrefOrderlocation"));
        this.configValues.remove("NegativeImpactValueOrderlocation");
        this.configValues.remove("NegativeImpactPrefOrderlocation");
        this.configValues.remove("OrganizationalCIAPrefOrderlocation");
        this.configValues.remove("NuSMVLocation");
        resultSet = new HashSet<>();
    }

    private HashMap<String,String> getPreferedValues() {
        HashMap<String,String> result = new HashMap<>(configValues);
        return result;
    }

    private HashMap<String,String> initialiseValuation(HashMap<String, String> startingBetaMostPreferedCompletion) {
        HashMap<String,String> result = new HashMap<>();
        for(String key : startingBetaMostPreferedCompletion.keySet()){
            result.put(key,"NULL");
        }
        return result;
    }

    public void findOptimalCompositions(WorkList workList, ArrayList<Job> orderedList){
        HashMap<String,String> startingBetaMostPreferedCompletion = getPreferedValues();
        HashMap<String,String> startingPreferenceValuation = initialiseValuation(startingBetaMostPreferedCompletion);
        Alternatives<String> startalt = new Alternatives<>("StartNode",6);
        Node<String> startNode = new Node(startalt,0);
        startNode.setStartNode();
        ArrayList<Node> temp = new ArrayList<Node>();
        temp.add(startNode);
        Path tempPath = new Path(temp,0);
        tempPath.setBetaMostPreferedCompletion(startingBetaMostPreferedCompletion);
        tempPath.setPreferenceValuation(startingPreferenceValuation);
        frontier.addElement(tempPath);
        while(!frontier.isEmpty()){
            Path candidate = chooseNextToExpand();
            frontier.removeFromFrontier(candidate);
            if(!exploreNextLevel(candidate,orderedList,workList)){
                resultSet.add(candidate);
            }
        }
        ArrayList<Path> orderedBaseCaseResults = crisnerPathReasoner.returnOrder(resultSet);
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
