package pso;

import functions.Function;

import java.util.Random;

/**
 * @author Artur Vasilov
 */
class Particle {

    private Random random;

    private double[] position;
    private double[] bestPosition;

    private double cost;
    private double bestCost;

    private double[] velocity;

    Particle(int size, int maxValue, int maxVelocity, Random random, Function function) {
        this.random = random;

        position = randomVector(size, maxValue, random);
        bestPosition = new double[size];
        System.arraycopy(position, 0, bestPosition, 0, size);

        cost = function.calculate(position);
        bestCost = cost;

        velocity = randomVector(size, maxVelocity, random);
    }

    private Particle() {
    }

    double getCost() {
        return cost;
    }

    Particle deepCopy() {
        Particle particle = new Particle();
        particle.random = random;
        particle.position = new double[position.length];
        System.arraycopy(position, 0, particle.position, 0, position.length);

        particle.bestPosition = new double[bestPosition.length];
        System.arraycopy(bestPosition, 0, particle.bestPosition, 0, bestPosition.length);

        particle.cost = cost;
        particle.bestCost = cost;

        particle.velocity = new double[velocity.length];
        System.arraycopy(velocity, 0, particle.velocity, 0, velocity.length);
        return particle;
    }

    private double[] randomVector(int size, int maxValue, Random random) {
        double[] vector = new double[size];
        for (int i = 0; i < size; i++) {
            vector[i] = random.nextInt(maxValue + 1);
        }
        return vector;
    }

    void updateVelocity(Particle best, int maxVelocity, double c1, double c2) {
        int size = velocity.length;
        for (int i = 0; i < size; i++) {
            double v1 = c1 * random.nextDouble() * (bestPosition[i] - position[i]);
            double v2 = c2 * random.nextDouble() * (best.position[i] - position[i]);
            velocity[i] += v1 + v2;
            if (velocity[i] > maxVelocity) {
                velocity[i] = maxVelocity;
            }
            if (velocity[i] < -maxVelocity) {
                velocity[i] = -maxVelocity;
            }
        }
    }

    void updatePosition(int maxValue) {
        int size = position.length;
        for (int i = 0; i < size; i++) {
            position[i] += Math.round(velocity[i]);
            if (position[i] > maxValue) {
                position[i] = maxValue - Math.abs(position[i] - maxValue);
                velocity[i] *= -1;
            }
            else if (position[i] < 0) {
                position[i] = 0;
                velocity[i] *= -1;
            }
            position[i] = (int) position[i];
        }
    }

    void updateCost(Function function) {
        cost = function.calculate(position);
        if (cost < bestCost) {
            bestCost = cost;
            System.arraycopy(position, 0, bestPosition, 0, position.length);
        }
    }
}
