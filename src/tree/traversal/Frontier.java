package tree.traversal;

import tree.structure.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by maheedhar on 5/23/16.
 */
public class Frontier {

    private HashSet<ArrayList<Node>> expansionFrontier;

    public Frontier(){
        expansionFrontier = new HashSet<>();
    }

    public void addElement(ArrayList<Node> element){
        expansionFrontier.add(element);
    }

    public ArrayList<Node>[] getCurrentFrontier(){
        ArrayList<Node>[] a = new ArrayList[expansionFrontier.size()];
        return expansionFrontier.toArray(a);
    }

    public void removeFromFrontier(ArrayList<Node> element){
        //todo dont know if this will work properly since the element comes after a lot of processing..
        // not sure if it will be in the same memory block
        expansionFrontier.remove(element);
    }

    public boolean isEmpty() {
        return expansionFrontier.isEmpty();
    }
}
