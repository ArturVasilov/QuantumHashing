package algorithms;

/**
 * @author Artur Vasilov
 */
public interface Algorithm {

    double solution();

    double solutionForFixedSize(int size);

    long runningTimeMs();

}
