import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        int numPhysical = 2;
        int numOnline = 3;
        ConcurrentHashMap<ArrayList<Integer>,ArrayList<Double>> branches = NewGreedy.noReallocationGreedy(10, numOnline, numPhysical, 0.7, 1, 10);
        System.out.println(branches);
        // for(ArrayList<Double> result: branches.values()){
        //     List<Double> subresult = result.subList(numPhysical+numOnline, result.size());
        //     System.out.println(subresult);
        // }
       
       
        
    }
}
