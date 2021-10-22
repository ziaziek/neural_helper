package com.pncomp.javaneural.training;

import com.pncomp.javaneural.networks.ExpandablePerceptronNeuralNetwork;
import com.pncomp.javaneural.services.NetworkSaveReadService;
import com.pncomp.javaneural.testing.NeuralNetworkClassificatorTester;
import com.pncomp.javaneural.testing.NeuralNetworkTester;
import org.neuroph.core.data.DataSet;

/**
 * Class to train perceptron neural networks.
 * This class tries to achieve successful training results by expanding neural networks as the training process goes.
 */
public class ExpandablePerceptronTrainer extends NeuralNetworkTrainer {


    private int inputs, outputs;

    /**
     * Constructor
     * @param ds data set to use for training
     * @param readSaveService service to read and write neural network
     */
    public ExpandablePerceptronTrainer(DataSet[] ds, NetworkSaveReadService readSaveService){
        super(ds, readSaveService);
    }

    /**
     * Constructor
     * @param dataSetFilename file name containing data set to train on
     * @param inputs number of inputs
     * @param outputs number of outputs
     * @param includeColumnNames indicates if data set contains column names
     * @param delimiter delimiter used in the loaded data set
     * @param readSaveService service to read and write neural network
     */
    public ExpandablePerceptronTrainer(String dataSetFilename, int inputs, int outputs, boolean includeColumnNames, String delimiter, NetworkSaveReadService readSaveService) {
        super(dataSetFilename, inputs, outputs, includeColumnNames, delimiter,  readSaveService);
        this.inputs =inputs;
        this.outputs = outputs;
    }


    /**
     * trains neural network with dataset devided into two sub-sets: training and testing in 80:20 percent proportion.
     */
    @Override
    public void trainNetwork() {
        setNetwork(new ExpandablePerceptronNeuralNetwork(inputs, outputs, new NeuralNetworkExpander(dataSets[0])));
        getNetwork().randomizeWeights();
        getNetwork().learn(dataSets[0]);
    }

    /**
     * Builds a classification tester. Set neural network and testing data set
     * @return tester
     */
    @Override
    public NeuralNetworkTester build() {
        NeuralNetworkTester tester = new NeuralNetworkClassificatorTester( 0.05);
        tester.setTestSet(dataSets[1]);
        tester.setNetwork(getNetwork());
        return tester;
    }
}
