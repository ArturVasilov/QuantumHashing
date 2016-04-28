package utils;

import java.util.Random;

/**
 * @author Artur Vasilov
 */
public final class OptimizationUtils {

    private OptimizationUtils() {
    }

    public static void perturbateParams(double[] params, double step, double maxValue, Random random) {
        for (int j = 0; j < params.length; j++) {
            if (params[j] + step > maxValue) {
                params[j] -= step * (random.nextBoolean() ? 1 : 0);
            } else if (params[j] - step < 0) {
                params[j] += step * (random.nextBoolean() ? 1 : 0);
            } else {
                params[j] += step * (random.nextInt(3) - 1);
            }
        }
    }

}
