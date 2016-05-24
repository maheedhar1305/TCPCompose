package tree.structure;

/**
 * Created by maheedhar on 5/22/16.
 */
public class Tree<T> {

    private int no_of_attributes;
    private NodeFactory<T> treeNodeFactory;

    public Tree(int no){
        no_of_attributes = no;
        treeNodeFactory = new NodeFactory<>(no);
    }

    public NodeFactory<T> getTreeNodeFactory(){
        return treeNodeFactory;
    }


}
