package reasoner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by maheedhar on 5/19/16.
 */
public class MockPreferenceReasoner<T> {
    public static <T> ArrayList<T> returnOrder(HashSet<T> items){
        ArrayList<T> weakOrder = new ArrayList<>();
        for(T item : items){
            weakOrder.add(item);
        }
        int size = items.size();
        Collections.shuffle(weakOrder);
        return weakOrder;
    }

    public static <T> ArrayList<T> returnOrder(T[] items){
        ArrayList<T> weakOrder = new ArrayList<>();
        for(T item : items){
            weakOrder.add(item);
        }
        int size = items.length;
        Collections.shuffle(weakOrder);
        return weakOrder;
    }

    public static <T> boolean isBetterThan(T element,T consideredElement) {
        HashSet<T> temp = new HashSet<>();
        temp.add(element);
        temp.add(consideredElement);
        ArrayList<T> order= MockPreferenceReasoner.returnOrder(temp);
        //todo !!!!must make sure this equals operation performs as expected!!!!
        if(order.get(0).equals(element))
            return true;
        else
            return false;
    }
}
