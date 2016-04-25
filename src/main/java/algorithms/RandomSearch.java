package algorithms;

import functions.Function;

/**
 * @author Artur Vasilov
 */
public class RandomSearch extends IteratedQuantumHashingAlgorithm {

    private final Function function;
    private final int maxValue;

    public RandomSearch(Function function, int maxParamsCount, int maxValue) {
        super(maxParamsCount);
        this.function = function;
        this.maxValue = maxValue;
    }

    @Override
    protected double findSolution(int size) {
        double result = Double.MAX_VALUE;
        //TODO
        return result;
    }
}
