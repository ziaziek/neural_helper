package com.pncomp.javaneural.training;

import com.pncomp.javaneural.networks.ExpandablePerceptronNeuralNetwork;
import org.neuroph.core.data.DataSet;

public class ExpandablePerceptronTrainer extends NeuralNetworkTrainer {


    private int inp, outp;

    public ExpandablePerceptronTrainer(DataSet[] ds, final String fileName){
        super(ds, fileName);
    }

    public ExpandablePerceptronTrainer(String dataSetFilename, int inputs, int outputs, boolean includeColumnNames, String delimiter, String saveNNFileName) {
        super(dataSetFilename, inputs, outputs, includeColumnNames, delimiter, saveNNFileName);
        this.inp=inputs;
        this.outp = outputs;
    }

    public void trainTestAndSave(){
        trainNetwork();
        testNetwork();
    }

    @Override
    public void trainNetwork() {
        setNetwork(new ExpandablePerceptronNeuralNetwork(inp, outp, new NeuralNetworkExpander(dataSets[0])));
        getNetwork().randomizeWeights();
        getNetwork().learn(dataSets[0]);
    }
}
