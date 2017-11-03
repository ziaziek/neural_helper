package com.pncomp.javaneural.training;

import com.pncomp.javaneural.services.NetworkSaveReadService;
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

    public NeuralNetwork getNetwork() {
        return network;
    }

    protected void setNetwork(NeuralNetwork network) {
        this.network = network;
    }
    private final NetworkSaveReadService networkSaveReadService;

    private Logger log = LoggerFactory.getLogger(getClass());

    private NeuralNetwork network;
    /**
     * Cretes neural network trainer
     * @param dataSet Array of dataset consisting of training and testing data sets.
     * @param readService service responsible fopr saving and/or reading the network
     */
    public NeuralNetworkTrainer(DataSet[] dataSet, NetworkSaveReadService readService){
        this.dataSets = dataSet;
        this.networkSaveReadService = readService;
    }

    /**
     * Creates neural network trainer. The trainer divides the read data set into training and testing data set in 80:20 proportion.
     * @param dataSetFilename filename of the data set
     * @param inputs number of inputs of the neural network and data set
     * @param outputs number of outputs of the neural network and data set
     * @param includeColumnNames should the column names from the dataset file be included
     * @param delimiter delimiter of the data in the data set
     * @param readService service responsible fopr saving and/or reading the network
     */
    public NeuralNetworkTrainer(final String dataSetFilename, int inputs, int outputs, boolean includeColumnNames, String delimiter, NetworkSaveReadService readService) {
        log.info("Creating dataset from file "+ dataSetFilename);
        dataSets = DataSet.createFromFile(dataSetFilename, inputs, outputs, delimiter, includeColumnNames).createTrainingAndTestSubsets(80, 20);
        this.networkSaveReadService=readService;
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

    public void trainTestAndSave(String name){
        trainNetwork();
        testNetwork(name);
    }

    /**
     * Tests the trained network based on the calculated training set.
     * Based on the testing results, the network is saved to a file or a message of unsuccessful training is thrown.
     */
    public void testNetwork(String name){
        log.warn("Teseting network.");
        if(network!=null){
            NeuralNetworkTester tester = useTester();
            if(tester.isTrainingSatisfactory()){
                networkSaveReadService.saveNetwork(network, name);
                log.info("Training successful. Network saved at "+name);
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
