package com.pncomp.javaneural.data.generators;

import java.util.Random;

/**
 * Class to generate random inputs from 0 to 1
 */
public class RandomInputGenerationFunction implements DataGeneratorFunction {

    Random r = new Random();

    @Override
    public double[] generateInputs() {
        return new double[0];
    }

    /**
     * Generate a random number
     * @return value from 0 to 1
     */
    @Override
    public double generateInput() {
        return r.nextDouble();
    }
}
