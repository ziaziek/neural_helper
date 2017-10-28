package com.pncomp.javaneural.training;

import com.pncomp.javaneural.testing.NeuralNetworkClassificatorTester;
import com.pncomp.javaneural.testing.NeuralNetworkTester;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Trains neural networks based on a given dataset array or data set filename.
 */
public abstract class NeuralNetworkTrainer {

    public DataSet[] getDataSets() {
        return dataSets;
    }

    protected DataSet[] dataSets;
    private final String saveNNFileName;

    public NeuralNetwork getNetwork() {
        return network;
    }

    protected void setNetwork(NeuralNetwork network) {
        this.network = network;
    }

    private Logger log = LoggerFactory.getLogger(getClass());

    private NeuralNetwork network;
    /**
     * Cretes neural network trainer
     * @param dataSet Array of dataset consisting of training and testing data sets.
     * @param fileNAme filename for  the neural network to be saved to
     */
    public NeuralNetworkTrainer(DataSet[] dataSet, final String fileNAme){
        this.dataSets = dataSet;
        this.saveNNFileName=fileNAme;
    }

    /**
     * Creates neural network trainer. The trainer divides the read data set into training and testing data set in 80:20 proportion.
     * @param dataSetFilename filename of the data set
     * @param inputs number of inputs of the neural network and data set
     * @param outputs number of outputs of the neural network and data set
     * @param includeColumnNames should the column names from the dataset file be included
     * @param delimiter delimiter of the data in the data set
     * @param saveNNFileName filename for  the neural network to be saved to
     */
    public NeuralNetworkTrainer(final String dataSetFilename, int inputs, int outputs, boolean includeColumnNames, String delimiter, final String saveNNFileName) {
        this.saveNNFileName = saveNNFileName;
        log.info("Creating dataset from file "+ dataSetFilename);
        dataSets = DataSet.createFromFile(dataSetFilename, inputs, outputs, delimiter, includeColumnNames).createTrainingAndTestSubsets(80, 20);
    }

    /**
     * Trains the network
     */
    public abstract void trainNetwork();

    public abstract NeuralNetworkTester buildTester();

    private NeuralNetworkTester useTester(){
        NeuralNetworkTester tester = buildTester();
        tester.setNetwork(network);
        tester.setTestSet(dataSets[1]);
        return tester;
    }

    public void trainTestAndSave(){
        trainNetwork();
        testNetwork();
    }

    /**
     * Tests the trained network based on the calculated training set.
     * Based on the testing results, the network is saved to a file or a message of unsuccessful training is thrown.
     */
    public void testNetwork(){
        log.warn("Teseting network.");
        if(network!=null){
            NeuralNetworkTester tester = useTester();
            if(tester.isTrainingSatisfactory()){
                network.save(saveNNFileName);
                log.info("Training successful. Network saved at "+saveNNFileName);
            } else {
                log.warn("Training did not succeed. Error rate "+tester.getResult().getClassificationMetricses()[0].getErrorRate()+", while acceptable rate is "+tester.getAcceptableErrorRate());
            }
            if(tester.getResult().getConfusionMatrix()!=null)
                log.info(tester.getResult().getConfusionMatrix().toString());
        } else {
            log.warn("Network not ser.");
        }
    }
}
