package algorithms.stochastic;

import algorithms.IteratedQuantumHashingAlgorithm;
import functions.Function;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Artur Vasilov
 */
public class RandomBruteSearch extends IteratedQuantumHashingAlgorithm {

    private final Function function;
    private final int maxValue;
    private final int iterations;

    private final Random random;

    public RandomBruteSearch(Function function, int maxParamsCount, int maxValue, int iterations) {
        super(maxParamsCount);
        this.function = function;
        this.maxValue = maxValue;
        this.iterations = iterations;

        random = new SecureRandom();
    }

    @Override
    protected double findSolution(int size) {
        double result = Double.MAX_VALUE;double[] params = new double[size];
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < size; j++) {
                params[j] = random.nextInt(maxValue + 1);
            }
            result = function.calculate(params);
        }
        return result;
    }
}
