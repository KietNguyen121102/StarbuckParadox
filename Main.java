import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        
        int cost = 10;
        int arrivalRate = 1;
       
      

        for(int numOnline = 1; numOnline <= 4; numOnline++){
            // for(double alpha = 0.2; alpha <= 0.8; alpha = alpha + 0.2){
                // for(int numCustomer = 1; numCustomer <= 15; numCustomer = numCustomer + 1){
                for(double greedyPopulation = 0.0; greedyPopulation <= 1.0; greedyPopulation = greedyPopulation + 0.2){
                    for(double physicalOverOnline = 0.0; physicalOverOnline <= 1.0; physicalOverOnline = physicalOverOnline + 0.2){
                        int numPhysical = 5 - numOnline;
                        double physicalPopulation = (1-greedyPopulation)*physicalOverOnline;
                        double onlinePopulation = (1-greedyPopulation)*(1-physicalOverOnline);
                    // double noReGreedyCost = Greedy.noReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
                    // double reGreedyCost = Greedy.ReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
                    // double optimalNoRe = Optimal.optimalNoReallocation(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);

        //Semi-strategic
                        double reSemiStrategic = 0;
                        double noReSemiStrategic = 0;

                        for(int i = 0; i <= 100; i++)
                        {reSemiStrategic += semiStrategic.reallocSemiStrategic(numOnline, numPhysical, cost, arrivalRate, 0.7, physicalPopulation, onlinePopulation, 100);}
                        for(int j = 0; j <= 100; j++)
                        {noReSemiStrategic += semiStrategic.noReallocSemiStrategic(numOnline, numPhysical, cost, arrivalRate, 0.7, physicalPopulation, onlinePopulation, 100);}
 
        //SPNE
                  
                    // double SPNECost = SPNEwithPrinciple.SPNEPrinciple(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
                    // double allQueueCost = allQueue.allQueue(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
                    // double allLineCost = allQueueCost/alpha;


                                  
                    // ArrayList<Double> result = new ArrayList<Double>();
                    // result.add((double) numOnline); 
                    // result.add((double) numPhysical);
                    // result.add(alpha);
                    // result.add((double) numCustomer);
                    // result.add(noReGreedyCost);
                    // result.add(reGreedyCost);
                    // result.add(optimalNoRe);
                    // result.add(SPNECost);
                    // result.add(allQueueCost);
                    // result.add(allLineCost);

                        ArrayList<Double> semiStrategicResult = new ArrayList<Double>();
                        semiStrategicResult.add((double) numOnline);
                        semiStrategicResult.add((double) numPhysical);
                        semiStrategicResult.add((double) physicalOverOnline); 
                        semiStrategicResult.add((double) greedyPopulation);
                        semiStrategicResult.add(reSemiStrategic/100);
                        semiStrategicResult.add(noReSemiStrategic/100);
                  

                    try(FileWriter f = new FileWriter("semiStrategicComparison.txt", true); 
                        BufferedWriter b = new BufferedWriter(f); 
                        PrintWriter p = new PrintWriter(b);) {
                            p.println(semiStrategicResult);
                            System.out.println("Add sucessfully");
                        }
                        catch (IOException i){
                            i.printStackTrace();
                    }


                }
            }
        }


        // System.out.println("Greedy with reallocation: " + reGreedyCost);
        // System.out.println("Greedy with no reallocation: " + noReGreedyCost);
        // System.out.println("optimal in no reallocation: " + optimalNoRe);
        // System.out.println("SPNE in reallocation: " + SPNECost);
        // System.out.println("All queue in reallocation: " + allQueueCost);
        

        // int[] decision = {1,0,1,1,1,1};
        // costMatrixGenerator.costGenerator(numOnline, numPhysical, cost, arrivalRate, alpha, decision);
       
        
    }
}
