package tree.traversal;

import reasoner.MockPreferenceReasoner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by maheedhar on 5/23/16.
 */
public class ResultSet<T> {

    private HashSet<T> tcpComposeResultSet;

    public ResultSet(){
        tcpComposeResultSet = new HashSet<>();
    }

    public boolean addElementToResultSet(T element){
        if (isNonDominatingSet(element)){
            removeLessPreferredSolutions(element);
            tcpComposeResultSet.add(element);
            return true;
        }else
            return false;
    }

    public void removeLessPreferredSolutions(T element){
        for(Iterator<T> iterable = tcpComposeResultSet.iterator();iterable.hasNext();){
            T consideredElement = iterable.next();
            if(isBetterThan(element,consideredElement)){
                iterable.remove();
            }
        }
    }

    private boolean isBetterThan(T element,T consideredElement) {
        HashSet<T> temp = new HashSet<>();
        temp.add(element);
        temp.add(consideredElement);
        ArrayList<T> order=new MockPreferenceReasoner<T>().returnOrder(temp);
        //todo !!!!must make sure this equals operation performs as expected!!!!
        if(order.get(0).equals(element))
            return true;
        else
            return false;
    }

    private boolean isNonDominatingSet(T element) {
        //cannot merge this into the snippet in addElementToresultSet. Too many uncertainities where removing an element from
        //result set should not be done in each step
        for(Iterator<T> iter = tcpComposeResultSet.iterator();iter.hasNext();){
            T consideredElement = iter.next();
            if(isBetterThan(consideredElement,element)){
                return false;
            }
        }
        return true;
    }

}
