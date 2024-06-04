import java.util.*;


public class OminiscientGreedy {

    public static double omniscientGreedy(int onlineBaristas, int physicalBaristas, double processingTime, int ArrivalRate, double alpha, int totalCustomers, int numCustomerArrived, ArrayList<Integer> prevGreedyStrategy, int timeStart, double[] onlineBaristaStatus, double[] physicalBaristaStatus, double totalCost, ArrayList<Integer> decisionAtTies) {
       boolean equal = true;

    //    ArrayList<Integer> decisionAtTies = new ArrayList<Integer>();
       
       ArrayList<Integer> currentGreedyStrategy = prevGreedyStrategy;
       ArrayList<Integer> finalDecision = new ArrayList<Integer>();
       HashMap<Integer, ArrayList<Integer>> greedyAtEachCustomer = new HashMap<Integer, ArrayList<Integer>>();
       
        
        double[] costOfGreedy = new double[totalCustomers];
        int numCustomer = numCustomerArrived;
        int numCustomerServed = 0;
        


        int totalCustomer = totalCustomers;

        double[] onlineBarista = onlineBaristaStatus;
        
        double[] physicalBarista = physicalBaristaStatus;

        Queue<Customer> physicalLine = new LinkedList<Customer>();
        Queue<Customer> onlineQueue = new LinkedList<Customer>();

        double customerArrivalRate = ArrivalRate;
        int timeToNextCust = 0;
        double totalTime = totalCost;
        double averageTime;

        for (int time = timeStart; time <= 5000; time = time + 1) {
            // System.out.println("At time t = " + time);

            Customer customer = new Customer(1);

            // Customer arrives at a fixed interval
            timeToNextCust++;
            // System.out.println("Time to next customer is:" + timeToNextCust);
            if (timeToNextCust == customerArrivalRate) { 
                customer.setArrivalTime(time);
                timeToNextCust = 0;
                numCustomer++;
            }



            if (customer.getArrivalTime() != 0 && numCustomer <= totalCustomers) {
                // System.out.println("Customer waiting in queue is " + onlineQueue.size());
                // System.out.println("Customer waiting in physical line is " + physicalLine.size());
             

                //comparing expected waiting time
                ArrayList<Integer> previousDecision = new ArrayList<Integer>();

               
                int[] previousDecisions = new int[previousDecision.size()];

                for(int i = 0; i < previousDecisions.length; i++){
                    previousDecisions[i] = previousDecision.get(i);
                }
                
                
              
                int[] ifQueue = new int[currentGreedyStrategy.size()];
                int[] ifLine = new int[currentGreedyStrategy.size()];

                for(int i = 0; i < currentGreedyStrategy.size(); i++){
                    ifQueue[i] = currentGreedyStrategy.get(i);
                    ifLine[i] = currentGreedyStrategy.get(i);
                }

                ifQueue[ifQueue.length-1] = 1; 
                ifLine[ifLine.length-1] = 0; 

                


                double queueWaitingTime = SchedulerSimulation.schedulerSimulation(onlineBaristas, physicalBaristas, processingTime, ArrivalRate, alpha, ifQueue);

                // System.out.println("queue waiting time for customer " + customer.getArrivalTime() + " is " + queueWaitingTime);

                double lineWaitingTime = SchedulerSimulation.schedulerSimulation(onlineBaristas, physicalBaristas, processingTime, ArrivalRate, alpha, ifLine);

                // System.out.println("line waiting time for customer " + customer.getArrivalTime() + " is " + lineWaitingTime);

                
                
                // System.out.println("Online queue expected waiting time is: " + queueWaitingTime + ", physical line expected waiting time is: " + lineWaitingTime);
                if (lineWaitingTime < queueWaitingTime && numCustomer <= totalCustomer ) {
                     
                    physicalLine.add(customer);
                    finalDecision.add(0);
                    currentGreedyStrategy.add(0);
                    // System.out.println("Customer chooses physical line");
                } 
                else if(lineWaitingTime == queueWaitingTime){
                    System.out.println("Tie happens at customer " + customer.getArrivalTime());
                    ArrayList<Integer> alternativeGreedy = (ArrayList<Integer>) currentGreedyStrategy.clone();
                    ArrayList<Integer> alternativeDecistionAtTies = (ArrayList<Integer>)  decisionAtTies.clone();

                    currentGreedyStrategy.add(0);
                    decisionAtTies.add(0);
                    omniscientGreedy(onlineBaristas, physicalBaristas, processingTime, ArrivalRate, alpha, totalCustomers, numCustomer, currentGreedyStrategy, time + 1, onlineBarista, physicalBarista, totalTime, decisionAtTies);
                    
                    alternativeGreedy.add(1);
                    alternativeDecistionAtTies.add(1);
                    omniscientGreedy(onlineBaristas, physicalBaristas, processingTime, ArrivalRate, alpha, totalCustomers, numCustomer, alternativeGreedy, time + 1, onlineBarista, physicalBarista, totalTime, alternativeDecistionAtTies);


                }
                else if(numCustomer <= totalCustomer){
                    
                    if(lineWaitingTime == queueWaitingTime){
                        equal = true;
                    }
                    onlineQueue.add(customer);
                    customer.beOnline();
                    finalDecision.add(1);
                    currentGreedyStrategy.add(1);
                    // System.out.println("Customer chooses online queue");
                }

            

               
    
                }

           Customer servingCustomer = new Customer(1);
        
            for (int num = 0; num < onlineBaristas; num++) {
                if ((time >= onlineBarista[num]) && ((physicalLine.size() != 0) || (onlineQueue.size() != 0))) {
                    if ((onlineQueue.size() != 0)) {
                        servingCustomer = onlineQueue.peek();
                        onlineQueue.poll();
                        numCustomerServed++;}
                   
                    onlineBarista[num] = (time + processingTime);
                    servingCustomer.setDepartureTime(onlineBarista[num]);
                    if (servingCustomer.online == true) {
                        totalTime += (servingCustomer.totalTime() * alpha);
                        // costOfGreedy[(int) (servingCustomer.arrivalTime - 1)] = (servingCustomer.totalTime()*alpha);
                    } else {
                        totalTime += servingCustomer.totalTime();
                        costOfGreedy[(int) (servingCustomer.arrivalTime - 1)] = servingCustomer.totalTime();
                    }

        

                }

            }

            Customer servingCustomer2 = new Customer(1);

            for (int num = 0; num < physicalBaristas; num++) {
                if ((time >= physicalBarista[num]) && ((physicalLine.size() != 0) || (onlineQueue.size() != 0))) {
                    if ((physicalLine.size() != 0)) {
                        servingCustomer2 = physicalLine.peek();
                        physicalLine.poll();
                        numCustomerServed++;}

                    physicalBarista[num] = (time + processingTime);
                    servingCustomer2.setDepartureTime(physicalBarista[num]);
                    if (servingCustomer2.online == true) {
                        totalTime += (servingCustomer2.totalTime() * alpha);
                        costOfGreedy[(int) (servingCustomer2.arrivalTime - 1)] = (servingCustomer2.totalTime() * alpha);
                    } else {
                        totalTime += servingCustomer2.totalTime();
                        // costOfGreedy[(int) (servingCustomer2.arrivalTime - 1)] = (servingCustomer2.totalTime());
                    }
                   

                }

            }

           
        }

       
        System.out.println("Cost of omniscient greedy is: " + totalTime + " after serving " + numCustomerServed + " customers");
      
        ArrayList<Double> greedyCost = new ArrayList<Double>();
        for(int i = 0; i < costOfGreedy.length; i++){
            greedyCost.add(costOfGreedy[i]);
        }
        System.out.println("Omniscient Greedy: " + currentGreedyStrategy);
        System.out.println("Decision at ties:" + decisionAtTies);
      
        
        greedyAtEachCustomer.put(totalCustomer, finalDecision);
        // System.out.println(greedyAtEachCustomer);
        return totalTime;

}
}
