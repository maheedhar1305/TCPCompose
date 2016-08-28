package tree.algorithms.tcpCompose;

import reasoner.CrisnerReasoner;
import scheduling.Alternatives;
import scheduling.Job;
import scheduling.Response;
import scheduling.WorkList;
import thirdParty.TcpComposeCommunicator;
import tree.algorithms.AbstractAlgorithm;
import tree.algorithms.tcpCompose.helpers.WorstFrontierCalculator;
import tree.structure.Node;
import tree.structure.Path;
import tree.structure.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by maheedhar on 5/23/16.
 */
public class TCPComposeWithGreedyCoverage extends TCPCompose{

    public TCPComposeWithGreedyCoverage(TcpComposeCommunicator prefEvaluatorInterface, HashMap<String, String> configValues) {
        super(prefEvaluatorInterface,configValues);
    }

    public Path chooseNextToExpand(ArrayList<Job> jobList) {
        ArrayList<Path> nonDominatedSet = crisnerPathReasoner.returnNonDominatedSet(frontier.getCurrentFrontier());
        HashMap<Integer, HashSet<Path>> rankedNonDominatedSet = new HashMap<>();
        if (nonDominatedSet.size() > 1) {
            int levelToExplore = 0;
            for (Path path : nonDominatedSet) {
                if (path.getWorkingLevel() > levelToExplore) {
                    levelToExplore = path.getWorkingLevel();
                }
            }
            //The qualifying jobs are only those weaknesses that have already been serviced and the weakness in the one step look ahead. Should not look at the whole order of weaknesses.
            //levelToExplore is the level of the one step look ahead
            HashSet<Job> qualifyingJobs = new HashSet<>();
            for (int i = 0; i <= levelToExplore; i++) {
                if (i < jobList.size()) {
                    qualifyingJobs.add(jobList.get(i));
                }
            }
            //we only look at the coverage of the response at the node that is about to be expanded.
            for (Path path : nonDominatedSet) {
                Alternatives responseAtCurrentLevel = (Alternatives) path.getCurrentNode().getNode_representation();
                HashSet<Job> covered = (HashSet<Job>) tcpComposeCommunicator.getCoverage(responseAtCurrentLevel);
                HashSet<Job> intersection = new HashSet<Job>(qualifyingJobs); // use the copy constructor
                intersection.retainAll(covered);
                HashSet<Path> members = rankedNonDominatedSet.get(intersection.size());
                if (members == null) {
                    members = new HashSet<>();
                }
                members.add(path);
                rankedNonDominatedSet.put(intersection.size(), members);
            }
            int highest = 0;
            for (Integer val : rankedNonDominatedSet.keySet()) {
                if (val > highest) {
                    highest = val;
                }
            }
            //If there is a TIE in the responses with highest coverage, choose the response that has coverage on the look ahead weakness!
            //This may not be the best decision.. we can play with this!!
            Path path = null;
            int coverage = 0;
            //We check if levelToExplore is less than joblist.size because if thats the case, then we have already reached the leaf and we dont have anything more to check
            for (Path ex : rankedNonDominatedSet.get(highest)) {
                //In case there are multiple paths that have the highest coverage of jobs for the bottom most response
                if (ex.getCovered().size() > coverage) {
                    //choose the path with the most coverage on current known job list
                    coverage = ex.getCovered().size();
                    path = ex;
                }
                if (ex.getCovered().size() == coverage && ex.getCovered().size()!=0) {
                    //if there are paths with same coverage, then choose the path that does that with the minimal number of responses
                    if (ex.getNumberOfResponses() < path.getNumberOfResponses()) {
                        path = ex;
                    }
                }else if(ex.getCovered().size()==0){
                    //special case..debug here in complex model irs2.prop to understand
                    path = ex;
                }
            }
            return path;
        } else {
            return nonDominatedSet.get(0);
        }
    }
}
