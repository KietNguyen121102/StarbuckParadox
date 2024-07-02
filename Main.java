
public class Main {
    public static void main(String[] args) {

        int numPhysical = 2;
        int numOnline = 3;
        int cost = 10;
        int arrivalRate = 1;
        double alpha = 0.5;
        int numCustomer = 10;

       
        double noReGreedyCost = Greedy.noReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        double reGreedyCost = Greedy.ReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        double optimalNoRe = Optimal.optimalNoReallocation(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        System.out.println(reGreedyCost + " vs " + noReGreedyCost + " vs " + optimalNoRe);

        //Semi-strategic

        double physicalPopulation = 0.5;
        double onlinePopulation = 0.2;
        double reSemiStrategic = semiStrategic.reallocSemiStrategic(numOnline, numPhysical, cost, arrivalRate, alpha, physicalPopulation, onlinePopulation, numCustomer);
        double noReSemiStrategic = semiStrategic.noReallocSemiStrategic(numOnline, numPhysical, cost, arrivalRate, alpha, physicalPopulation, onlinePopulation, numCustomer);
 
       
        
    }
}
