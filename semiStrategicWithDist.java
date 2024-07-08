import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class semiStrategicWithDist {
     public static double reallocSemiStrategicWithDist(int numOnline, int numPhysical, double alpha, double physicalPopulation, double onlinePopulation, int numCustomer){
        int numCustomerArrived = 0;
        ArrayList<Double> totalTimeArray = new ArrayList<Double>();
        ArrayList<Integer> currentStrategy = new ArrayList<Integer>();
        Map<Double, Double> costMap = new HashMap<Double, Double>();

        double[] onlineBarista = new double[numOnline];
        for (int i = 0; i < numOnline; i++) {
            onlineBarista[i] = 0;
        }

        double[] physicalBarista = new double[numPhysical];
        for (int i = 0; i < numPhysical; i++) {
            physicalBarista[i] = 0;
        }


        Queue<Customer> physicalLine = new LinkedList<Customer>();
        Queue<Customer> onlineQueue = new LinkedList<Customer>();

        double totalTime = 0;
        double averageTime;
        int time = 0;
    
        for (int t = 0; t <= 10000; t++) { 
            int cost = helperDistribution.costNormalDist(10, 2); 
            
            // System.out.println("Time to next customer is:" + timeToNextCust);
            if (numCustomerArrived < numCustomer) {
                Customer customer = new Customer(time);
                double i = Math.random();
                int arrivalRate = helperDistribution.gapExponentialDist(0.2);
                
                if (i < physicalPopulation) {
                    physicalLine.add(customer);
                    currentStrategy.add(0);
                    numCustomerArrived++;
                    System.out.println("Customer " + customer.getArrivalTime() + " chooses physical line");

                } else if(i >= physicalPopulation && i <= (onlinePopulation + physicalPopulation) ) {
                    onlineQueue.add(customer);
                    customer.beOnline();
                    currentStrategy.add(1);
                    numCustomerArrived++;

                    System.out.println("Customer " + customer.getArrivalTime() + " chooses online line");

                } else {
                    int[] ifQueue = new int[currentStrategy.size()+1];
                    int[] ifLine = new int[currentStrategy.size()+1];

                    for(int j = 0; j < currentStrategy.size(); j++){
                        ifQueue[j] = currentStrategy.get(j);
                        ifLine[j] = currentStrategy.get(j);
                    }

                    ifQueue[ifQueue.length-1] = 1;
                    ifQueue[ifLine.length-1] = 0;

                    // System.out.println(ifQueue.length);
                

                    double queueExpectedCost = SchedulerSimulation.schedulerSimulation(numOnline, numPhysical, cost, arrivalRate, alpha, ifQueue);
                    double lineExpectedCost = SchedulerSimulation.schedulerSimulation(numOnline, numPhysical, cost, arrivalRate, alpha, ifLine);
                  
                    if(numPhysical == 0){lineExpectedCost = queueExpectedCost+1;}
                    if(numOnline == 0){queueExpectedCost = lineExpectedCost+1;}

                    if (lineExpectedCost < queueExpectedCost) {
                        physicalLine.add(customer);
                        currentStrategy.add(0);
                        System.out.println("Customer " + customer.getArrivalTime() + " chooses physical line");
                    } else{
                        onlineQueue.add(customer);
                        customer.beOnline();
                        currentStrategy.add(1);
                        System.out.println("Customer " + customer.getArrivalTime() + " chooses online line");
                    }
                    numCustomerArrived++;
                    time += arrivalRate;
                }
            }

            for (int num = 0; num < numOnline; num++) {
                Customer servingCustomer = new Customer(0);

                if ((time >= onlineBarista[num]) && ((physicalLine.size() != 0) || (onlineQueue.size() != 0))) {
                    if ((onlineQueue.size() != 0)) {
                        servingCustomer = onlineQueue.poll();
                    } else if (physicalLine.size() != 0) {
                        servingCustomer = physicalLine.poll();
                    }

                   
                    onlineBarista[num] = (time + cost);
                   
                    servingCustomer.setDepartureTime(onlineBarista[num]);
                    // System.out.println("Customer serving: " + servingCustomer.getArrivalTime());
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
                Customer servingCustomer2 = new Customer(0);

                if ((time >= physicalBarista[num]) && ((physicalLine.size() != 0) || (onlineQueue.size() != 0))) {
                    if ((physicalLine.size() != 0)) {
                        servingCustomer2 = physicalLine.poll();
                    } else if (onlineQueue.size() != 0) {
                        servingCustomer2 = onlineQueue.poll();
                    }


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

        System.out.println(costMap);

        double total = 0;
        for(int i = 0; i < numCustomer; i++){
            total += costMap.get((double)i);
        }


        return total;
    }

    public static double noReallocSemiStrategicWithDist(int numOnline, int numPhysical, double alpha, double physicalPopulation, double onlinePopulation, int numCustomer){
        int numCustomerArrived = 0;
        ArrayList<Double> totalTimeArray = new ArrayList<Double>();
        ArrayList<Integer> currentStrategy = new ArrayList<Integer>();
        Map<Double, Double> costMap = new HashMap<Double, Double>();


        double[] onlineBarista = new double[numOnline];
        for (int i = 0; i < numOnline; i++) {
            onlineBarista[i] = 0;
        }

        double[] physicalBarista = new double[numPhysical];
        for (int i = 0; i < numPhysical; i++) {
            physicalBarista[i] = 0;
        }



        Queue<Customer> physicalLine = new LinkedList<Customer>();
        Queue<Customer> onlineQueue = new LinkedList<Customer>();

        double totalTime = 0;
        double averageTime;
        int time = 0;

        for (int t = 0; t <= 10000; t++) {
            int cost = helperDistribution.costNormalDist(10, 2); 
            
            // System.out.println("Time to next customer is:" + timeToNextCust);
            if (numCustomerArrived < numCustomer) {
                
                Customer customer = new Customer(time);
                double i = Math.random();
                int arrivalRate = helperDistribution.gapExponentialDist(0.2);
                
                if (i < physicalPopulation) {
                    physicalLine.add(customer);
                    currentStrategy.add(0);
                    numCustomerArrived++;
                    System.out.println("Customer " + customer.getArrivalTime() + " chooses physical line");

                } else if(i >= physicalPopulation && i <= (onlinePopulation + physicalPopulation) ) {
                    onlineQueue.add(customer);
                    customer.beOnline();
                    currentStrategy.add(1);
                    numCustomerArrived++;

                    System.out.println("Customer " + customer.getArrivalTime() + " chooses online line");

                } else {
                    int[] ifQueue = new int[currentStrategy.size()+1];
                    int[] ifLine = new int[currentStrategy.size() +1];

                    for(int j = 0; j < currentStrategy.size(); j++){
                        ifQueue[j] = currentStrategy.get(j);
                        ifLine[j] = currentStrategy.get(j);
                    }

                    ifQueue[ifQueue.length-1] = 1;
                    ifQueue[ifLine.length-1] = 0;

                    // System.out.println(ifQueue.length);
                

                    double queueExpectedCost = SchedulerSimulation.schedulerSimulation(numOnline, numPhysical, cost, arrivalRate, alpha, ifQueue);
                    double lineExpectedCost = SchedulerSimulation.schedulerSimulation(numOnline, numPhysical, cost, arrivalRate, alpha, ifLine);
                  
                    if(numPhysical == 0){lineExpectedCost = queueExpectedCost+1;}
                    if(numOnline == 0){queueExpectedCost = lineExpectedCost+1;}

                    if (lineExpectedCost < queueExpectedCost) {
                        physicalLine.add(customer);
                        currentStrategy.add(0);
                        System.out.println("Customer " + customer.getArrivalTime() + " chooses physical line");
                    } else{
                        onlineQueue.add(customer);
                        customer.beOnline();
                        currentStrategy.add(1);
                        System.out.println("Customer " + customer.getArrivalTime() + " chooses online line");
                    }
                    numCustomerArrived++;
                    time += arrivalRate;
                }
            }


          
          
        

            for (int num = 0; num < numOnline; num++) {
                Customer servingCustomer = new Customer(0);

                if ((time >= onlineBarista[num]) && onlineQueue.size() != 0) {
                    if ((onlineQueue.size() != 0)) {
                        servingCustomer = onlineQueue.poll();
                    }
                   
                    onlineBarista[num] = (time + cost);
                    servingCustomer.setDepartureTime(onlineBarista[num]);
                    // System.out.println("Customer serving: " + servingCustomer.getArrivalTime());
                    double waitTime = (servingCustomer.departureTime-servingCustomer.arrivalTime) * alpha;
                    costMap.put(servingCustomer.arrivalTime, waitTime);
                }

            }

           

            for (int num = 0; num < numPhysical; num++) {
                Customer servingCustomer2 = new Customer(0);

                if ((time >= physicalBarista[num]) && physicalLine.size() != 0) {
                    if ((physicalLine.size() != 0)) {
                        servingCustomer2 = physicalLine.poll();
                    } 

                    physicalBarista[num] = (time + cost);
                    servingCustomer2.setDepartureTime(physicalBarista[num]);
                   
                    double waitTime = (servingCustomer2.departureTime-servingCustomer2.arrivalTime);
                    servingCustomer2.setTotalTime(waitTime);
                    costMap.put(servingCustomer2.arrivalTime, waitTime);
                    
                }

            }
        }

        System.out.println(costMap);

        double total = 0;
        for(int i = 0; i < numCustomer; i++){
            total += costMap.get((double)i);
        }

            
        return total;
    }
}