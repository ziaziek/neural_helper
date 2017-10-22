package com.pncomp.javaneural.data.generators;

public class LinearInputGenerationFunction implements DataGeneratorFunction {

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    private int currentX;

    private double min, step;
    public LinearInputGenerationFunction(double minValue, double maxValue, double nsamples) {
        this.min=minValue;
        this.step = (maxValue-minValue)/nsamples;
    }

    @Override
    public double[] generateInputs() {
        return new double[0];
    }

    @Override
    public double generateInput() {
        currentX++;
        return min+currentX*step;
    }
}
