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
    private HashMap<String,String> betaMostPreferedCompletion ;
    private HashMap<String,String> preferenceValuation;

    public Path(ArrayList<Node> temp,int currentLevel) {
        path = temp;
        covered = new HashSet<>();
        coveredWorkList = new HashMap<>();
        workingLevel = currentLevel;
    }

    public HashMap<String, String> getBetaMostPreferedCompletion() {
        return betaMostPreferedCompletion;
    }

    public HashMap<String, String> getPreferenceValuation() {
        return preferenceValuation;
    }

    public void setBetaMostPreferedCompletion(HashMap<String, String> betaMostPreferedCompletion) {
        this.betaMostPreferedCompletion = betaMostPreferedCompletion;
    }

    public void setPreferenceValuation(HashMap<String, String> preferenceValuation) {
        this.preferenceValuation = preferenceValuation;
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

    public HashMap<Job, Alternatives> getCoveredWorkList() {
        return coveredWorkList;
    }

    public void setCoveredWorkList(HashMap<Job, Alternatives> coveredWorkList) {
        this.coveredWorkList = new HashMap<>(coveredWorkList);
    }

    public void setCovered(HashSet<Job> covered) {
        this.covered = new HashSet<>(covered);
    }

    public int getNumberOfResponses(){
        HashSet<String> uniqueResponses = new HashSet<>();
        for(int i =1; i<path.size();i++){
            Node node = path.get(i);
            uniqueResponses.add(node.toString());
        }
        return uniqueResponses.size();
    }
}
