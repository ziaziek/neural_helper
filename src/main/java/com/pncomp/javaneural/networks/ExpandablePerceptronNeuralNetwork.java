package com.pncomp.javaneural.networks;

import com.pncomp.javaneural.training.ExpansionStrategy;
import org.neuroph.core.Layer;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.NeuralNetworkEvent;
import org.neuroph.core.events.NeuralNetworkEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.ConnectionFactory;

import java.util.Date;

/**
 * Created by Przemo on 2017-10-18.
 * {@link MultiLayerPerceptron} that can be expanded.
 * It has a {@link NeuralNetworkEventListener} and {@link ExpansionStrategy} attached that will be called
 * to adjust the network structure while learning.
 */



public class ExpandablePerceptronNeuralNetwork extends MultiLayerPerceptron {

    public Date getStartLearningTime() {
        return startLearningTime;
    }

    private Date startLearningTime;

    /**
     * Creates the network.
     * @param inputNeuronsCount number of input neurons
     * @param outputNeuronsCount number of output neurons
     * @param expander strategy for the neural network expansion during the training process.
     */
    public ExpandablePerceptronNeuralNetwork(int inputNeuronsCount, int outputNeuronsCount, final ExpansionStrategy expander) {
        super(inputNeuronsCount, outputNeuronsCount);
        expander.attachNetwork(this);
        this.addListener(new NeuralNetworkEventListener() {

            @Override
            public void handleNeuralNetworkEvent(NeuralNetworkEvent neuralNetworkEvent) {
                if(neuralNetworkEvent.getEventType() == NeuralNetworkEvent.Type.CALCULATED){
                    expander.expandNetwork();
                }
            }
        });
    }

    @Override
    public void learn(DataSet trainingSet) {
        startLearningTime = new Date();
        super.learn(trainingSet);
    }

    /**
     *
     * @return number of all neurons in the network
     */
    public int getAllNeuronsCount(){
        int s = 0;
        for(Object layer: this.getLayers()){
            s+=((Layer)layer).getNeuronsCount();
        }
        return s;
    }

    /**
     * Fully reconnects the network.
     * First, all connections are removed.
     * Afterwards, all layers get fully connected.
     */
    public void reconnectFull(){
        for(Object layer: this.getLayers()){
            for(Neuron n : ((Layer)layer).getNeurons()){
                n.removeAllConnections();
            }
        }
        for(int i=1; i<this.getLayersCount(); i++){
            ConnectionFactory.fullConnect(this.getLayerAt(i-1), this.getLayerAt(i));
        }
    }

}
