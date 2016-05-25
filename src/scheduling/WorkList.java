package scheduling;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maheedhar on 5/23/16.
 */
public class WorkList {

    private HashMap<Job,ArrayList<Alternatives>> tcpComposeWorkList;

    private ArrayList<Job> priorityOrder;

    public WorkList(){
        tcpComposeWorkList = new HashMap<>();
    }

    public void addAlternative(Job job,Alternatives alternative){
        ArrayList alternativesList = tcpComposeWorkList.get(job) ;
        if(alternativesList == null){
            alternativesList = new ArrayList();
        }
        alternativesList.add(alternative);
        tcpComposeWorkList.put(job,alternativesList);
    }

    public ArrayList<Alternatives> getAlternativesList(Job job){
        return tcpComposeWorkList.get(job);
    }

}
