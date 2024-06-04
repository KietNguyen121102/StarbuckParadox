import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

public class SchedulerSimulation {
    public static double schedulerSimulation(int onlineBaristas, int physicalBaristas, double processingTime, int ArrivalRate, double alpha, int[] decisions) {
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

        double customerArrivalRate = ArrivalRate;
        int timeToNextCust = 0;
        double totalTime = 0;
        double averageTime;

        for (int time = 1; time <= 4800; time = time + 1) {
        

            Customer customer = new Customer(0);

            
            timeToNextCust++;
        
            if (timeToNextCust == customerArrivalRate) {
                customer.setArrivalTime(time);
                timeToNextCust = 0;
                numCustomer++;
            }

            


            if (customer.getArrivalTime() != 0 && numCustomer <= decisions.length) {
        
                
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

            Customer servingCustomer = new Customer(0);
        

            for (int num = 0; num < onlineBaristas; num++) {
                if ((time >= onlineBarista[num]) && ((physicalLine.size() != 0) || (onlineQueue.size() != 0))) {
                    if ((onlineQueue.size() != 0)) {
                        servingCustomer = onlineQueue.peek();
                        onlineQueue.poll();
                        numCustomerServed++;
                    } else if (physicalLine.size() != 0) {
                        servingCustomer = physicalLine.peek();
                        physicalLine.poll();
                        numCustomerServed++;
                    }

                    // System.out.println("Breakpoint 2");

                    // System.out.println("Customer serving by online barista number " + num + " is customer:" + servingCustomer.getArrivalTime());
                    // if(servingCustomer.online == true){System.out.println("This customer is from the online queue");}
                    // else{System.out.println("This customer is from the in person line");}

                    onlineBarista[num] = (time + processingTime);
                    servingCustomer.setDepartureTime(onlineBarista[num]);
                    if (servingCustomer.online == true) {
                        totalTime += (servingCustomer.totalTime() * alpha);
                        costMap.put(servingCustomer.getArrivalTime(), servingCustomer.totalTime() * alpha);
                    } else {
                        totalTime += servingCustomer.totalTime();
                        costMap.put(servingCustomer.getArrivalTime(), servingCustomer.totalTime());
                    }

                }

            }

            Customer servingCustomer2 = new Customer(0);

            for (int num = 0; num < physicalBaristas; num++) {
                if ((time >= physicalBarista[num]) && ((physicalLine.size() != 0) || (onlineQueue.size() != 0))) {
                    if ((physicalLine.size() != 0)) {
                        servingCustomer2 = physicalLine.peek();
                        physicalLine.poll();
                        numCustomerServed++;
                    } else if (onlineQueue.size() != 0) {
                        servingCustomer2 = onlineQueue.peek();
                        onlineQueue.poll();
                        numCustomerServed++;
                    }

                   
                    physicalBarista[num] = (time + processingTime);
                    servingCustomer2.setDepartureTime(physicalBarista[num]);
                    if (servingCustomer2.online == true) {
                        totalTime += (servingCustomer2.totalTime() * alpha);
                        costMap.put(servingCustomer2.getArrivalTime(), servingCustomer2.totalTime() * alpha);
                    } else {
                        totalTime += servingCustomer2.totalTime();
                        costMap.put(servingCustomer2.getArrivalTime(), servingCustomer2.totalTime());
                    }

                }

            }


          
          
           

            
        }

        // System.out.println("current length of strategy is: " + decisions.length);

        for(double i = 1; i <= decisions.length; i = i + 1.0){
            costMatrix.add(costMap.get(i));
        } 

        averageTime = totalTime / (numCustomerServed);
      
    
        return costMatrix.get(costMatrix.size()-1);


        
    }
}
