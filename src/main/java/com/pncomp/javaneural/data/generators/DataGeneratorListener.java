package com.pncomp.javaneural.data.generators;

/**
 * Listens to events of input and/or output generation.
 */
public interface DataGeneratorListener {

    /**
     * Called when an input number is generated
     * @param inputs
     */
    void inputGenerated(double inputs);

    /**
     * Called when putput array is generated.
     * @param output
     */
    void outputsGenerated(double[] output);

}
