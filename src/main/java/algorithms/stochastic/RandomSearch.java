package algorithms.stochastic;

import algorithms.IteratedQuantumHashingAlgorithm;
import functions.Function;
import utils.OptimizationUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Artur Vasilov
 */
public class RandomSearch extends IteratedQuantumHashingAlgorithm {

    private final Function function;
    private final int maxValue;
    private final int step;

    private final Random random;

    public RandomSearch(Function function, int maxParamsCount, int maxValue, int step) {
        super(maxParamsCount);
        this.function = function;
        this.maxValue = maxValue;
        this.step = step;

        random = new SecureRandom();
    }

    @Override
    protected double findSolution(int size) {
        double[] params = new double[size];
        double[] tempParams = new double[size];
        for (int i = 0; i < size; i++) {
            params[i] = random.nextInt(maxValue);
        }
        System.arraycopy(params, 0, tempParams, 0, size);

        double result = function.calculate(params);

        int iterations = (int) Math.round(Math.pow(2, size) * 2);
        iterations = Math.min(iterations, 256);
        boolean improved;
        do {
            improved = false;
            for (int i = 0; i < iterations; i++) {
                OptimizationUtils.perturbateParams(tempParams, step, maxValue, random);
                double resultTemp = function.calculate(tempParams);
                if (resultTemp < result) {
                    result = resultTemp;
                    System.arraycopy(tempParams, 0, params, 0, size);
                    improved = true;
                    break;
                } else {
                    System.arraycopy(params, 0, tempParams, 0, size);
                }
            }
        } while (improved);

        return result;
    }
}
