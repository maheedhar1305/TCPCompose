package tree.algorithms;

import scheduling.Alternatives;
import scheduling.Job;
import scheduling.Response;
import thirdParty.TcpComposeCommunicator;
import tree.algorithms.tcpCompose.helpers.WorstFrontierCalculator;
import tree.structure.Frontier;
import tree.structure.Node;
import tree.structure.Path;

import java.util.ArrayList;

/**
 * Created by maheedhar on 6/15/16.
 */
public abstract class AbstractAlgorithm implements Algorithm {

    protected Frontier frontier ;
    protected WorstFrontierCalculator worstFrontierCalculator;
    protected TcpComposeCommunicator tcpComposeCommunicator;

    protected AbstractAlgorithm(TcpComposeCommunicator prefEvaluatorInterface, WorstFrontierCalculator worstFrontierCalculator){
        frontier = new Frontier();
        this.worstFrontierCalculator = worstFrontierCalculator;
        tcpComposeCommunicator = prefEvaluatorInterface;
    }

    public void createNewPath(Job newJob, Path toBeExpandedPath, Alternatives alternative){
        Node newNode = new Node(alternative,toBeExpandedPath.getWorkingLevel()+1);
        ArrayList<Node> newPath = new ArrayList<>(toBeExpandedPath.getPath());
        newPath.add(newNode);
        Path pathObject = new Path(newPath,toBeExpandedPath.getWorkingLevel()+1);
        pathObject.setCoveredWorkList(toBeExpandedPath.getCoveredWorkList());
        pathObject.setCovered(toBeExpandedPath.getCovered());
        if(worstFrontierCalculator!=null){
            //todo add support for BFS too for this update
            //todo instead of alternative.getAttributes. we shuold give the attributes of the exact response of that weakness's reponse in that level
            Response chosenOne = null;
            for(Response response : tcpComposeCommunicator.getOriginalResponses().get(newJob)){
                if(response.equals((Response)alternative.getNode_representation())){
                    chosenOne = response;
                    break;
                }
            }
            pathObject.setPreferenceValuation(worstFrontierCalculator.computeValWorstFrontier(toBeExpandedPath.getPreferenceValuation(),chosenOne.getAttributes()));
            pathObject.setBetaMostPreferedCompletion(worstFrontierCalculator.computeBetaWorstFrontier(toBeExpandedPath.getBetaMostPreferedCompletion(),chosenOne.getAttributes(),toBeExpandedPath.getPreferenceValuation()));
        }
        frontier.addElement(pathObject);
    }

}
