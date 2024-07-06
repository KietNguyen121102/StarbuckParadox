import java.util.*;

public class Main {
    public static void main(String[] args) {

        int numPhysical = 2;
        int numOnline = 3;
        int cost = 10;
        int arrivalRate = 1;
        double alpha = 0.7;
        int numCustomer = 11;

       
        double noReGreedyCost = Greedy.noReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        double reGreedyCost = Greedy.ReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        double optimalNoRe = Optimal.optimalNoReallocation(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);

        //Semi-strategic

        // double physicalPopulation = 0.5;
        // double onlinePopulation = 0.2;
        // double reSemiStrategic = semiStrategic.reallocSemiStrategic(numOnline, numPhysical, cost, arrivalRate, alpha, physicalPopulation, onlinePopulation, numCustomer);
        // double noReSemiStrategic = semiStrategic.noReallocSemiStrategic(numOnline, numPhysical, cost, arrivalRate, alpha, physicalPopulation, onlinePopulation, numCustomer);
 
        //SPNE

        double SPNECost = SPNEwithPrinciple.SPNEPrinciple(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        double allQueueCost = allQueue.allQueue(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);



        System.out.println("Greedy with reallocation: " + reGreedyCost);
        System.out.println("Greedy with no reallocation: " + noReGreedyCost);
        System.out.println("optimal in no reallocation: " + optimalNoRe);
        System.out.println("SPNE in reallocation: " + SPNECost);
        System.out.println("All queue in reallocation: " + allQueueCost);
        

        // int[] decision = {1,0,1,1,1,1};
        // costMatrixGenerator.costGenerator(numOnline, numPhysical, cost, arrivalRate, alpha, decision);
       
        
    }
}
