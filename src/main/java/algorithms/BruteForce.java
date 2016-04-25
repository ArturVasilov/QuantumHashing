package algorithms;

import functions.Function;

/**
 * @author Artur Vasilov
 */
public class BruteForce extends IteratedQuantumHashingAlgorithm {

    private final Function function;
    private final int maxValue;

    public BruteForce(Function function, int maxParamsCount, int maxValue) {
        super(maxParamsCount);
        this.function = function;
        this.maxValue = maxValue;
    }

    @Override
    protected double findSolution(int size) {
        double result;
        double[] params = new double[size];
        for (int i = 0; i < size; i++) {
            params[i] = 0;
        }
        int currentIndex = size - 1;
        boolean flag = false;
        do {
            result = function.calculate(params);
            while (params[currentIndex] >= maxValue && currentIndex > 0) {
                flag = true;
                for (int i = currentIndex; i < size; i++) {
                    params[currentIndex] = 0;
                }
                currentIndex--;
                params[currentIndex]++;
            }
            if (flag) {
                currentIndex = size - 1;
                flag = false;
            }
            else {
                params[currentIndex]++;
            }
        } while (params[0] <= maxValue);
        return result;
    }
}
