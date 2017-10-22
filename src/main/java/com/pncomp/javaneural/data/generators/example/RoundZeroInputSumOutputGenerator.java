package com.pncomp.javaneural.data.generators.example;

import com.pncomp.javaneural.data.generators.DataProducerOutputGenerationStrategy;

/**
 * Strategy for generating output based on criteria of input sum and given radius.
 * When the sum of inputs is within the radius, 0 will be generated, 1 - otherwise.
 */
public class RoundZeroInputSumOutputGenerator implements DataProducerOutputGenerationStrategy {

    private final double r;

    public RoundZeroInputSumOutputGenerator(double radius) {
        r=radius;
    }

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
