package algorithms.stochastic;

import functions.Function;

/**
 * @author Artur Vasilov
 */
public class MultiRestartAdaptiveRandomSearch extends AdaptiveRandomSearch {

    private final int restartsCount;

    public MultiRestartAdaptiveRandomSearch(Function function, int maxParamsCount, int maxValue, double stepFactor,
                                            int restartsCount) {
        super(function, maxParamsCount, maxValue, stepFactor);
        this.restartsCount = restartsCount;
    }

    @Override
    protected double findSolution(int size) {
        double result = Double.MAX_VALUE;
        for (int i = 0; i < restartsCount; i++) {
            result = super.findSolution(size);
        }
        return result;
    }
}
