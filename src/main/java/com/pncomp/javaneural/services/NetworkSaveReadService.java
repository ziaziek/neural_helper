package com.pncomp.javaneural.services;

import org.neuroph.core.NeuralNetwork;

public interface NetworkSaveReadService {

    int saveNetwork(NeuralNetwork net, String name);

    NeuralNetwork readNetwork(String networkName);

}
