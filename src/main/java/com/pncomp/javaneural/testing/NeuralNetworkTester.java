package com.pncomp.javaneural.testing;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.eval.EvaluationResult;

public abstract class NeuralNetworkTester {

    public double getAcceptableErrorRate() {
        return acceptableErrorRate;
    }

    final double acceptableErrorRate;

    public EvaluationResult getResult() {
        return result;
    }

    protected void setResult(EvaluationResult result) {
        this.result = result;
    }

    private EvaluationResult result;

    public NeuralNetwork getNetwork() {
        return network;
    }

    public void setNetwork(NeuralNetwork network) {
        this.network = network;
    }

    public DataSet getTestSet() {
        return testSet;
    }

    public void setTestSet(DataSet testSet) {
        this.testSet = testSet;
    }

    NeuralNetwork network;
    DataSet testSet;

    public NeuralNetworkTester(double accepterError){
        this.acceptableErrorRate=accepterError;
    }

    public abstract boolean isTrainingSatisfactory();
}
