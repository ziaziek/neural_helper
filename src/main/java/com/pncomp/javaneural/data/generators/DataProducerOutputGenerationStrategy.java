package com.pncomp.javaneural.data.generators;

/**
 * Strategy for generating the output for the neural network when training data set is built.
 */
public interface DataProducerOutputGenerationStrategy {

    /**
     * Generates the output array for the newly built data set.
     * The output is based on the imputs.
     * @param inputs
     * @return
     */
    double[] generateOutput(double[] inputs);

}
