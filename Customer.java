public class Customer {
    double arrivalTime;
    double departureTime;
    
    boolean online = false;
    int preferences; 
    int index;


    public Customer(int arrives){
        arrivalTime = arrives;
    }

    public double getArrivalTime(){
        return this.arrivalTime;
    }

    public double getDepartureTime(){
        return this.departureTime;
    }

    public double totalTime(){
        return departureTime - arrivalTime;
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
