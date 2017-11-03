package com.pncomp.javaneural.training;

import com.pncomp.javaneural.networks.ExpandablePerceptronNeuralNetwork;
import com.pncomp.javaneural.services.NetworkSaveReadService;
import com.pncomp.javaneural.testing.NeuralNetworkClassificatorTester;
import com.pncomp.javaneural.testing.NeuralNetworkTester;
import org.neuroph.core.data.DataSet;

public class ExpandablePerceptronTrainer extends NeuralNetworkTrainer {


    private int inp, outp;

    public ExpandablePerceptronTrainer(DataSet[] ds, NetworkSaveReadService readSaveService){
        super(ds, readSaveService);
    }

    public ExpandablePerceptronTrainer(String dataSetFilename, int inputs, int outputs, boolean includeColumnNames, String delimiter, NetworkSaveReadService readSaveService) {
        super(dataSetFilename, inputs, outputs, includeColumnNames, delimiter,  readSaveService);
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
