package com.pncomp.javaneural.data.generators.example;

import com.pncomp.javaneural.data.generators.DataProducerOutputGenerationStrategy;

/**
 * Strategy for generating output based on criteria of input sum and given radius.
 * When the sum of inputs is within the radius, 0 will be generated, 1 - otherwise.
 */
public class RoundZeroInputSumOutputGenerator implements DataProducerOutputGenerationStrategy {

    private final double r;

    /**
     * Construct the class with given radius for 0,1 output generation
     * @param radius
     */
    public RoundZeroInputSumOutputGenerator(double radius) {
        r=radius;
    }

    /**
     * Generates output vector of 0's and 1's
     * @param inputs input vector
     * @return vector of 0's and 1's, based on the simple sum of inputs. If the sum is within the radius +-,
     * a single value vector of 0 is generated, otherwise - a single value vector of 1
     */
    @Override
    public double[] generateOutput(double[] inputs) {
        double s=0;
        for(int i=0; i<inputs.length; i++){
            s+=inputs[i];
        }
        if(s<-r || s>r){
            return new double[]{0};
        } else {
            return new double[]{1};
        }
    }
}
