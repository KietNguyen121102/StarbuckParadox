
public class Main {
    public static void main(String[] args) {

        int numPhysical = 1;
        int numOnline = 4;

       
        double noReGreedyCost = Greedy.noReallocationGreedy(numOnline, numPhysical, 10, 1, 0.5, 2);
        double reGreedyCost = Greedy.ReallocationGreedy(numOnline, numPhysical, 10, 1, 0.5, 2);
        System.out.println(reGreedyCost + " vs " + noReGreedyCost);

 
       
        
    }
}
