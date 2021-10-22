package com.pncomp.javaneural.data.generators;

/**
 * This class is a function to produce linear defined input data
 */
public class LinearInputGenerationFunction implements DataGeneratorFunction {

    /**
     * Sets current point which is used to generate input value
     * @param currentX current point
     */
    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    private int currentX;

    private double min, step;

    /**
     * Class constructor
     * @param minValue minimum value of inputs
     * @param maxValue maximum value of inputs
     * @param nsamples number of samples to produce
     */
    public LinearInputGenerationFunction(double minValue, double maxValue, double nsamples) {
        this.min=minValue;
        this.step = (maxValue-minValue)/nsamples;
    }

    @Override
    public double[] generateInputs() {
        return new double[0];
    }

    /**
     * Generates input value based on current point. The current point gets increased.
     * @return input value
     */
    @Override
    public double generateInput() {
        currentX++;
        return min+currentX*step;
    }
}
