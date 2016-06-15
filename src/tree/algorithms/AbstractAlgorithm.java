package tree.algorithms;

import scheduling.Alternatives;
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

    protected AbstractAlgorithm(){
        frontier = new Frontier();
    }

    public void createNewPath(Path toBeExpandedPath, Alternatives alternative){
        Node newNode = new Node(alternative,toBeExpandedPath.getWorkingLevel()+1);
        ArrayList<Node> newPath = new ArrayList<>(toBeExpandedPath.getPath());
        newPath.add(newNode);
        frontier.addElement(new Path(newPath,toBeExpandedPath.getWorkingLevel()+1));
    }

}
