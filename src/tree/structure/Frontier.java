package tree.structure;

import tree.structure.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by maheedhar on 5/23/16.
 */
public class Frontier {

    private HashSet<Path> expansionFrontier;

    public Frontier(){
        expansionFrontier = new HashSet<>();
    }

    public void addElement(Path element){
        expansionFrontier.add(element);
    }

    public Path[] getCurrentFrontier(){
        Path[] a = new Path[expansionFrontier.size()];
        return expansionFrontier.toArray(a);
    }

    public void removeFromFrontier(Path element){
        expansionFrontier.remove(element);
    }

    public boolean isEmpty() {
        return expansionFrontier.isEmpty();
    }
}
