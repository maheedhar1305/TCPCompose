package tree.structure;

import reasoner.CrisnerReasoner;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by maheedhar on 5/23/16.
 */
public class ResultSet {

    private HashSet<Path> tcpComposeResultSet;
    private CrisnerReasoner crisnerPathReasoner;

    public ResultSet(CrisnerReasoner crisnerPathReasoner){
        this.crisnerPathReasoner = crisnerPathReasoner;
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
            if(crisnerPathReasoner.isBetterThanOrIncomparable(element,consideredElement)){
                iterable.remove();
            }
        }
    }

    private boolean isNonDominatingSet(Path element) {
        //cannot merge this into the snippet in addElementToresultSet. Too many uncertainities where removing an element from
        //result set should not be done in each step
        for(Iterator<Path> iter = tcpComposeResultSet.iterator();iter.hasNext();){
            Path consideredElement = iter.next();
            if(crisnerPathReasoner.isBetterThanOrIncomparable(consideredElement,element)){
                return false;
            }
        }
        return true;
    }

    public HashSet<Path> getTcpComposeResultSet() {
        return tcpComposeResultSet;
    }
}
