package algorithms;

/**
 * @author Artur Vasilov
 */
public abstract class IteratedQuantumHashingAlgorithm implements Algorithm {

    private final int maxParamsCount;

    private long time;

    public IteratedQuantumHashingAlgorithm(int maxParamsCount) {
        this.maxParamsCount = maxParamsCount;
        time = 0;
    }

    @Override
    public final double solution() {
        double result = Double.MAX_VALUE;
        for (int size = 1; size <= maxParamsCount; size *= 2) {
            double temp = solutionForFixedSize(size);
            if (temp < result) {
                result = temp;
            }
        }
        return result;
    }

    @Override
    public final double solutionForFixedSize(int size) {
        long start = System.nanoTime();
        double result = findSolution(size);
        long end = System.nanoTime();
        time += (end - start) / 1_000_000;
        return result;
    }

    @Override
    public long runningTimeMs() {
        return time;
    }

    protected abstract double findSolution(int size);
}
