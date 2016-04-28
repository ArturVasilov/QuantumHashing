package algorithms.stochastic;

import algorithms.IteratedQuantumHashingAlgorithm;
import functions.Function;
import utils.OptimizationUtils;

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
        double[] tempParams = new double[size];
        double[] largeTempParams = new double[size];
        for (int i = 0; i < size; i++) {
            params[i] = random.nextInt(maxValue);
        }
        System.arraycopy(params, 0, tempParams, 0, size);
        System.arraycopy(params, 0, largeTempParams, 0, size);

        int step = 1;

        double result = function.calculate(params);

        int iterations = (int) Math.round(Math.pow(2, size) * 2);
        boolean improved;
        do {
            improved = false;
            for (int i = 0; i < iterations; i++) {
                int largerStep = increasedStepSize(step);
                OptimizationUtils.perturbateParams(tempParams, step, maxValue, random);
                OptimizationUtils.perturbateParams(largeTempParams, largerStep, maxValue, random);

                double resultTemp = function.calculate(params);
                double resultTempLarger = function.calculate(largeTempParams);
                if (resultTempLarger < resultTemp) {
                    step = largerStep;
                    resultTemp = resultTempLarger;
                    if (resultTemp < result) {
                        System.arraycopy(largeTempParams, 0, tempParams, 0, size);
                    }
                }
                if (resultTemp < result) {
                    result = resultTemp;
                    System.arraycopy(tempParams, 0, params, 0, size);
                    improved = true;
                    break;
                } else {
                    System.arraycopy(params, 0, tempParams, 0, size);
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

