import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
class SPNEwithPrinciple{



    public static double SPNEPrinciple(int numOnlineBarista, int numPhysicalBarista, int processingTime, int arrivalRate, double alpha, int customers){
        ConcurrentHashMap<ArrayList<Integer>, ArrayList<Double>> costMatrix = new ConcurrentHashMap<ArrayList<Integer>, ArrayList<Double>>();
        HashMap<Integer, ArrayList<Double>> possibleCostOfEachCustomer = new HashMap<Integer, ArrayList<Double>>();
        int[] arr = new int[customers];
        
        BinaryString binaryString = new BinaryString();
        binaryString.generateAllBinaryStrings(customers, arr, 0);

        ArrayList<int[]> allPossibleDecisions = binaryString.result;
        ArrayList<int[]> combinations = new ArrayList<int[]>();

        for(int[] decision: allPossibleDecisions){
            if(decision.length == customers)
            {combinations.add(decision);}
        }

        //Construcinng all strategies profile
        for (int[] decisions : combinations) {
            ArrayList<Double> currentCostMatrix = costGen.costGenerator(numOnlineBarista, numPhysicalBarista, processingTime, arrivalRate, alpha, decisions);
            ArrayList<Integer> decision = new ArrayList<Integer>();
            for (int i = 0; i < decisions.length; i++) {
                decision.add(decisions[i]);
            }
            costMatrix.put(decision, currentCostMatrix);
        }






        // Finding the all-queue cost
        ArrayList<Integer> allQueueStrategy = new ArrayList<Integer>();
        for(int i = 0; i < combinations.get(1).length; i++){
            allQueueStrategy.add(1);
        }
        ArrayList<Double> alQueueCost = costMatrix.get(allQueueStrategy);
        double allQueueCost = 0;
        for(int i = 0; i < alQueueCost.size(); i ++){
            allQueueCost += alQueueCost.get(i);
        }

        

        for (int customer = customers; customer >= 0; customer--) {
            int tieCount = 0;
            // System.out.println("Size of cost matrix is: " + costMatrix.size());
            HashMap<ArrayList<Integer>, ArrayList<Double>> toRemove = new HashMap<ArrayList<Integer>, ArrayList<Double>>();

            for (Map.Entry<ArrayList<Integer>, ArrayList<Double>> waitingTime : costMatrix.entrySet()) {
                ArrayList<Integer> strategy = waitingTime.getKey();
                ArrayList<Double> cost = waitingTime.getValue();
                ArrayList<Integer> alternativeStrategy = new ArrayList<Integer>();

                //finding the alternative node

               for(Map.Entry<ArrayList<Integer>, ArrayList<Double>> waitingTime2 : costMatrix.entrySet()){
                    boolean potentialAlternative = true;
                    for(int i = 0; i < customer; i++){
                        if(waitingTime2.getKey().get(i) != strategy.get(i)){
                            potentialAlternative = false;
                        }
                    }
                    if(potentialAlternative == true && (!waitingTime2.getKey().equals(strategy))){
                        alternativeStrategy = waitingTime2.getKey();                  
                     }

               }

                //comparing two alternative nodes

                ArrayList<Double> alternativeCost = costMatrix.get(alternativeStrategy);
                

               

                if(alternativeCost != null){
              
                if(cost.get(customer) < alternativeCost.get(customer)){
                    costMatrix.remove(alternativeStrategy, alternativeCost);
                }
                else if(cost.get(customer) > alternativeCost.get(customer)){
                    costMatrix.remove(strategy, cost);
                }
                else{
                    double estimatedCost = 0;
                    double estimatedAlternativeCost = 0;
                    if(costMatrix.get(strategy) != null){
                    for(int i = 0; i < costMatrix.get(strategy).size(); i++){
                        estimatedCost += costMatrix.get(strategy).get(i);
                    }}

                    if(costMatrix.get(alternativeStrategy) != null){
                    for(int i = 0; i < costMatrix.get(alternativeStrategy).size(); i++){
                        estimatedAlternativeCost += costMatrix.get(alternativeStrategy).get(i);
                    }}
                    if(estimatedCost < estimatedAlternativeCost){
                        costMatrix.remove(alternativeStrategy, alternativeCost);
                    }
                    else if(estimatedCost > estimatedAlternativeCost){
                        costMatrix.remove(strategy, cost);
                    }
                    else{
                        int numQStrat = 0;
                        int numQAlter = 0;
                        for(int i = 0; i < strategy.size(); i++){
                            if(strategy.get(i) == 1){
                                numQStrat++;
                            }
                        }
                        for(int j = 0; j < strategy.size(); j++){
                            if(strategy.get(j) == 0){
                                numQAlter ++;
                            }
                        }
                        if(numQStrat < numQAlter){
                            costMatrix.remove(strategy, cost);
                        }
                        else if(numQStrat > numQAlter){
                            costMatrix.remove(alternativeStrategy, alternativeCost);
                        }
                        else{
                            Random rand = new Random();
                            double randomNum = rand.nextDouble();
                            if(randomNum >= 0.5){
                                costMatrix.remove(strategy, cost);
                            }else{
                                costMatrix.remove(alternativeStrategy, alternativeCost);
                            }
                        }
                    }
                }
            }
                
            
   
            }
            
        }

       

        // Finding the Subgame Perfect Equilibrium cost
        ArrayList<Integer> SPEStrategy = new ArrayList<Integer>();
        Map.Entry<ArrayList<Integer>, ArrayList<Double>> firstEntry = costMatrix.entrySet().iterator().next();
        SPEStrategy = firstEntry.getKey();
        double totalCost = 0; 
        for(int i = 0; i < firstEntry.getValue().size(); i++){
            totalCost += firstEntry.getValue().get(i);
        }
        System.out.println("SPNE strategy is : " + SPEStrategy + " with cost " + firstEntry.getValue());
        return totalCost;
    }
}