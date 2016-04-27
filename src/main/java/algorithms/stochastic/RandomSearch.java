package algorithms.stochastic;

import algorithms.IteratedQuantumHashingAlgorithm;
import functions.Function;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Artur Vasilov
 */
public class RandomSearch extends IteratedQuantumHashingAlgorithm {

    private final Function function;
    private final int maxValue;
    private final int stepSize;

    private final Random random;

    public RandomSearch(Function function, int maxParamsCount, int maxValue, int stepSize) {
        super(maxParamsCount);
        this.function = function;
        this.maxValue = maxValue;
        this.stepSize = stepSize;

        random = new SecureRandom();
    }

    @Override
    protected double findSolution(int size) {
        double[] params = new double[size];
        for (int i = 0; i < size; i++) {
            params[i] = random.nextInt(maxValue);
        }

        double result = function.calculate(params);

        int iterations = (int) Math.round(Math.pow(2, size) * 2);
        boolean improved;
        do {
            improved = false;
            for (int i = 0; i < iterations; i++) {
                for (int j = 0; j < size; j++) {
                    if (params[j] + stepSize > maxValue) {
                        params[j] -= stepSize;
                    } else if (params[j] - stepSize < 0) {
                        params[j] += stepSize;
                    } else {
                        params[j] += stepSize * (random.nextBoolean() ? 1 : -1);
                    }
                }
                double resultTemp = function.calculate(params);
                if (resultTemp < result) {
                    result = resultTemp;
                    improved = true;
                    break;
                }
            }
        } while (improved);

        return result;
    }
}
