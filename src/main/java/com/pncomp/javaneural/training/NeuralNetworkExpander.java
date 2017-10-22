package com.pncomp.javaneural.training;

import com.pncomp.javaneural.networks.ExpandablePerceptronNeuralNetwork;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Przemo on 2017-10-15.
 * Class for adjusting neural network structure while training.
 * Implements {@link TimeSpanExpansionStategy}
 */
public class NeuralNetworkExpander implements TimeSpanExpansionStategy {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ExpandablePerceptronNeuralNetwork network;
    private final int expansionTimeMultiplier = 5000;
    private final DataSet ds;

    public TransferFunctionType getTransfer() {
        return transfer;
    }

    public void setTransfer(TransferFunctionType transfer) {
        this.transfer = transfer;
    }

    private TransferFunctionType transfer = TransferFunctionType.SIGMOID;
    int currentTriggeringLayerCount = 2;
    int maxNeuronsCountForLayer = 0;

    /**
     * Construct NeuralNetwork expander
     * @param ds DataSet to train the network
     */
    public NeuralNetworkExpander(final DataSet ds){
        this.ds=ds;

    }

    @Override
    public void attachNetwork(ExpandablePerceptronNeuralNetwork network) {
        this.network = network;
        this.maxNeuronsCountForLayer = network.getInputsCount()*network.getInputsCount()*(network.getOutputsCount()+1);
    }

    /**
     * Expands the network
     */
    public void expandNetwork(){
        if(network!=null && (new Date().getTime() - network.getStartLearningTime().getTime()) > calculateExpansionTime(network)){

            SupervisedLearning learningRule = network.getLearningRule();
            log.info("----Extending intra layers.----");
            log.info("Total network error: "+String.valueOf(learningRule.getTotalNetworkError()));
            log.info("Allowed max error: "+String.valueOf(learningRule.getMaxError()));

            network.stopLearning();
            doExpansion();

            log.info("All neurons in the network: "+network.getAllNeuronsCount());

            log.info("Resumed learning.");
            network.learn(ds);

        }
    }

    private void doExpansion() {
        network.reset();
        int layerIndex = currentTriggeringLayerCount-1;
        if(network.getLayers().size()<=currentTriggeringLayerCount){
            log.info("Adding a new layer.");
            network.addLayer(layerIndex, LayerFactory.createLayer( 2, transfer));
        } else {
            addNeuronToManagedLayer(layerIndex);
        }
        network.reconnectFull();
        network.randomizeWeights();
    }

    private void addNeuronToManagedLayer(int layerIndex) {
        log.info("Adding a neuron to layer "+ String.valueOf(layerIndex));
        Layer layer = network.getLayers().get(layerIndex);
        layer.addNeuron(NeuronFactory.createNeuron(new NeuronProperties(transfer, true)));
        log.info("Neurons in the layer: "+ String.valueOf(layer.getNeuronsCount()));
        if(layer.getNeuronsCount()>=maxNeuronsCountForLayer){
            log.info("Maximum allowed neurons exceeded ("+String.valueOf(maxNeuronsCountForLayer)+"). New layer will be added next time.");
            currentTriggeringLayerCount++;
        }
    }

    @Override
    public int calculateExpansionTime(NeuralNetwork net){
        return (int)Math.sqrt(network.getAllNeuronsCount()+1)*expansionTimeMultiplier;
    }

}
