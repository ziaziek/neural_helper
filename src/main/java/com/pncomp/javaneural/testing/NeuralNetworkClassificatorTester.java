package com.pncomp.javaneural.testing;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.error.ErrorFunction;
import org.neuroph.core.learning.error.MeanSquaredError;
import org.neuroph.eval.ClassifierEvaluator;
import org.neuroph.eval.ErrorEvaluator;
import org.neuroph.eval.Evaluation;
import org.neuroph.eval.EvaluationResult;
import org.neuroph.eval.classification.ClassificationMetrics;

/**
 * Created by Przemo on 2017-10-18.
 * Class for a neural network testing
 */
public class NeuralNetworkClassificatorTester extends NeuralNetworkTester {

    /**
     * Creates the tester
     * @param tolerableErrorRate if the error rate of the network is higher than this number, the testing is considered unsatisfactory
     */
    public NeuralNetworkClassificatorTester(double tolerableErrorRate) {
        super(tolerableErrorRate);
    }

    /**
     * Runs the tests
     * @return evaluation results {@link EvaluationResult}
     */
    public EvaluationResult testForClassification() {
        Evaluation eval = new Evaluation();
        eval.addEvaluator(new ClassifierEvaluator.Binary(0.5));
        setResult(eval.evaluateDataSet(network, testSet));
        return getResult();
    }

    /**
     *
     * @return true, id the training is satisfactory, i.e. when the error rate is below the tolerableErrorRate
     */
    public boolean isTrainingSatisfactory() {
        if (getResult() == null) {
            testForClassification();
        }
        return getResult().getClassificationMetricses()[0].getErrorRate() < acceptableErrorRate;
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
