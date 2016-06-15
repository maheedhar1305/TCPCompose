package tree.structure;

import scheduling.Alternatives;

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
    private int level;

    public Node(Alternatives option,int level){
        node_representation = (T)option ; //todo this type casting should not be a problem at run time..double check though
        no_of_attributes = option.getNo_of_attributes();
        this.level = level;
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

    public int getLevel() {
        return level;
    }

    public T getNode_representation() {
        return node_representation;
    }

    public String toString(){
        return node_representation.toString();
    }
}
