import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Greedy{
    public static double noReallocationGreedy(int numOnline, int numPhysical, int cost, int arrivalRate, double alpha, int numCustomer){

        ConcurrentHashMap<ArrayList<Integer>, ArrayList<Double>> choiceSequences = new ConcurrentHashMap<ArrayList<Integer>, ArrayList<Double>>();
        ArrayList<Integer> firstSequence = new ArrayList<Integer>();
        ArrayList<Double> firstBaristaState = new ArrayList<Double>();
        for(int i = 0; i < numOnline + numPhysical; i++){firstBaristaState.add(1.0);}
        firstBaristaState.add(0.0);
        choiceSequences.put(firstSequence, firstBaristaState);
        Map<Double, Double> costMap = new HashMap<Double, Double>();

        double min = 1000000;

        for(int customerNo = 0; customerNo < numCustomer; customerNo++){
            System.out.println(choiceSequences.keySet().size());
            System.out.println("Customer no: " + customerNo);
            List<ArrayList<Integer>> keys = new ArrayList<>(choiceSequences.keySet());

            for(ArrayList<Integer> choiceSequence: keys){
                int customerArrivalTime = (customerNo)*arrivalRate;
                
                double customerWaitingTime = 0;
                double onlineProcessingTime = 0;
                double physicalProcessingTime = 0;

                
               
                ArrayList<Double> currentBaristaState = new ArrayList<Double>();
                currentBaristaState = choiceSequences.get(choiceSequence);
                choiceSequences.remove(choiceSequence);


                //Online processing
                

                int nextOnlineBarista = 0;
                double nextOnlineSlot = currentBaristaState.get(0);
                

                for(int onlineBaristaNo = 0; onlineBaristaNo < numOnline; onlineBaristaNo++){
                    if(currentBaristaState.get(onlineBaristaNo) < nextOnlineSlot){
                        nextOnlineSlot = currentBaristaState.get(onlineBaristaNo);
                        nextOnlineBarista = onlineBaristaNo;
                    }
                }

                if(nextOnlineSlot-1 == 0){onlineProcessingTime = alpha*cost;}
                else{onlineProcessingTime = ((nextOnlineSlot)*cost + nextOnlineBarista - customerArrivalTime)*alpha;}
                    

                
                int nextPhysicalBarista = numOnline;
                double nextPhysicalSlot = currentBaristaState.get(numOnline);

                for(int physicalBaristaNo = numOnline; physicalBaristaNo < numOnline+numPhysical; physicalBaristaNo++){
                    if(currentBaristaState.get(physicalBaristaNo) < nextPhysicalSlot){
                        nextPhysicalSlot = currentBaristaState.get(physicalBaristaNo); 
                        nextPhysicalBarista = physicalBaristaNo;
                    }
                }
                if(nextPhysicalSlot-1 == 0){physicalProcessingTime = cost;}
                else{physicalProcessingTime = (nextPhysicalSlot)*cost + nextPhysicalBarista - customerArrivalTime;}
                

                System.out.println("Online processing: " + onlineProcessingTime + " at barista " + nextOnlineBarista +  " Physical processing: " + physicalProcessingTime + " at barista " + nextPhysicalBarista +" at customer "+ customerArrivalTime);

                choiceSequences.remove(choiceSequence);

                if(onlineProcessingTime > physicalProcessingTime){
                    currentBaristaState.set(nextPhysicalBarista, currentBaristaState.get(nextPhysicalBarista) + 1);
                    customerWaitingTime = physicalProcessingTime;

                    currentBaristaState.set(numOnline+numPhysical, currentBaristaState.get(numOnline+numPhysical) + customerWaitingTime);
                    costMap.put((double) customerNo, customerWaitingTime);
                    // choiceSequences.remove(choiceSequence);
                    choiceSequence.add(0);
                    choiceSequences.put(choiceSequence, currentBaristaState);
                    // System.out.println("Customer " + customerArrivalTime + "choose physical line");
                    System.out.println("Customer " + customerArrivalTime + " choose line");
                    

                }
                else if(onlineProcessingTime < physicalProcessingTime){
                    currentBaristaState.set(nextOnlineBarista, currentBaristaState.get(nextOnlineBarista) + 1);
                    customerWaitingTime = onlineProcessingTime;

                    currentBaristaState.set(numOnline+numPhysical, currentBaristaState.get(numOnline+numPhysical) + customerWaitingTime);
                    costMap.put((double) customerNo, customerWaitingTime);

                    // choiceSequences.remove(choiceSequence);
                    choiceSequence.add(1);
                    choiceSequences.put(choiceSequence, currentBaristaState);
                    System.out.println("Customer " + customerArrivalTime + " choose queue");
                }
                else{        
                    //line branch
                    ArrayList<Integer> alternativeChoiceSequence = new ArrayList<Integer>();
                    ArrayList<Double> alternativeBaristaState = new ArrayList<Double>();
                    
                    for(int i = 0; i < choiceSequence.size(); i++){
                        alternativeChoiceSequence.add(choiceSequence.get(i));
                    }
                    for(int i = 0; i < currentBaristaState.size(); i++){
                        alternativeBaristaState.add(currentBaristaState.get(i));
                    }

                    alternativeBaristaState.set(nextPhysicalBarista, alternativeBaristaState.get(nextPhysicalBarista) + 1);
                    System.out.println("Alternative state: " + alternativeBaristaState);
                    double alternativeCustomerWaitingTime = physicalProcessingTime;
                    alternativeBaristaState.set(numOnline+numPhysical, currentBaristaState.get(numOnline+numPhysical) + alternativeCustomerWaitingTime);
                    alternativeChoiceSequence.add(0);
                    alternativeBaristaState.add(0.0);
                    choiceSequences.put(alternativeChoiceSequence, alternativeBaristaState);
                    // System.out.println("Add another branch");
                    
                    //queue branch
                    
                    currentBaristaState.set(nextOnlineBarista, currentBaristaState.get(nextOnlineBarista) + 1);
                    System.out.println("Current state: " + currentBaristaState);
                    customerWaitingTime = onlineProcessingTime;

                    currentBaristaState.set(numOnline+numPhysical, currentBaristaState.get(numOnline+numPhysical) + customerWaitingTime);
                    choiceSequence.add(1);
                    currentBaristaState.add(1.0);
                    choiceSequences.put(choiceSequence, currentBaristaState);

                    System.out.println("Ties at: " + customerArrivalTime);
                                     
                }
            }
        }
        
        for(Map.Entry<ArrayList<Integer>, ArrayList<Double>> waitingTime : choiceSequences.entrySet()){
            double totalCost = 0;
            for(int i = 0; i < waitingTime.getValue().size(); i++){
                totalCost = totalCost + waitingTime.getValue().get(i);
            }
            if(totalCost < min){
               min = totalCost;
            }
        }

        return min;
    }

    public static double ReallocationGreedy(int numOnline, int numPhysical, int cost, int arrivalRate, double alpha, int numCustomer) {
        
        ArrayList<Integer> greedyStrategy = new ArrayList<Integer>();
        double totalCost = 0;
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
                        totalCost += waitTime; 
                        costMap.put(servingCustomer.arrivalTime, waitTime);
                    } else {
                        double waitTime = (servingCustomer.departureTime-servingCustomer.arrivalTime);
                        totalCost += waitTime; 
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
                        totalCost += waitTime; 
                        costMap.put(servingCustomer2.arrivalTime, waitTime);
                    } else {
                        double waitTime = (servingCustomer2.departureTime-servingCustomer2.arrivalTime);
                        totalCost += waitTime; 
                        servingCustomer2.setTotalTime(waitTime);
                        costMap.put(servingCustomer2.arrivalTime, waitTime);

                    }
                }

            }

           

        }
        System.out.println("Code runs here");
        System.out.println(costMap);
        return totalCost;
    }
}