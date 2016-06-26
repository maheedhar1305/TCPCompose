package tree.algorithms;

import scheduling.Alternatives;
import tree.algorithms.tcpCompose.helpers.WorstFrontierCalculator;
import tree.structure.Frontier;
import tree.structure.Node;
import tree.structure.Path;
import tree.structure.ResultSet;

import java.util.ArrayList;

/**
 * Created by maheedhar on 6/15/16.
 */
public abstract class AbstractAlgorithm implements Algorithm {

    protected Frontier frontier ;
    protected WorstFrontierCalculator worstFrontierCalculator;

    protected AbstractAlgorithm(WorstFrontierCalculator worstFrontierCalculator){
        frontier = new Frontier();
        this.worstFrontierCalculator = worstFrontierCalculator;
    }

    public void createNewPath(Path toBeExpandedPath, Alternatives alternative){
        Node newNode = new Node(alternative,toBeExpandedPath.getWorkingLevel()+1);
        ArrayList<Node> newPath = new ArrayList<>(toBeExpandedPath.getPath());
        newPath.add(newNode);
        Path pathObject = new Path(newPath,toBeExpandedPath.getWorkingLevel()+1);
        pathObject.setCoveredWorkList(toBeExpandedPath.getCoveredWorkList());
        pathObject.setCovered(toBeExpandedPath.getCovered());
        pathObject.setPreferenceValuation(worstFrontierCalculator.computeWorstFrontier(toBeExpandedPath.getPreferenceValuation(),alternative.getAttributes()));
        pathObject.setBetaMostPreferedCompletion(worstFrontierCalculator.computeWorstFrontier(toBeExpandedPath.getBetaMostPreferedCompletion(),alternative.getAttributes()));
        frontier.addElement(pathObject);
    }

}
