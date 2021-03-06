package tree.algorithms.tcpCompose;

import scheduling.Alternatives;
import scheduling.Job;
import thirdParty.TcpComposeCommunicator;
import tree.structure.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by maheedhar on 6/15/16.
 */
public class coverageFirstTCPCompose extends TCPCompose {

    public coverageFirstTCPCompose(TcpComposeCommunicator tcpComposeCommunicator,HashMap<String, String> configValues){
        super(tcpComposeCommunicator,configValues);
    }

    //The emphasis here is to expand the response which has the most coverage going forward
    public Path chooseNextToExpand(ArrayList<Job> jobList){
        HashSet<Path> result = new HashSet<>();
        int coverageSize = 0; //initialise
        for(Path path : frontier.getCurrentFrontier()){
            if(path.getCurrentNode().getLevel() != 0){
                //size of the coverage of the current node in the chosen path
                int size = tcpComposeCommunicator.getCoverage((Alternatives)path.getCurrentNode().getNode_representation()).size();
                if (size > coverageSize){
                    //erase the old set whenever a node with more coverage is found
                    result = new HashSet<>();
                    result.add(path);
                    coverageSize = size;
                }else if(size == coverageSize){
                    //if two responses have same coverage, we can then see the CIA attributes
                    // of them to determine which is better amongst them
                    result.add(path);
                }
            }else{
                result.add(path);
            }
        }
        return (Path)crisnerPathReasoner.returnNonDominatedSet(result).get(0);
    }

}
