package pso;

import functions.Function;

/**
 * @author Artur Vasilov
 */
public interface Algorithm {

    void calculate(Function function, int numbers);

    double bestValue();

    double[] bestPoint();

}
