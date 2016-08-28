package tree.algorithms.tcpCompose;

import scheduling.Alternatives;
import scheduling.Job;
import thirdParty.TcpComposeCommunicator;
import tree.structure.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by maheedhar on 8/27/16.
 */
public class TCPComposeWithFullOrderGreedyCoverage extends TCPCompose{

    public TCPComposeWithFullOrderGreedyCoverage(TcpComposeCommunicator prefEvaluatorInterface, HashMap<String, String> configValues) {
        super(prefEvaluatorInterface,configValues);
    }

    public Path chooseNextToExpand(ArrayList<Job> jobList){
        ArrayList<Path> nonDominatedSet= crisnerPathReasoner.returnNonDominatedSet(frontier.getCurrentFrontier());
        HashMap<Integer,HashSet<Path>> rankedNonDominatedSet = new HashMap<>();
        if(nonDominatedSet.size() > 1){
            int levelToExplore = 0;
            for (Path path : nonDominatedSet){
                if(path.getWorkingLevel() > levelToExplore){
                    levelToExplore = path.getWorkingLevel();
                }
            }
            //The qualifying jobs are only those weaknesses that have already been serviced and the weakness in the one step look ahead. Should not look at the whole order of weaknesses.
            //levelToExplore is the level of the one step look ahead
            HashSet<Job> qualifyingJobs = new HashSet<>(jobList);

            //we only look at the coverage of the response at the node that is about to be expanded.
            for(Path path : nonDominatedSet){
                Alternatives responseAtCurrentLevel = (Alternatives)path.getCurrentNode().getNode_representation();
                HashSet<Job> covered = (HashSet<Job>) tcpComposeCommunicator.getCoverage(responseAtCurrentLevel);
                HashSet<Path> members = rankedNonDominatedSet.get(covered.size());
                if(members == null){
                    members = new HashSet<>();
                }
                members.add(path);
                rankedNonDominatedSet.put(covered.size(),members);
            }
            int highest = 0;
            for(Integer val : rankedNonDominatedSet.keySet()){
                if(val > highest){
                    highest = val;
                }
            }
            Path path = null;
            int coverage = 0;
            for(Path ex : rankedNonDominatedSet.get(highest)){
                if(ex.getCovered().size() > coverage){
                    coverage = ex.getCovered().size();
                    path = ex;
                }
                if (ex.getCovered().size() == coverage && ex.getCovered().size()!=0) {
                    if (ex.getNumberOfResponses() < path.getNumberOfResponses()) {
                        path = ex;
                    }
                }else if(ex.getCovered().size()==0){
                    path = ex;
                }
            }
            return path;
        }else {
            return nonDominatedSet.get(0);
        }
    }
}
