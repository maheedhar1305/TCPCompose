package reasoner;

import model.Outcome;
import model.PreferenceQuery;
import model.Query;
import model.QueryResult;
import test.CPTheoryDominanceExperimentDriver;
import tree.structure.Path;
import util.Constants;

import java.util.*;

/**
 * Created by maheedhar on 7/11/16.
 */
public class CrisnerPathReasoner {

    private String nuSMVLocation;
    private String psFileName ;

    public CrisnerPathReasoner(String nuSMVLoc,String fileName){
        nuSMVLocation = nuSMVLoc;
        psFileName = fileName;
    }

    public ArrayList<Path> returnPathOrder(HashSet<Path> items){
        ArrayList weakOrder = new ArrayList<Path>();
        for(Path item : items){
            weakOrder.add(item);
        }
        Collections.sort(weakOrder,new CrisnerPathComparator());
        return weakOrder;
    }

    public ArrayList<Path> returnPathOrder(Path[] items){
        ArrayList weakOrder = new ArrayList<Path>();
        for(Path item : items){
            weakOrder.add(item);
        }
        Collections.sort(weakOrder,new CrisnerPathComparator());
        return weakOrder;
    }

    public boolean isBetterThan(Path element,Path consideredElement) {
        HashSet<Path> temp = new HashSet<>();
        temp.add(element);
        temp.add(consideredElement);
        ArrayList<Path> order= returnPathOrder(temp);
        if(order.get(0).equals(element))
            return true;
        else
            return false;
    }

    public class CrisnerPathComparator implements Comparator<Path>{

        @Override
        public int compare(Path o1, Path o2) {
            try {
                Constants.CURRENT_MODEL_CHECKER = Constants.MODEL_CHECKER.NuSMV;
                Constants.SMV_EXEC_COMMAND = nuSMVLocation;
                Set<Outcome> outcomes = new HashSet<Outcome>();
                HashMap<String, String> map1 = o1.getBetaMostPreferedCompletion();
                Outcome out1 = new Outcome(map1);
                out1.setLabel("BETTER");
                outcomes.add(out1);
                HashMap<String, String> map2 = o2.getBetaMostPreferedCompletion();
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
