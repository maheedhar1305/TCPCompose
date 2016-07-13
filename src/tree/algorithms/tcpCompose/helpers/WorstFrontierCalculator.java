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
    private String sourceFile;

    public WorstFrontierCalculator(String name){
        valueOrder = new HashMap<>();
        sourceFile = name;
        valueOrder = getValueOrder();
    }

    //to read the value order from file
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

    public HashMap<String,String> computeValWorstFrontier(HashMap<String,String> val1 , HashMap<String,String> val2){
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

    public HashMap<String,String> computeBetaWorstFrontier(HashMap<String,String> betaval1 , HashMap<String,String> betaval2, HashMap<String,String> previousVal){
        HashMap<String,String> result = new HashMap<>();
        for(String key : valueOrder.keySet()){
            if(betaval1.get(key)!=null && betaval2.get(key)!=null ){
                if(previousVal.get(key).equals("NULL") && isLesserThan(key,betaval1.get(key),betaval2.get(key))){
                    //example case : when our expectation for an attribute is PARTIAL and we have the same as beta values, but the first ever value that gets assigned is COMPLETE, then for that path we need to update beta as COMPLETE for that variable
                    //for more understanding see the paper figure 3, the path from b2 to b2p3
                    //working verified
                    result.put(key,betaval2.get(key));
                }else{
                    if(isLesserThan(key,betaval1.get(key),betaval2.get(key))){
                        result.put(key,betaval1.get(key));
                    }else{
                        result.put(key,betaval2.get(key));
                    }
                }
            }else{
                System.out.println("Unsupported attribute types");
            }
        }
        return result;
    }

}
