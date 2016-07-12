package tree.structure;

import reasoner.CrisnerPathReasoner;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by maheedhar on 5/23/16.
 */
public class ResultSet {

    private HashSet<Path> tcpComposeResultSet;

    public ResultSet(){
        tcpComposeResultSet = new HashSet<>();
    }

    public boolean addElementToResultSet(Path element){
        if (isNonDominatingSet(element)){
            removeLessPreferredSolutions(element);
            tcpComposeResultSet.add(element);
            return true;
        }else
            return false;
    }

    public void removeLessPreferredSolutions(Path element){
        for(Iterator<Path> iterable = tcpComposeResultSet.iterator();iterable.hasNext();){
            Path consideredElement = iterable.next();
            if(CrisnerPathReasoner.isBetterThan(element,consideredElement)){
                iterable.remove();
            }
        }
    }

    private boolean isNonDominatingSet(Path element) {
        //cannot merge this into the snippet in addElementToresultSet. Too many uncertainities where removing an element from
        //result set should not be done in each step
        for(Iterator<Path> iter = tcpComposeResultSet.iterator();iter.hasNext();){
            Path consideredElement = iter.next();
            if(CrisnerPathReasoner.isBetterThan(consideredElement,element)){
                return false;
            }
        }
        return true;
    }

}
