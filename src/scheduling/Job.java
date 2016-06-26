package scheduling;


import java.util.HashMap;
import java.util.Objects;

/**
 * Created by maheedhar on 5/23/16.
 */
public class Job<T> {

    private T job;

    private HashMap<String,String> attributes;

    public Job(T name){
        job = name;
        attributes = new HashMap<>();
    }

    public T getJob(){
        return job;
    }

    public String toString(){
        return job.toString();
    }

    @Override
    public int hashCode() {
        return job.toString().hashCode();
    }

    public boolean equals(Object obj){
        return job.toString().equals(((Job)obj).getJob().toString());
    }

    public void setAttributes(HashMap<String,String> attributes) {
        this.attributes = attributes;
    }
}
