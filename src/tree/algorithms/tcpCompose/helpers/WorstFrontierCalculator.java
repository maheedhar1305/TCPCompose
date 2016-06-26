package tree.algorithms.tcpCompose.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * Created by maheedhar on 6/26/16.
 */
public class WorstFrontierCalculator {

    private HashMap<String,HashMap<String,Integer>> valueOrder;
    //todo need to have something not hard coded for path
    private String sourceFile;

    public WorstFrontierCalculator(String name){
        valueOrder = new HashMap<>();
        sourceFile = name;
        valueOrder = getValueOrder();
    }

    public HashMap<String,HashMap<String,Integer>> getValueOrder() {
        HashMap<String,HashMap<String,Integer>> cVals = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            String attr_name = null;
            for(String line; (line = br.readLine()) != null; ) {
                if(line.startsWith("#") || line.trim()==""){
                    continue;
                }
                if(line.startsWith("*")){
                    attr_name= line.substring(1);
                    continue;
                }
                if(line.contains("=")){
                    String[] splits = line.split("=");
                    HashMap<String,Integer> ref ;
                    if(cVals.get(attr_name)!=null){
                        ref = cVals.get(attr_name);
                    }else {
                        ref = new HashMap<String,Integer>();
                    }
                    ref.put(splits[0].trim(),Integer.parseInt(splits[1].trim()));
                    cVals.put(attr_name,ref);
                }
            }
        }catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
        return cVals;
    }


    public boolean isLesserThan(String key, String a,String b){
        if(valueOrder.get(key).get(a) < valueOrder.get(key).get(b)){
            return true;
        }
        return false;
    }

    public HashMap<String,String> computeWorstFrontier(HashMap<String,String> val1 , HashMap<String,String> val2){
        HashMap<String,String> result = new HashMap<>();
        for(String key : valueOrder.keySet()){
            if(val1.get(key)!=null && val2.get(key)!=null ){
                if(isLesserThan(key,val1.get(key),val2.get(key))){
                    result.put(key,val1.get(key));
                }else{
                    result.put(key,val2.get(key));
                }
            }else{
                System.out.println("Unsupported attribute types");
            }
        }
        return result;
    }

}
