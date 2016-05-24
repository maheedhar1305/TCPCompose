package tree.structure;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by maheedhar on 5/22/16.
 */
public class Node<T> {

    private T node_representation;
    private int heuristic_value;
    private int no_of_attributes;
    private ArrayList<String> attributes;
    private ArrayList<Node> links;
    private ArrayList<Node> chainSoFar;
    private boolean startNode = false;
    private boolean endNode = false;

    public Node(T representation ,int no){
        node_representation = representation;
        no_of_attributes = no;
        attributes = new ArrayList<>(no);
    }

    public void setStartNode(){
        startNode = true;
    }

    public boolean isStartNode(){
        return startNode;
    }

    public void setEndNode(){
        endNode = true;
    }

    public boolean isEndNode(){
        return endNode;
    }

    public void connectToALink(ArrayList<Node> chain){
        chainSoFar = chain;
    }

    public void establishLink(Node child,ArrayList<Node> chain){
        child.connectToALink(chain);
    }

}
