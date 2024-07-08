import java.util.Random;

public class helperDistribution {
    public static int costNormalDist(int mean, int sd){
        Random random = new Random();
        double gaussian = random.nextGaussian();
        double cost = mean + sd * gaussian;
        return (int) Math.round(cost);
    }

    public static int gapExponentialDist(double lambda) {
        Random random = new Random();
        double uniform = random.nextDouble();
        double cost = Math.log(1 - uniform) / (-lambda);
        return (int) Math.round(cost);
    }
}
