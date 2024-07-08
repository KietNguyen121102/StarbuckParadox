import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class noReCostGen {
    public static ArrayList<Double> costGenerator(int numOnline, int numPhysical, int cost, int ArrivalRate, double alpha, int[] decisions) {

        int numCustomer = 0;
        int numCustomerServed = 0;
        HashMap<Double, Double> costMap = new HashMap<Double,Double>();
        ArrayList<Double> costMatrix = new ArrayList<Double>();

        double[] onlineBarista = new double[numOnline];
        for (int i = 0; i < numOnline; i++) {
            onlineBarista[i] = 0;
        }

        double[] physicalBarista = new double[numPhysical];
        for (int i = 0; i < numPhysical; i++) {
            physicalBarista[i] = 0;
        }



        Queue<Customer> line = new LinkedList<Customer>();
        Queue<Customer> queue = new LinkedList<Customer>();

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

            


            if (numCustomer <= decisions.length) {
        
                
                int decision = decisions[numCustomer-1];
                
                // System.out.println("Online queue expected waiting time is: " + queueWaitingTime + ", physical line expected waiting time is: " + lineWaitingTime);
                if (decision == 0) {
                    line.add(customer);
                    // System.out.println("Customer chooses physical line");
                } else if (decision == 1){
                    queue.add(customer);
                    customer.beOnline();
                    // System.out.println("Customer chooses online queue");
                }
            }

        

            for (int num = 0; num < numOnline; num++) {
                if (time >= onlineBarista[num] && numCustomerServed < numCustomer && (queue.size() != 0)) {
                    Customer servingCustomer = new Customer(-1);
                   
                    servingCustomer = queue.poll();
                    numCustomerServed++; 
                    servingCustomer.beOnline();
                   

                    // System.out.println("Online barista " + num + " serving customer " + servingCustomer.arrivalTime + " at time " + time);
                    onlineBarista[num] = (time + cost);
                    
                    servingCustomer.setDepartureTime(onlineBarista[num]);
                    if (servingCustomer.online == true) {
                        double waitTime = (servingCustomer.departureTime-servingCustomer.arrivalTime) * alpha;
                        costMap.put(servingCustomer.arrivalTime, waitTime);
                    } else {
                        double waitTime = (servingCustomer.departureTime-servingCustomer.arrivalTime);
                        servingCustomer.setTotalTime(waitTime);
                        costMap.put(servingCustomer.arrivalTime, waitTime);
                    }
                }

            }

           

            for (int num = 0; num < numPhysical; num++) {
                Customer servingCustomer2 = new Customer(-1);
                if (time >= physicalBarista[num] && numCustomerServed < numCustomer && (line.size() != 0)) {
                   
                   
                    servingCustomer2 = line.poll();
                    numCustomerServed++;
                   

                    // System.out.println("Physical barista " + num + " serving customer " + servingCustomer2.arrivalTime + " at time " + time);
                    physicalBarista[num] = (time + cost);
                    
                    servingCustomer2.setDepartureTime(physicalBarista[num]);
                    if (servingCustomer2.online == true) {
                        double waitTime = (servingCustomer2.departureTime-servingCustomer2.arrivalTime) * alpha;
                        costMap.put(servingCustomer2.arrivalTime, waitTime);
                    } else {
                        double waitTime = (servingCustomer2.departureTime-servingCustomer2.arrivalTime);
                        servingCustomer2.setTotalTime(waitTime);
                        costMap.put(servingCustomer2.arrivalTime, waitTime);

                    }
                }

            }

        }
        
       for(double customerNo = 0.0; customerNo < decisions.length; customerNo++){
            costMatrix.add(costMap.get(customerNo));
       }



        return costMatrix;
    }
}
