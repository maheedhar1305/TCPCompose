package reasoner;

import model.Outcome;
import model.PreferenceQuery;
import model.Query;
import model.QueryResult;
import scheduling.Alternatives;
import scheduling.Job;
import scheduling.Response;
import test.CPTheoryDominanceExperimentDriver;
import tree.structure.Path;
import util.Constants;

import java.util.*;

/**
 * Created by maheedhar on 7/11/16.
 */
public class CrisnerReasoner<T> {

    private String nuSMVLocation;
    private String psFileName ;

    public CrisnerReasoner(String nuSMVLoc,String fileName){
        nuSMVLocation = nuSMVLoc;
        psFileName = fileName;
    }

    public <T> ArrayList<T> returnNonDominatedSet(HashSet<T> items){
        HashSet<T> weakOrder = __returnNonDominatedSet(items);
        return new ArrayList(weakOrder);
    }

    public <T> ArrayList<T> returnWeakOrder(HashSet<T> items){
        ArrayList weakOrder = new ArrayList<T>();
        for(T item : items){
            weakOrder.add(item);
        }
        Collections.sort(weakOrder,new CrisnerPathComparator());
        return weakOrder;
    }

    public <T> ArrayList<T> returnNonDominatedSet(T[] items){
        ArrayList weakOrder = new ArrayList<T>();
        for(T item : items){
            weakOrder.add(item);
        }
        HashSet<T> result = __returnNonDominatedSet(new HashSet<T>(weakOrder));
        return new ArrayList(result);
    }

    public boolean isBetterThanOrIncomparable(T element, T consideredElement) {
        HashSet<T> temp = new HashSet<>();
        temp.add(element);
        temp.add(consideredElement);
        ArrayList<T> order= returnNonDominatedSet(temp);
        if(order.get(0).equals(element))
            return true;
        else
            return false;
    }

    public <T> ArrayList<T> returnNonDominatedSet(ArrayList<T> items) {
        HashSet<T> weakOrder = __returnNonDominatedSet(new HashSet<T>(items));
        return new ArrayList(weakOrder);
    }

    public <T> HashSet<T> __returnNonDominatedSet(HashSet<T> items) {
        HashSet<T> result = new HashSet<T>();
        CrisnerPathComparator checker = new CrisnerPathComparator();
        for (T candidate : items){
            boolean nonDominated = true;
            for (T check : items){
                if(!check.equals(candidate)){
                    if(checker.compare(candidate,check)==1){
                        nonDominated = false;
                        break;
                    }
                }
            }
            if(nonDominated){
                result.add(candidate);
            }
        }
        return result;
    }

    public boolean isStrictlyBetterThan(T element, T consideredElement) {
        CrisnerPathComparator checker = new CrisnerPathComparator();
        if(checker.compareFinally(element,consideredElement)==-1){
            return true;
        }else {
            return false;
        }
    }

    public class CrisnerPathComparator implements Comparator{

        @Override
        public int compare(Object o1, Object o2) {
            try {
                HashMap<String, String> map1 = null;
                HashMap<String, String> map2 = null;
                if(o1 instanceof Path && o2 instanceof Path){
                    map1 = ((Path)o1).getBetaMostPreferedCompletion();
                    map2 = ((Path)o2).getBetaMostPreferedCompletion();
                }else if(o1 instanceof Alternatives && o2 instanceof Alternatives){
                    map1 = ((Alternatives)o1).getAttributes();
                    map2 = ((Alternatives)o2).getAttributes();
                }
                else if(o1 instanceof Job && o2 instanceof Job){
                    map1 = ((Job)o1).getAttributes();
                    map2 = ((Job)o2).getAttributes();
                }else if(o1 instanceof Response && o2 instanceof Response){
                    map1 = ((Response)o1).getAttributes();
                    map2 = ((Response)o2).getAttributes();
                }
                Constants.CURRENT_MODEL_CHECKER = Constants.MODEL_CHECKER.NuSMV;
                Constants.SMV_EXEC_COMMAND = nuSMVLocation;
                Set<Outcome> outcomes = new HashSet<Outcome>();
                Outcome out1 = new Outcome(map1);
                out1.setLabel("BETTER");
                outcomes.add(out1);
                Outcome out2 = new Outcome(map2);
                out2.setLabel("WORSE");
                outcomes.add(out2);
                Query q = new Query(PreferenceQuery.QueryType.DOMINANCE, psFileName, outcomes);
                QueryResult result = new CPTheoryDominanceExperimentDriver().executeQuery(q);
                //Using negative numbers to say "this is less than that", positive numbers to say "this is more than that" and 0 to say "these 2 things are equal" has been in many computer languages for 30+ years.
                //since we are ordering in descending order with most impactful first. Thus we are reversing -1 and +1 roles
                if(result.getResult()){
                    return -1; //todo verify
                }else{
                    Set<Outcome> outcomes1 = new HashSet<Outcome>();
                    Outcome out3 = new Outcome(map1);
                    out3.setLabel("WORSE");
                    outcomes1.add(out3);
                    Outcome out4 = new Outcome(map2);
                    out4.setLabel("BETTER");
                    outcomes1.add(out4);
                    Query q1 = new Query(PreferenceQuery.QueryType.DOMINANCE, psFileName, outcomes1);
                    QueryResult result1 = new CPTheoryDominanceExperimentDriver().executeQuery(q1);
                    if(result1.getResult()) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            }catch (Exception e){
                //this is supposed to happen during BFS because no values for beta and val in BFS
                System.out.println("Error in comparator : " + e);
                return 0;
            }
        }

        public int compareFinally(Object o1, Object o2) {
            try {
                HashMap<String, String> map1 = null;
                HashMap<String, String> map2 = null;
                if(o1 instanceof Path && o2 instanceof Path){
                    map1 = ((Path)o1).getPreferenceValuation();
                    map2 = ((Path)o2).getPreferenceValuation();
                }
                Constants.CURRENT_MODEL_CHECKER = Constants.MODEL_CHECKER.NuSMV;
                Constants.SMV_EXEC_COMMAND = nuSMVLocation;
                Set<Outcome> outcomes = new HashSet<Outcome>();
                Outcome out1 = new Outcome(map1);
                out1.setLabel("BETTER");
                outcomes.add(out1);
                Outcome out2 = new Outcome(map2);
                out2.setLabel("WORSE");
                outcomes.add(out2);
                Query q = new Query(PreferenceQuery.QueryType.DOMINANCE, psFileName, outcomes);
                QueryResult result = new CPTheoryDominanceExperimentDriver().executeQuery(q);
                //Using negative numbers to say "this is less than that", positive numbers to say "this is more than that" and 0 to say "these 2 things are equal" has been in many computer languages for 30+ years.
                //since we are ordering in descending order with most impactful first. Thus we are reversing -1 and +1 roles
                if(result.getResult()){
                    return -1; //todo verify
                }else{
                    Set<Outcome> outcomes1 = new HashSet<Outcome>();
                    Outcome out3 = new Outcome(map1);
                    out3.setLabel("WORSE");
                    outcomes1.add(out3);
                    Outcome out4 = new Outcome(map2);
                    out4.setLabel("BETTER");
                    outcomes1.add(out4);
                    Query q1 = new Query(PreferenceQuery.QueryType.DOMINANCE, psFileName, outcomes1);
                    QueryResult result1 = new CPTheoryDominanceExperimentDriver().executeQuery(q1);
                    if(result1.getResult()) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            }catch (Exception e){
                //this is supposed to happen during BFS because no values for beta and val in BFS
                System.out.println("Error in comparator : " + e);
                return 0;
            }
        }
    }

}
