package scheduling;

import java.util.ArrayList;

/**
 * Created by maheedhar on 5/23/16.
 */
public class Alternatives<T> {

    private T node_representation;
    private int value;
    private int no_of_attributes;
    private ArrayList<String> attributes;

    public Alternatives(T representation ,int no){
        node_representation = representation;
        no_of_attributes = no;
        attributes = new ArrayList<>(no);
    }
}
