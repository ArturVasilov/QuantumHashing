package algorithms.stochastic;

import algorithms.IteratedQuantumHashingAlgorithm;
import functions.Function;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Artur Vasilov
 */
public class AdaptiveRandomSearch extends IteratedQuantumHashingAlgorithm {

    private final Function function;
    private final int maxValue;

    private final double stepFactor;

    private final Random random;

    public AdaptiveRandomSearch(Function function, int maxParamsCount, int maxValue, double stepFactor) {
        super(maxParamsCount);
        this.function = function;
        this.maxValue = maxValue;
        this.stepFactor = stepFactor;

        random = new SecureRandom();
    }

    @Override
    protected double findSolution(int size) {
        double[] params = new double[size];
        for (int i = 0; i < size; i++) {
            params[i] = random.nextInt(maxValue);
        }
        int step = 1;

        double result = function.calculate(params);

        double[] largeParams = new double[size];
        int iterations = (int) Math.round(Math.pow(2, size) * 2);
        boolean improved;
        do {
            improved = false;
            for (int i = 0; i < iterations; i++) {
                System.arraycopy(params, 0, largeParams, 0, size);
                int largerStep = increasedStepSize(step);
                for (int j = 0; j < size; j++) {
                    if (params[j] + step > maxValue) {
                        params[j] -= step;
                    } else if (params[j] - step < 0) {
                        params[j] += step;
                    } else {
                        params[j] += step * (random.nextBoolean() ? 1 : -1);
                    }

                    if (largeParams[j] + largerStep > maxValue) {
                        largeParams[j] -= largerStep;
                    } else if (largeParams[j] - largerStep < 0) {
                        largeParams[j] += largerStep;
                    } else {
                        largeParams[j] += largerStep * (random.nextBoolean() ? 1 : -1);
                    }
                }
                double resultTemp = function.calculate(params);
                double resultTempLarger = function.calculate(largeParams);
                if (resultTempLarger < resultTemp) {
                    step = largerStep;
                    resultTemp = result;
                }
                if (resultTemp < result) {
                    result = resultTemp;
                    improved = true;
                    break;
                }
            }
            if (!improved) {
                step = increasedStepSize(step);
            }
        } while (improved || (step < maxValue / 8));

        return result;
    }

    private int increasedStepSize(int current) {
        int step = (int) Math.round(current + current * stepFactor);
        return step == current ? (current + 1) : step;
    }
}

