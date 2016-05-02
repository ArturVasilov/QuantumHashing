package functions;

import java.util.Arrays;

/**
 * @author Artur Vasilov
 */
public class StandardQuantumHashFunction implements Function {

    private static final double EPS = 0.0000000001;

    private final int q;

    private double currentBest = Double.MAX_VALUE;
    private double[] bestParams = {};
    private final double[] values;

    private boolean optimizeCalculations = true;

    public StandardQuantumHashFunction(int q) {
        this.q = q;
        values = new double[q];
        Arrays.fill(values, 10);
    }

    public double calculate(double[] params) {
        int d = params.length;
        double currentWorst = 0;
        for (int i = 1; i <= q / 2; i++) {
            double result = 0;
            for (double param : params) {
                int x = (int) ((param * i) % q);
                if (values[x] > 1) {
                    values[x] = Math.cos(2 * Math.PI * x / q);
                    if (Math.abs(values[x]) < EPS) {
                        values[x] = 0;
                    }
                }
                result += values[x];
            }
            result /= d;
            if (result < EPS) {
                result = 0;
            }
            if (result > currentBest && optimizeCalculations) {
                return currentBest;
            }
            else if (result > currentWorst) {
                currentWorst = result;
            }
        }
        if (currentWorst < currentBest) {
            bestParams = new double[d];
            System.arraycopy(params, 0, bestParams, 0, d);
            currentBest = currentWorst;
        }
        return currentBest;
    }

    @Override
    public double[] bestParams() {
        return bestParams;
    }

    public void setOptimizeCalculations(boolean optimizeCalculations) {
        this.optimizeCalculations = optimizeCalculations;
    }
}
