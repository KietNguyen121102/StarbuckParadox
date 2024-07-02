public class Customer {
    
    double arrivalTime;
    double departureTime;
    double waitTime;
    
    boolean online = false;
    int preferences; 
    int index;


    public Customer(int arrival){
        arrivalTime = arrival;
    }

    public double getArrivalTime(){
        return this.arrivalTime;
    }

    public double getDepartureTime(){
        return this.departureTime;
    }

    public void setTotalTime(double processingTime){
        waitTime = processingTime;
    }

    public void setDepartureTime(double departs){
        departureTime = departs;
    }

    public void setArrivalTime(double arrives){
        this.arrivalTime = arrives;
    }

    public void beOnline(){
        this.online = true;
    }



}
