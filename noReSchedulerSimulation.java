import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

public class noReSchedulerSimulation {
    public static ArrayList<Double> noReSchedulerSimulation(int onlineBaristas, int physicalBaristas, double processingTime, int ArrivalRate, double alpha, int[] decisions) {
        // every minute in an 8-hour working day
        int numCustomer = 0;
        int numCustomerServed = 0;
        HashMap<Double, Double> costMap = new HashMap<Double,Double>();
        ArrayList<Double> costMatrix = new ArrayList<Double>();

        double[] onlineBarista = new double[onlineBaristas];
        for (int i = 0; i < onlineBaristas; i++) {
            onlineBarista[i] = 0;
        }

        double[] physicalBarista = new double[physicalBaristas];
        for (int i = 0; i < physicalBaristas; i++) {
            physicalBarista[i] = 0;
        }



        Queue<Customer> physicalLine = new LinkedList<Customer>();
        Queue<Customer> onlineQueue = new LinkedList<Customer>();
        ArrayList<Customer> servedCustomer = new ArrayList<Customer>();

        double customerArrivalRate = ArrivalRate;
        int timeToNextCust = 0;
        double totalTime = 0;
        double averageTime;

        for (int time = 0; time <= 4800; time = time + 1) {
        

            Customer customer = new Customer(0);
            timeToNextCust++;
        
            if (timeToNextCust == customerArrivalRate) {
                customer.setArrivalTime(time);
                timeToNextCust = 0;
                numCustomer++;
            }

            


            if (numCustomer <= decisions.length) {
        
                
                int decision = decisions[numCustomer-1];
                
                // System.out.println("Online queue expected waiting time is: " + queueWaitingTime + ", physical line expected waiting time is: " + lineWaitingTime);
                if (decision == 0) {
                    physicalLine.add(customer);
                    // System.out.println("Customer chooses physical line");
                } else if (decision == 1){
                    onlineQueue.add(customer);
                    customer.beOnline();
                    // System.out.println("Customer chooses online queue");
                }
            }

        

            for (int num = 0; num < onlineBaristas; num++) {

                Customer servingCustomer = new Customer(0);

                if ((time >= onlineBarista[num]) && (onlineQueue.size() != 0)) {
                    servingCustomer = onlineQueue.peek();
                    onlineQueue.poll();
                    numCustomerServed++;

                    if(onlineBarista[num] != 0)
                    {servingCustomer.setTotalTime((time+processingTime-servingCustomer.arrivalTime)*alpha);}
                    else{servingCustomer.setTotalTime((processingTime)*alpha);}
                    
            
                    onlineBarista[num] = (time + processingTime);
                   
                    servedCustomer.add(servingCustomer);
                }

            }

            

            for (int num = 0; num < physicalBaristas; num++) {

                Customer servingCustomer2 = new Customer(0);

                if ((time >= physicalBarista[num]) && ((physicalLine.size() != 0) )) {
                  
                    servingCustomer2 = physicalLine.peek();
                    physicalLine.poll();
                    numCustomerServed++;
                    
                    if(physicalBarista[num] != 0)
                    {servingCustomer2.setTotalTime(time+processingTime - servingCustomer2.arrivalTime);}
                    else{servingCustomer2.setTotalTime(processingTime);}
                    
                    physicalBarista[num] = (time + processingTime);
                    

                    servedCustomer.add(servingCustomer2);

                }
            }     
        }

        for(int i = 0; i < numCustomer;i++){
            for(Customer customer: servedCustomer){
                if(customer.getArrivalTime() == i){
                    costMatrix.add(customer.waitTime);
                }
            }
            // Customer currentCustomer = servedCustomer.get(i);
            // costMatrix.add(currentCustomer.waitTime);

        }

        averageTime = totalTime / (numCustomerServed);
        // System.out.println("Cost matrix: " + costMatrix);
    
        return costMatrix;


        
    }
}
