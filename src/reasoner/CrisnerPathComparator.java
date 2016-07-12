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
 * Created by maheedhar on 6/26/16.
 */
public class CrisnerPathComparator implements Comparator<Path>{

    @Override
    public int compare(Path o1, Path o2) {
        try {
            //todo need to have two specs with different value specifications
            //todo for first pref ordering we order based on which is most severe and we need to have complete as most values but for responses none needs to have most value since it is negative impact which we are trying to minimize
            Constants.CURRENT_MODEL_CHECKER = Constants.MODEL_CHECKER.NuSMV;
            Constants.SMV_EXEC_COMMAND = "/opt/NuSMV-2.0.6-Darwin/bin/NuSMV ";
            String psFileName = "/Users/maheedhar/Downloads/QueryTest/test-newNames.xml";
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
                return 1;
            }
        }catch (Exception e){
            //this is supposed to happen during BFS because no values for beta and val in BFS
            System.out.println("Error in comparator : " + e);
            return 0;
        }
    }
}
