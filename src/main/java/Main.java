import algorithms.Algorithm;
import algorithms.BruteForce;
import algorithms.RandomBruteSearch;
import functions.Function;
import functions.StandardQuantumHashFunction;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Artur Vasilov
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please, enter your input bits count:");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        System.out.println("Select algorithm:");
        System.out.println("Brute force - 1");
        System.out.println("Random brute search - 2");
        int algorithmCode = scanner.nextInt();

        int q = (int) Math.pow(2.0, 1.0 * n);
        Function function = new StandardQuantumHashFunction(q);

        Algorithm algorithm;
        switch (algorithmCode) {
            case 2: {
                System.out.println("Please, enter number of random search iterations");
                int iterations = scanner.nextInt();
                algorithm = new RandomBruteSearch(function, iterations, n, q);
                break;
            }

            case 1:
            default:
                algorithm = new BruteForce(function, n, q);
        }

        System.out.println("Please, enter qubits count. Enter 0, if you want dynamic selection.");
        int qubits = scanner.nextInt();
        System.out.println("Result - " + (qubits == 0 ? algorithm.solution() : algorithm.solutionForFixedSize(qubits)));
        System.out.println("Running time - " + algorithm.runningTimeMs());
        System.out.println("Best params - " + Arrays.toString(function.bestParams()));
    }

}
