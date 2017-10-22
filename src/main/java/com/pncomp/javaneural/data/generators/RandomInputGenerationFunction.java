package com.pncomp.javaneural.data.generators;

import java.util.Random;

public class RandomInputGenerationFunction implements DataGeneratorFunction {

    Random r = new Random();

    @Override
    public double[] generateInputs() {
        return new double[0];
    }

    @Override
    public double generateInput() {
        return r.nextDouble();
    }
}
