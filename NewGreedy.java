import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NewGreedy{
    public static ConcurrentHashMap<ArrayList<Integer>, ArrayList<Double>> noReallocationGreedy(int numCustomer, int numOnline, int numPhysical, double alpha, int arrivalRate, int cost){

        ConcurrentHashMap<ArrayList<Integer>, ArrayList<Double>> choiceSequences = new ConcurrentHashMap<ArrayList<Integer>, ArrayList<Double>>();
        ArrayList<Integer> firstSequence = new ArrayList<Integer>();
        ArrayList<Double> firstBaristaState = new ArrayList<Double>();
        for(int i = 0; i < numOnline + numPhysical; i++){firstBaristaState.add(1.0);}
        firstBaristaState.add(0.0);
        choiceSequences.put(firstSequence, firstBaristaState);


         

        for(int customerNo = 0; customerNo < numCustomer; customerNo++){
            System.out.println(choiceSequences.keySet().size());
            int loopRun = 0;
            System.out.println("Customer no: " + customerNo);
            List<ArrayList<Integer>> keys = new ArrayList<>(choiceSequences.keySet());

            for(ArrayList<Integer> choiceSequence: keys){



                // System.out.println("Current barista state: " + choiceSequences.get(choiceSequence));

                

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
                    // choiceSequences.remove(choiceSequence);
                    choiceSequence.add(1);
                    choiceSequences.put(choiceSequence, currentBaristaState);
                    System.out.println("Customer " + customerArrivalTime + " choose queue");



                }
                else{
                    // choiceSequences.remove(choiceSequence);

        
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
        
        return choiceSequences;

    }
}