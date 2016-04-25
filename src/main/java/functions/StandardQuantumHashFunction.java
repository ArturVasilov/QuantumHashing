package functions;

/**
 * @author Artur Vasilov
 */
public class StandardQuantumHashFunction implements Function {

    private final int q;

    private double currentBest = Double.MAX_VALUE;
    private double[] bestParams = {};

    public StandardQuantumHashFunction(int q) {
        this.q = q;
    }

    public double calculate(double[] params) {
        int d = params.length;
        double currentWorst = 0;
        for (int i = 1; i < q; i++) {
            double result = 0;
            for (double param : params) {
                result += Math.cos(2 * Math.PI * param * i / q);
            }
            result /= d;
            if (result > currentBest) {
                return currentBest;
            } else if (result > currentWorst) {
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
}
