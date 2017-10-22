package com.pncomp.javaneural.testing;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.eval.ClassifierEvaluator;
import org.neuroph.eval.Evaluation;
import org.neuroph.eval.EvaluationResult;
import org.neuroph.eval.classification.ClassificationMetrics;

/**
 * Created by Przemo on 2017-10-18.
 * Class for a neural network testing
 */
public class NeuralNetworkTester {

    final NeuralNetwork network;
    final DataSet testSet;

    public double getAcceptableErrorRate() {
        return acceptableErrorRate;
    }

    final double acceptableErrorRate;

    public EvaluationResult getResult() {
        return result;
    }

    private EvaluationResult result;

    /**
     * Creates the tester
     * @param net network to test
     * @param testingSet testing data set
     * @param tolerableErrorRate if the error rate of the network is higher than this number, the testing is considered unsatisfactory
     */
    public NeuralNetworkTester(final NeuralNetwork net, DataSet testingSet, double tolerableErrorRate) {
        this.network = net;
        this.testSet = testingSet;
        this.acceptableErrorRate = tolerableErrorRate;
    }

    /**
     * Runs the tests
     * @return evaluation results {@link EvaluationResult}
     */
    public EvaluationResult test() {
        Evaluation eval = new Evaluation();
        eval.addEvaluator(new ClassifierEvaluator.Binary(0.5));
        this.result = eval.evaluateDataSet(network, testSet);
        return result;
    }

    /**
     *
     * @return true, id the training is satisfactory, i.e. when the error rate is below the tolerableErrorRate
     */
    public boolean isTrainingSatisfactory() {
        if (result == null) {
            test();
        }
        return result.getClassificationMetricses()[0].getErrorRate() < acceptableErrorRate;
    }

    /**
     * Prints out the testing results information.
     * @param evaluationResult
     */
    public void showEvaluationResults(EvaluationResult evaluationResult) {
        System.out.println("Network evaluation.");
        System.out.println("Testing data set size: " + String.valueOf(evaluationResult.getDataSet().getRows().size()));
        System.out.println("Mean squared error: " + String.valueOf(evaluationResult.getMeanSquareError()));
        ClassificationMetrics metrics = evaluationResult.getClassificationMetricses()[0];
        System.out.println("Network wrong predictions: " + String.valueOf(metrics.getErrorRate()));
        System.out.println("Network error rate: " + String.valueOf(metrics.getErrorRate()));
        System.out.println(evaluationResult.getConfusionMatrix());
    }
}
