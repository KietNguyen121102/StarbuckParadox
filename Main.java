
public class Main {
    public static void main(String[] args) {

        int numPhysical = 1;
        int numOnline = 4;
        int cost = 10;
        int arrivalRate = 1;
        double alpha = 0.5;
        int numCustomer = 4;

       
        double noReGreedyCost = Greedy.noReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, 4);
        double reGreedyCost = Greedy.ReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, 4);
        double optimalNoRe = Optimal.optimalNoReallocation(numOnline, numPhysical, cost, arrivalRate, alpha, 4);

        //Semi-strategic

        double physicalPopulation = 0;
        double onlinePopulation = 0;
        double reSemiStrategic = semiStrategic.reallocSemiStrategic(numOnline, numPhysical, cost, arrivalRate, alpha, physicalPopulation, onlinePopulation, numCustomer);

        System.out.println(reGreedyCost + " vs " + noReGreedyCost + " vs " + optimalNoRe);

 
       
        
    }
}
