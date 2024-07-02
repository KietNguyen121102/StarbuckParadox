import java.util.*;

public class SchedulerSimulation {
    public static double schedulerSimulation(int onlineBaristas, int physicalBaristas, double processingTime, int ArrivalRate, double alpha, int[] decisions) {
        if(decisions.length == 1 && decisions[0] == 1){return processingTime*alpha;}
        if(decisions.length == 1 && decisions[0] == 0){return processingTime;}
        // every minute in an 8-hour working day
        // every minute in an 8-hour working day
        int numCustomer = 0;
        HashMap<Double, Double> costMap = new HashMap<Double,Double>();

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

        for (int time = 0; time <= 4800; time = time + 1) {
        

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

        

            for (int num = 0; num < onlineBaristas; num++) {
                Customer servingCustomer = new Customer(0);
        
                if ((time >= onlineBarista[num]) && ((physicalLine.size() != 0) || (onlineQueue.size() != 0))) {
                    if ((onlineQueue.size() != 0)) {
                        servingCustomer = onlineQueue.peek();
                        onlineQueue.poll();
                    } else if (physicalLine.size() != 0) {
                        servingCustomer = physicalLine.peek();
                        physicalLine.poll();
                    }

                    // System.out.println("Breakpoint 2");

                   
                    onlineBarista[num] = (time + processingTime);
                    servingCustomer.setDepartureTime(onlineBarista[num]);
                    if (servingCustomer.online == true) {
                        double waitTime = (servingCustomer.departureTime - servingCustomer.arrivalTime)* alpha;
                        costMap.put(servingCustomer.getArrivalTime(), waitTime);
                    } else {
                        double waitTime = (servingCustomer.departureTime - servingCustomer.arrivalTime);
                        costMap.put(servingCustomer.getArrivalTime(), waitTime);

                    }

                }

            }

            

            for (int num = 0; num < physicalBaristas; num++) {
                Customer servingCustomer2 = new Customer(0);
                if ((time >= physicalBarista[num]) && ((physicalLine.size() != 0) || (onlineQueue.size() != 0))) {
                    if ((physicalLine.size() != 0)) {
                        servingCustomer2 = physicalLine.peek();
                        physicalLine.poll();
                    } else if (onlineQueue.size() != 0) {
                        servingCustomer2 = onlineQueue.peek();
                        onlineQueue.poll();
                    }

                   
                    physicalBarista[num] = (time + processingTime);
                    servingCustomer2.setDepartureTime(physicalBarista[num]);
                    if (servingCustomer2.online == true) {
                        double waitTime = (servingCustomer2.departureTime - servingCustomer2.arrivalTime)* alpha;
                        costMap.put(servingCustomer2.getArrivalTime(), waitTime);
                    } else {
                        double waitTime = (servingCustomer2.departureTime - servingCustomer2.arrivalTime);
                        costMap.put(servingCustomer2.getArrivalTime(), waitTime);
                    }

                }

            }
        }

        double lastCustomer = (double) (decisions.length -1);
        return costMap.get(lastCustomer);
        
    }

}
