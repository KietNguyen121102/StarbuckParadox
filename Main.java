import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        
        for(int j = 0; j <= 100; j++)
        {
            ArrayList<Double> costAnalysis = nonStrategic.noReallocNonStrategic(1, 4, 10, 1, 0.7, 0.6, 0.4, 50);
                    
            ArrayList<Double> result = new ArrayList<Double>();
            double cost = 0;
            for(int k = 1; k < costAnalysis.size(); k++){
                cost += costAnalysis.get(k);
                if(k % 5 == 0){
                    result.add(cost);
                    cost = 0;
                }
            }

            try(FileWriter f = new FileWriter("nonStrategicAnalysis.txt", true); 
            BufferedWriter b = new BufferedWriter(f); 
            PrintWriter p = new PrintWriter(b);) {
                p.println(result);
                            // System.out.println("Add sucessfully");
            }
                catch (IOException i){
                i.printStackTrace();
            }
        }

        
        // int cost = 10;
        // int arrivalRate = 1;
       
      

        // for(int numOnline = 1; numOnline <= 4; numOnline++){
        //     // for(double alpha = 0.2; alpha <= 0.8; alpha = alpha + 0.2){
        //         for(double physicalPopulation = 0; physicalPopulation <= 1; physicalPopulation = physicalPopulation + 0.2){
        //             for(int numCustomer = 1; numCustomer <= 100; numCustomer = numCustomer + 1){
        //         // for(double greedyPopulation = 0.0; greedyPopulation <= 1.0; greedyPopulation = greedyPopulation + 0.2){
        //             // for(double physicalOverOnline = 0.0; physicalOverOnline <= 1.0; physicalOverOnline = physicalOverOnline + 0.2){
        //                 int numPhysical = 5 - numOnline;
        //                 double onlinePopulation = 1 - physicalPopulation;
        //                 // double physicalPopulation = (1-greedyPopulation)*physicalOverOnline;
        //                 // double onlinePopulation = (1-greedyPopulation)*(1-physicalOverOnline);
        //             // double noReGreedyCost = Greedy.noReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        //             // double reGreedyCost = Greedy.ReallocationGreedy(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        //             // double optimalNoRe = Optimal.optimalNoReallocation(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);

        // //Semi-strategic
        //                 double reSemiStrategic = 0;
        //                 double noReSemiStrategic = 0;

        //                 // for(int j = 0; j <= 100; j++)
        //                 // {reSemiStrategic += semiStrategic.reallocSemiStrategic(numOnline, numPhysical, cost, arrivalRate, 0.7, physicalPopulation, onlinePopulation, 100);}
        //                 // for(int j = 0; j <= 100; j++)
        //                 // {noReSemiStrategic += semiStrategic.noReallocSemiStrategic(numOnline, numPhysical, cost, arrivalRate, 0.7, physicalPopulation, onlinePopulation, 100);}
 
        //     //SPNE
                  
        //                 // double SPNECost = SPNEwithPrinciple.SPNEPrinciple(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        //                 // double allQueueCost = allQueue.allQueue(numOnline, numPhysical, cost, arrivalRate, alpha, numCustomer);
        //                 // double allLineCost = allQueueCost/alpha;

        //     //Non-strategic
        //                 double nonStrategicCost = 0;
        //                 for(int i = 0; i <= 100; i++)
        //                 {nonStrategicCost += nonStrategic.noReallocNonStrategic(numOnline, numPhysical, cost, arrivalRate, 0.7, physicalPopulation, onlinePopulation, numCustomer);}

                                  
        //                 ArrayList<Double> result = new ArrayList<Double>();
        //                 result.add((double) numOnline); 
        //                 result.add((double) numPhysical);
        //                 result.add((double) numCustomer);
        //                 result.add(physicalPopulation);
        //                 result.add(onlinePopulation);
        //                 // result.add(alpha);
        //                 result.add(nonStrategicCost/100);
        //                 // result.add(physicalOverOnline);
        //                 // result.add(greedyPopulation);
        //                 // result.add(reSemiStrategic/100);
        //                 // result.add((double) numCustomer);
        //                 // result.add(noReGreedyCost);
        //                 // result.add(reGreedyCost);
        //                 // result.add(optimalNoRe);
        //                 // result.add(SPNECost);
        //                 // result.add(allQueueCost);
        //                 // result.add(allLineCost);

        //     //Not constant cost and arrival time

        //             // double[] costEachRunWithRe = new double[100];
        //             // double[] costEachRunNoRe = new double[100];
        //             // double[] allQueueCost = new double[100];
        //             // double[] allLineCost = new double[100];

        //             // for(int i = 0; i < 100; i++){
        //             //     costEachRunWithRe[i] = notConstantVar.ReallocationGreedy(numOnline, numPhysical, alpha, numCustomer);
        //             //     costEachRunNoRe[i] = notConstantVar.noReallocationGreedy(numOnline, numPhysical, alpha, numCustomer);
        //             //     allQueueCost[i] = notConstantVar.allQueue(numOnline, numPhysical, alpha, numCustomer);
        //             //     allLineCost[i] = notConstantVar.allLine(numOnline, numPhysical, alpha, numCustomer);

        //             // }

                    

        //             // double meanNoRe = helperDistribution.calculateMean(costEachRunNoRe);
        //             // double meanWithRe = helperDistribution.calculateMean(costEachRunWithRe);
        //             // double SDNoRe = helperDistribution.calculateStandardDeviation(costEachRunNoRe, meanNoRe);
        //             // double SDRe = helperDistribution.calculateStandardDeviation(costEachRunWithRe, meanWithRe);
        //             // double meanAllQueue = helperDistribution.calculateMean(allQueueCost);
        //             // double SDAllQueue = helperDistribution.calculateStandardDeviation(allQueueCost, meanAllQueue);
        //             // double meanAllLine = helperDistribution.calculateMean(allLineCost);
        //             // double SDAllLine = helperDistribution.calculateStandardDeviation(allLineCost, meanAllLine);

        //             //     ArrayList<Double> notConstantResult = new ArrayList<Double>();
        //             //     notConstantResult.add((double) numOnline);
        //             //     notConstantResult.add((double) numPhysical);
        //             //     notConstantResult.add(alpha); 
        //             //     notConstantResult.add((double) numCustomer);
        //             //     notConstantResult.add(meanNoRe);
        //             //     notConstantResult.add(SDNoRe);
        //             //     notConstantResult.add(meanWithRe);
        //             //     notConstantResult.add(SDRe);
        //             //     notConstantResult.add(meanAllQueue);
        //             //     notConstantResult.add(SDAllQueue);
        //             //     notConstantResult.add(meanAllLine);
        //             //     notConstantResult.add(SDAllLine);
                        
                        
                  

        //             try(FileWriter f = new FileWriter("nonStrategic.txt", true); 
        //                 BufferedWriter b = new BufferedWriter(f); 
        //                 PrintWriter p = new PrintWriter(b);) {
        //                     p.println(result);
        //                     // System.out.println("Add sucessfully");
        //                 }
        //                 catch (IOException i){
        //                     i.printStackTrace();
        //             }


        //         }
        //     }
        // // }


        // // System.out.println("Greedy with reallocation: " + reGreedyCost);
        // // System.out.println("Greedy with no reallocation: " + noReGreedyCost);
        // // System.out.println("optimal in no reallocation: " + optimalNoRe);
        // // System.out.println("SPNE in reallocation: " + SPNECost);
        // // System.out.println("All queue in reallocation: " + allQueueCost);
        

        // // int[] decision = {1,0,1,1,1,1};
        // // costMatrixGenerator.costGenerator(numOnline, numPhysical, cost, arrivalRate, alpha, decision);
        //     }
       
        
    }
}
// }
