package com.pncomp.javaneural.services;

import org.neuroph.core.NeuralNetwork;

public class NeuralNetworkFileService implements NetworkSaveReadService {
    @Override
    public int saveNetwork(NeuralNetwork net, String name) {
        net.save(name);
        return 0;
    }

    @Override
    public NeuralNetwork readNetwork(String networkName) {
        return NeuralNetwork.createFromFile(networkName);
    }
}
