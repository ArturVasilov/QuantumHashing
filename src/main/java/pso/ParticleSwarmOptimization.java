package pso;

import algorithms.IteratedQuantumHashingAlgorithm;
import functions.Function;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Artur Vasilov
 */
public class ParticleSwarmOptimization extends IteratedQuantumHashingAlgorithm {

    private final Function function;
    private final int maxValue;
    private final int particles;

    private final Random random;

    public ParticleSwarmOptimization(Function function, int maxParamsCount, int maxValue, int particles) {
        super(maxParamsCount);
        this.function = function;

        this.maxValue = maxValue;
        this.particles = particles;

        random = new SecureRandom();
    }

    @Override
    protected double findSolution(int size) {
        ParticleSwarm swarm = new ParticleSwarm(particles, size, maxValue, random, function);
        swarm.findGlobalBest();
        int iterations = 10;
        for (int i = 0; i < iterations; i++) {
            swarm.updateParameters();
        }
        return swarm.getBest().getCost();
    }
}
