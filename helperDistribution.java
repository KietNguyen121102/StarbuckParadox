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

    public static double calculateMean(double[] data) {
        double sum = 0.0;
        for (double num : data) {
            sum += num;
        }
        return sum / data.length;
    }

    public static double calculateStandardDeviation(double[] data, double mean) {
        double sumSquaredDifferences = 0.0;
        for (double num : data) {
            sumSquaredDifferences += Math.pow(num - mean, 2);
        }
        return Math.sqrt(sumSquaredDifferences / data.length);
    }
}
