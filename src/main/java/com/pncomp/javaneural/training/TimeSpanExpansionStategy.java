package com.pncomp.javaneural.training;

import org.neuroph.core.NeuralNetwork;

/**
 * Created by Przemo on 2017-10-18.
 * Strategy for neural network expansion based on time formula
 */
public interface TimeSpanExpansionStategy extends ExpansionStrategy {

    int calculateExpansionTime(NeuralNetwork net);

}
