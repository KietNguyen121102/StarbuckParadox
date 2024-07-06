import java.util.ArrayList;

public class allQueue {
    

    public static double allQueue(int numOnline, int numPhysical, int cost, int arrivalRate, double alpha, int numCustomer){
        double totalCost = 0;
        int[] decision = new int[numCustomer];
        for(int i = 0; i < decision.length; i++){
            decision[i] =1;
        }

        ArrayList<Double> costMatrix = costMatrixGenerator.costGenerator(numOnline, numPhysical, cost, arrivalRate, alpha, decision);
        for(int i = 0; i < costMatrix.size(); i++){
            totalCost += costMatrix.get(i);
        }

        return totalCost;
    }

}
