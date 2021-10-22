package com.pncomp.javaneural.testing;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.error.MeanSquaredError;
import org.neuroph.eval.ErrorEvaluator;
import org.neuroph.eval.EvaluationResult;

/**
 * Class to test neural networks. This class is a type of {@link NeuralNetworkClassificatorTester}
 */
public class NeuralNetworkErrorTester extends NeuralNetworkClassificatorTester {
    /**
     * Creates the tester
     *
     * @param tolerableErrorRate if the error rate of the network is higher than this number, the testing is considered unsatisfactory
     */
    public NeuralNetworkErrorTester(double tolerableErrorRate) {
        super(tolerableErrorRate);
    }

    /**
     * Calculates the error of the network using MeanSquaredError method.
     * @return total error of the network on the test data set
     */
    public double testForError(){
        ErrorEvaluator errorEvaluator = new ErrorEvaluator(new MeanSquaredError());
        for(DataSetRow dsr : testSet.getRows()){
            network.setInput(dsr.getInput());
            network.calculate();
            errorEvaluator.processNetworkResult(network.getOutput(), dsr.getDesiredOutput());
            setResult(new EvaluationResult());
            getResult().setMeanSquareError(errorEvaluator.getResult());
        }
        return errorEvaluator.getResult().doubleValue();
    }

    /**
     * Indicates if training was successful or not
     * @return true, if the mean square error rate of the test results is less than pre-defined acceptance error rate, provided in the constructor
     */
    @Override
    public boolean isTrainingSatisfactory() {
        if(getResult()==null){
            testForError();
        }
        return getResult().getMeanSquareError()<acceptableErrorRate;
    }


}
