package scheduling;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maheedhar on 5/23/16.
 */
public class Alternatives<T> {

    private T node_representation;
    private int no_of_attributes;
    private HashMap<String,String> attributes;

    public Alternatives(T representation ,int no){
        node_representation = representation;
        no_of_attributes = no;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public HashMap<String,String> getAttributes() {
        return attributes;
    }

    public int getNo_of_attributes() {
        return no_of_attributes;
    }

    public T getNode_representation() {
        return node_representation;
    }

    @Override
    public int hashCode() {
        return node_representation.toString().hashCode();
    }

    public boolean equals(Object obj){
        return node_representation.toString().equals(((Alternatives)obj).getNode_representation().toString());
    }

    public String toString(){
        return node_representation.toString();
    }
}
