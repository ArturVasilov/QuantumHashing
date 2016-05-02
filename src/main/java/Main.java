import algorithms.Algorithm;
import algorithms.BruteForce;
import algorithms.stochastic.AdaptiveRandomSearch;
import algorithms.stochastic.MultiRestartAdaptiveRandomSearch;
import algorithms.stochastic.RandomBruteSearch;
import algorithms.stochastic.RandomSearch;
import functions.Function;
import functions.StandardQuantumHashFunction;
import pso.ParticleSwarmOptimization;

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
        System.out.println("Random search - 3");
        System.out.println("Adaptive random search - 4");
        System.out.println("Multi-restart adaptive random search - 5");
        System.out.println("Particle swarm optimization - 6");
        int algorithmCode = scanner.nextInt();

        int q = (int) Math.pow(2.0, 1.0 * n);
        Function function = new StandardQuantumHashFunction(q);

        Algorithm algorithm;
        switch (algorithmCode) {
            case 2: {
                System.out.println("Please, enter number of stochastic search iterations:");
                int iterations = scanner.nextInt();
                algorithm = new RandomBruteSearch(function, n, q, iterations);
                break;
            }

            case 3: {
                System.out.println("Please, enter step size:");
                int stepSize = scanner.nextInt();
                algorithm = new RandomSearch(function, n, q, stepSize);
                break;
            }

            case 4:
                System.out.println("Please, enter step factor:");
                double stepFactor = scanner.nextDouble();
                algorithm = new AdaptiveRandomSearch(function, n, q, stepFactor);
                break;

            case 5:
                System.out.println("Please, enter step factor:");
                double restartStepFactor = scanner.nextDouble();
                System.out.println("Please, enter restarts count:");
                int restartsCount = scanner.nextInt();
                algorithm = new MultiRestartAdaptiveRandomSearch(function, n, q, restartStepFactor, restartsCount);
                break;

            case 6:
                ((StandardQuantumHashFunction) function).setOptimizeCalculations(false);
                System.out.println("Please, enter particles count:");
                int particles = scanner.nextInt();
                algorithm = new ParticleSwarmOptimization(function, n, q, particles);
                break;

            case 1:
            default:
                algorithm = new BruteForce(function, n, q);
        }

        System.out.println("Please, enter qubits count. Enter 0, if you want dynamic selection.");
        int qubits = scanner.nextInt();
        System.out.println("Result - " + (qubits == 0 ? algorithm.solution() : algorithm.solutionForFixedSize(qubits)));
        System.out.println("Running time - " + algorithm.runningTimeMs());

        double[] best = function.bestParams();
        int[] bestParams = new int[best.length];
        for (int i = 0; i < best.length; i++) {
            bestParams[i] = (int) best[i];
        }
        System.out.println("Best params - " + Arrays.toString(bestParams));
    }

}
