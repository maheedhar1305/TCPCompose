package tree.structure;

import scheduling.Alternatives;
import scheduling.Job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by maheedhar on 6/14/16.
 */
public class Path {

    private ArrayList<Node> path;
    private int workingLevel;
    private HashSet<Job> covered;
    private HashMap<Job,Alternatives> coveredWorkList;

    public Path(ArrayList<Node> temp,int currentLevel) {
        path = temp;
        covered = new HashSet<>();
        coveredWorkList = new HashMap<>();
        workingLevel = currentLevel;
    }

    public HashSet<Job> getCovered() {
        return covered;
    }

    public ArrayList<Node> getPath(){
        return path;
    }

    public Node getCurrentNode(){
        int size = path.size();
        return path.get(size-1);
    }

    public void addToCoverage(Set<Job> coverage,Alternatives response) {
        if(coverage!=null){
            for(Job job : coverage){
                covered.add(job);
                addCoveredWorkList(job,response);
            }
        }else{
            System.out.println("Path/addToCoverage() : Unexpected null assignment");
        }
    }

    public void extendPath(Node newNode) {
        path.add(newNode);
    }

    public int getWorkingLevel() {
        return workingLevel;
    }

    public void setWorkingLevel(int workingLevel) {
        this.workingLevel = workingLevel;
    }

    public void addCoveredWorkList(Job job,Alternatives alternatives){
        coveredWorkList.put(job,alternatives);
    }

    public Alternatives getCoveredWorkList(Job job) {
        return coveredWorkList.get(job);
    }

    public void printPath(ArrayList<Job> orderedList) {
        int level = 1;
        for(Job job : orderedList){
            System.out.println(level +" : "+job.toString() + "-->");
            System.out.println(path.get(level).toString());
            level++;
        }
    }
}
