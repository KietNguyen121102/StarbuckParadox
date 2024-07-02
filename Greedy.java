import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Greedy{
    public static double noReallocationGreedy(int numOnline, int numPhysical, int cost, int arrivalRate, double alpha, int numCustomer){

        ArrayList<Integer> greedyStrategy = new ArrayList<Integer>();
        Map<Double, Double> costMap = new HashMap<Double, Double>();
 

        int numCustomerServed = 0; 
        int numCustomerArrived = 0;
        
        double[] onlineBarista = new double[numOnline];
        double[] physicalBarista = new double[numPhysical];
        for (int i = 0; i < numOnline; i++) {
            onlineBarista[i] = 0;
        }
        for (int i = 0; i < numPhysical; i++) {
            physicalBarista[i] = 0;
        }

        Queue<Customer> line = new LinkedList<Customer>();
        Queue<Customer> queue = new LinkedList<Customer>();

        for(int time = 0; time <= 5000; time++){

           

            if(time % arrivalRate == 0 && numCustomerArrived < numCustomer){
                Customer customer = new Customer(time);
                
                int[] ifQueue = new int[greedyStrategy.size()+1];
                int[] ifLine = new int[greedyStrategy.size() +1];

                for(int i = 0; i < greedyStrategy.size(); i++){
                    ifQueue[i] = greedyStrategy.get(i);
                    ifLine[i] = greedyStrategy.get(i);
                }

                ifQueue[ifQueue.length-1] = 1;
                ifQueue[ifLine.length-1] = 0;

                
                double queueExpectedCost = SchedulerSimulation.schedulerSimulation(numOnline, numPhysical, cost, arrivalRate, alpha, ifQueue);
                double lineExpectedCost = SchedulerSimulation.schedulerSimulation(numOnline, numPhysical, cost, arrivalRate, alpha, ifLine);
                
                if(numPhysical == 0){lineExpectedCost = queueExpectedCost+1;}
                if(numOnline == 0){queueExpectedCost = lineExpectedCost+1;}


                if(lineExpectedCost < queueExpectedCost){
                    line.add(customer);
                    greedyStrategy.add(0);
                    numCustomerArrived++;
                }
                else{
                    queue.add(customer);
                    greedyStrategy.add(1);
                    numCustomerArrived++;

                }
            }

            for (int num = 0; num < numOnline; num++) {
                if (time >= onlineBarista[num] && numCustomerServed < numCustomer && queue.size() != 0 ) {
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
                if (time >= physicalBarista[num] && numCustomerServed < numCustomer && line.size() != 0) {
                   
                   
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
        // System.out.println(costMap);

        double total = 0;
        for(int i = 0; i < numCustomer; i++){
            total += costMap.get((double)i);
        }

            
        return total;
    }
    

    public static double ReallocationGreedy(int numOnline, int numPhysical, int cost, int arrivalRate, double alpha, int numCustomer) {
        
        ArrayList<Integer> greedyStrategy = new ArrayList<Integer>();
        Map<Double, Double> costMap = new HashMap<Double, Double>();
 

        int numCustomerServed = 0; 
        int numCustomerArrived = 0;
        
        double[] onlineBarista = new double[numOnline];
        double[] physicalBarista = new double[numPhysical];
        for (int i = 0; i < numOnline; i++) {
            onlineBarista[i] = 0;
        }
        for (int i = 0; i < numPhysical; i++) {
            physicalBarista[i] = 0;
        }

        Queue<Customer> line = new LinkedList<Customer>();
        Queue<Customer> queue = new LinkedList<Customer>();

        for(int time = 0; time <= 5000; time++){

           

            if(time % arrivalRate == 0 && numCustomerArrived < numCustomer){
                Customer customer = new Customer(time);
                
                int[] ifQueue = new int[greedyStrategy.size()+1];
                int[] ifLine = new int[greedyStrategy.size() +1];

                for(int i = 0; i < greedyStrategy.size(); i++){
                    ifQueue[i] = greedyStrategy.get(i);
                    ifLine[i] = greedyStrategy.get(i);
                }

                ifQueue[ifQueue.length-1] = 1;
                ifQueue[ifLine.length-1] = 0;

                double queueExpectedCost = SchedulerSimulation.schedulerSimulation(numOnline, numPhysical, cost, arrivalRate, alpha, ifQueue);
                double lineExpectedCost = SchedulerSimulation.schedulerSimulation(numOnline, numPhysical, cost, arrivalRate, alpha, ifLine);
                
                if(numPhysical == 0){lineExpectedCost = queueExpectedCost+1;}
                if(numOnline == 0){queueExpectedCost = lineExpectedCost+1;}

                if(lineExpectedCost < queueExpectedCost){
                    line.add(customer);
                    greedyStrategy.add(0);
                    numCustomerArrived++;
                }
                else{
                    queue.add(customer);
                    greedyStrategy.add(1);
                    numCustomerArrived++;

                }
            }

            for (int num = 0; num < numOnline; num++) {
                if (time >= onlineBarista[num] && numCustomerServed < numCustomer && (queue.size() != 0 || line.size() != 0)) {
                    Customer servingCustomer = new Customer(-1);
                    if ((queue.size() != 0)) {
                        servingCustomer = queue.poll();
                        numCustomerServed++; 
                        servingCustomer.beOnline();
                    } else if (line.size() != 0) {
                        servingCustomer = line.poll();
                        numCustomerServed++;
                    }

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
                if (time >= physicalBarista[num] && numCustomerServed < numCustomer && (queue.size() != 0 || line.size() != 0)) {
                   
                    if ((line.size() != 0)) {
                        servingCustomer2 = line.poll();
                        numCustomerServed++;
                    } else if (queue.size() != 0) {
                        servingCustomer2 = queue.poll();
                        numCustomerServed++;
                        servingCustomer2.beOnline();
                    }

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
        // System.out.println(costMap);

        double total = 0;
        for(int i = 0; i < numCustomer; i++){
            total += costMap.get((double)i);
        }

            
        return total;
    }
}