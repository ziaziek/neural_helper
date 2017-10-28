package com.pncomp.javaneural.training;

import com.pncomp.javaneural.networks.ExpandablePerceptronNeuralNetwork;
import com.pncomp.javaneural.testing.NeuralNetworkClassificatorTester;
import com.pncomp.javaneural.testing.NeuralNetworkTester;
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


    @Override
    public void trainNetwork() {
        setNetwork(new ExpandablePerceptronNeuralNetwork(inp, outp, new NeuralNetworkExpander(dataSets[0])));
        getNetwork().randomizeWeights();
        getNetwork().learn(dataSets[0]);
    }

    @Override
    public NeuralNetworkTester buildTester() {
        NeuralNetworkTester tester = new NeuralNetworkClassificatorTester( 0.05);
        tester.setTestSet(dataSets[1]);
        tester.setNetwork(getNetwork());
        return tester;
    }
}
