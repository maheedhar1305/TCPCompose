package reasoner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by maheedhar on 7/11/16.
 */
public class CrisnerPathReasoner {

    public static <Path> ArrayList<Path> returnPathOrder(HashSet<Path> items){
        ArrayList weakOrder = new ArrayList<Path>();
        for(Path item : items){
            weakOrder.add(item);
        }
        Collections.sort(weakOrder,new CrisnerPathComparator());
        return weakOrder;
    }

    public static <Path> ArrayList<Path> returnPathOrder(Path[] items){
        ArrayList weakOrder = new ArrayList<Path>();
        for(Path item : items){
            weakOrder.add(item);
        }
        Collections.sort(weakOrder,new CrisnerPathComparator());
        return weakOrder;
    }

    public static <Path> boolean isBetterThan(Path element,Path consideredElement) {
        HashSet<Path> temp = new HashSet<>();
        temp.add(element);
        temp.add(consideredElement);
        ArrayList<Path> order= CrisnerPathReasoner.returnPathOrder(temp);
        if(order.get(0).equals(element))
            return true;
        else
            return false;
    }

}
