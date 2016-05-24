package tree.structure;

/**
 * Created by maheedhar on 5/22/16.
 */
public class NodeFactory<T> {

    private int no_Of_attributes;

    public NodeFactory(int no){
        no_Of_attributes = no;
    }

    public Node<T> createNewNode(T representation){
        return new Node<T>(representation,no_Of_attributes);
    }
}
