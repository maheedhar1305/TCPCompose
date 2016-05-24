package reasoner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by maheedhar on 5/19/16.
 */
public class MockPreferenceReasoner<T> {
    public ArrayList<T> returnOrder(HashSet<T> items){
        ArrayList<T> weakOrder = new ArrayList<>();
        for(T item : items){
            weakOrder.add(item);
        }
        int size = items.size();
        Collections.shuffle(weakOrder);
        return weakOrder;
    }
}
