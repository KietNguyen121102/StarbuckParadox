import java.util.*;

class Main{
    public static void main(String[] args) {
        ArrayList<Integer> greedyStrategy = new ArrayList<Integer>();
        double[] onlineBarista = new double[3];
        for(int i = 0; i < onlineBarista.length; i++){onlineBarista[i] = 0;}
        double[] physicalBarista = new double[2];
        for(int i = 0; i < physicalBarista.length; i++){physicalBarista[i] = 0;}
        greedyStrategy.add(1);
        ArrayList<Integer> decisionAtTies = new ArrayList<Integer>();
        OminiscientGreedy.omniscientGreedy(3, 2, 10, 1, 0.7, 50, 0, greedyStrategy, 1, onlineBarista, physicalBarista, 0, decisionAtTies);
    }
}