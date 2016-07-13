package reasoner;

import model.Outcome;
import model.PreferenceQuery;
import model.Query;
import model.QueryResult;
import scheduling.Job;
import test.CPTheoryDominanceExperimentDriver;
import tree.structure.Path;
import util.Constants;

import java.util.*;

/**
 * Created by maheedhar on 7/11/16.
 */
public class CrisnerJobReasoner {

    private String nuSMVLocation;
    private String psFileName ;

    public CrisnerJobReasoner(HashMap<String, String> configValues){
        nuSMVLocation = configValues.get("NuSMVLocation");
        psFileName = configValues.get("OrganizationalCIAPrefOrderlocation");
    }

    public ArrayList<Job> returnJobOrder(HashSet<Job> items){
        ArrayList weakOrder = new ArrayList<Job>();
        for(Job item : items){
            weakOrder.add(item);
        }
        Collections.sort(weakOrder,new CrisnerJobComparator());
        return weakOrder;
    }

    public class CrisnerJobComparator implements Comparator<Job>{

        @Override
        public int compare(Job o1, Job o2) {
            try {
                Constants.CURRENT_MODEL_CHECKER = Constants.MODEL_CHECKER.NuSMV;
                Constants.SMV_EXEC_COMMAND = nuSMVLocation;
                Set<Outcome> outcomes = new HashSet<Outcome>();
                HashMap<String, String> map1 = o1.getAttributes();
                Outcome out1 = new Outcome(map1);
                out1.setLabel("BETTER");
                outcomes.add(out1);
                HashMap<String, String> map2 = o2.getAttributes();
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
                    return 1;
                }
            }catch (Exception e){
                //this is supposed to happen during BFS because no values for beta and val in BFS
                System.out.println("Error in comparator : " + e);
                return 0;
            }
        }
    }

}
