import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

public class Optimal {
    
    public static double optimalNoReallocation(int numOnline, int numPhysical, int cost, int arrivalRate, double alpha, int numCustomer){
        
        double min = 10000000;
        ArrayList<ArrayList<Integer>> optimalDecision = new ArrayList<ArrayList<Integer>>();
        ConcurrentHashMap<ArrayList<Integer>, ArrayList<Double>> costMatrix = new ConcurrentHashMap<ArrayList<Integer>, ArrayList<Double>>();
        HashMap<Integer, ArrayList<Double>> possibleCostOfEachCustomer = new HashMap<Integer, ArrayList<Double>>();
        int[] arr = new int[numCustomer];

        BinaryString binaryString = new BinaryString();
        binaryString.generateAllBinaryStrings(numCustomer, arr, 0);

        ArrayList<int[]> allPossibleDecisions = binaryString.result;
        ArrayList<int[]> combinations = new ArrayList<int[]>();

        for(int[] decision: allPossibleDecisions){
            if(decision.length == numCustomer){
                combinations.add(decision);
            }
        }

    //Construcinng all strategies profile
        for (int[] decisions : combinations) {
            ArrayList<Double> currentCostMatrix = noReSchedulerSimulation.noReSchedulerSimulation(numOnline, numPhysical, cost, arrivalRate, alpha, decisions);
            ArrayList<Integer> decision = new ArrayList<Integer>();
            for (int i = 0; i < decisions.length; i++) {
                decision.add(decisions[i]);
            }
            costMatrix.put(decision, currentCostMatrix);

        }

    

        for(Map.Entry<ArrayList<Integer>, ArrayList<Double>> waitingTime : costMatrix.entrySet()){
            double totalCost = 0;
            for(int i = 0; i < waitingTime.getValue().size(); i++){
                totalCost = totalCost + waitingTime.getValue().get(i);
            }
            if(totalCost < min){
            min = totalCost;
            }
        }

        for(Map.Entry<ArrayList<Integer>, ArrayList<Double>> waitingTime : costMatrix.entrySet()){
            double totalCost = 0;
            for(int i = 0; i < waitingTime.getValue().size(); i++){
                totalCost = totalCost + waitingTime.getValue().get(i);
            }
            if(totalCost == min){
            optimalDecision.add(waitingTime.getKey());
            }
        }

        ArrayList<Double> optimalCostOfEachCustomer = costMatrix.get(optimalDecision.get(0));



        // double greedyCost = NoReGreedySimulation.NoReGreedy(2, 1, 10, 1, 0.7 , customer);

        System.out.println("Optimal in noreallocation: " + optimalDecision.get(0) + " with cost " + optimalCostOfEachCustomer);

        return min;
    
        }
    }

