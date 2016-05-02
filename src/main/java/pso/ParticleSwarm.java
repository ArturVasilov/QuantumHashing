package pso;

import functions.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Artur Vasilov
 */
class ParticleSwarm {

    private final Function function;

    private int maxValue;
    private int maxVelocity;

    private final List<Particle> particles;

    private Particle best;

    ParticleSwarm(int particlesCount, int size, int maxValue, Random random, Function function) {
        this.function = function;

        this.maxValue = maxValue;
        maxVelocity = Math.max(2, maxValue / (size * 2));
        particles = new ArrayList<>(particlesCount);
        for (int i = 0; i < particlesCount; i++) {
            particles.add(new Particle(size, maxValue, maxVelocity, random, function));
        }
    }

    void findGlobalBest() {
        double bestCost = particles.get(0).getCost();
        int bestIndex = 0;
        for (int i = 1; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            if (particle.getCost() < bestCost) {
                bestCost = particle.getCost();
                bestIndex = i;
            }
        }
        best = particles.get(bestIndex).deepCopy();
    }

    void updateParameters() {
        double c1 = 2.0;
        double c2 = 2.0;
        for (Particle particle : particles) {
            particle.updateVelocity(best, maxVelocity, c1, c2);
            particle.updatePosition(maxValue);
            particle.updateCost(function);
        }
        findGlobalBest();
    }

    Particle getBest() {
        return best;
    }
}
