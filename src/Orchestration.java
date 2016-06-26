import reasoner.MockPreferenceReasoner;
import scheduling.Alternatives;
import scheduling.Job;
import scheduling.WorkList;
import thirdParty.TcpComposeCommunicator;
import tree.algorithms.bdfs.BreadthFirstSearch;
import tree.algorithms.tcpCompose.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by maheedhar on 5/23/16.
 */
public class Orchestration {

    public static void main(String args[]){
        HashSet<Job> workset = new HashSet<>();
        Job job1 = new Job("job1");
        Job job2 = new Job("job1");
        Job job3 = new Job("job1");
        Job job4 = new Job("job4");
        workset.add(job1);
        workset.add(job2);
        workset.add(job3);
        workset.add(job4);
        ArrayList<Job> orderedWorkList = new MockPreferenceReasoner<Job>().returnOrder(workset);
        Alternatives<String> job1alt1 = new Alternatives<>("job1alt1",6);
        Alternatives<String> job1alt2 = new Alternatives<>("job1alt2",6);
        Alternatives<String> job1alt3 = new Alternatives<>("job1alt3",6);
        Alternatives<String> job2alt1 = new Alternatives<>("job2alt1",6);
        Alternatives<String> job2alt2 = new Alternatives<>("job2alt2",6);
        Alternatives<String> job2alt3 = new Alternatives<>("job2alt3",6);
        Alternatives<String> job2alt4 = new Alternatives<>("job2alt4",6);
        Alternatives<String> job3alt1 = new Alternatives<>("job3alt1",6);
        Alternatives<String> job3alt2 = new Alternatives<>("job3alt2",6);
        Alternatives<String> job3alt3 = new Alternatives<>("job3alt3",6);
        Alternatives<String> job4alt1 = new Alternatives<>("job4alt1",6);
        Alternatives<String> job4alt2 = new Alternatives<>("job4alt2",6);
        WorkList workList = new WorkList();
        workList.addAlternative(job1,job1alt1);
        workList.addAlternative(job1,job1alt2);
        workList.addAlternative(job1,job1alt3);
        workList.addAlternative(job2,job2alt1);
        workList.addAlternative(job2,job2alt2);
        workList.addAlternative(job2,job2alt3);
        workList.addAlternative(job2,job2alt4);
        workList.addAlternative(job3,job3alt1);
        workList.addAlternative(job3,job3alt2);
        workList.addAlternative(job3,job3alt3);
        workList.addAlternative(job4,job4alt1);
        workList.addAlternative(job4,job4alt2);
//        new TCPCompose(new TcpComposeCommunicator() {
//            @Override
//            public Set<Job> getCoverage(Alternatives alternatives) {
//                return null;
//            }
//        }).findOptimalCompositions(workList,orderedWorkList);
//        new BreadthFirstSearch().findOptimalCompositions(workList,orderedWorkList);
    }
}
