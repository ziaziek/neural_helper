package com.pncomp.javaneural.services;

import org.neuroph.core.NeuralNetwork;

/**
 * Service to store neural networks in files
 */
public class NeuralNetworkFileService implements NetworkSaveReadService {
    /**
     * Saves neural network in a file
     * @param net neural network
     * @param name file name
     * @return 0, if successful
     */
    @Override
    public int saveNetwork(NeuralNetwork net, String name) {
        net.save(name);
        return 0;
    }

    /**
     * Read neural network from a file
     * @param networkName file name containing neural network definition
     * @return neural network
     */
    @Override
    public NeuralNetwork readNetwork(String networkName) {
        return NeuralNetwork.createFromFile(networkName);
    }
}
