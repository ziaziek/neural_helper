package com.pncomp.javaneural.training;

import com.pncomp.javaneural.networks.ExpandablePerceptronNeuralNetwork;

/**
 * Created by Przemo on 2017-10-18.
 */
public interface ExpansionStrategy {

    void attachNetwork(ExpandablePerceptronNeuralNetwork network);

    void expandNetwork();

}
