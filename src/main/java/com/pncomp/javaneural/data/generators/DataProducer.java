package com.pncomp.javaneural.data.generators;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import java.util.Random;

/**
 * Produces random data for a neural network
 */
public class DataProducer {

    /**
     * Generates a random dataset for a neural network training.
     * @param inputs number of inputs
     * @param outputs number of ouputs
     * @param rowCount number of rows in the dataset (samples).
     * @param outputStrategy strategy of defining the output value
     * @param dgListener listener for data generation. See {@link DataGeneratorListener}
     * @return
     */
    public static DataSet generateRandomDataSet(int inputs, int outputs, int rowCount, final DataProducerOutputGenerationStrategy outputStrategy, final DataGeneratorListener dgListener){
        return  generateDataSet(inputs, outputs, rowCount, new RandomInputGenerationFunction(), outputStrategy, dgListener);
    }

    public static DataSet generateDataSet(int inputs, int outputs, int rowCount, final DataGeneratorFunction fx, final DataProducerOutputGenerationStrategy outputStrategy, final DataGeneratorListener dgListener){
        DataSet ds = new DataSet(inputs, outputs);
        for (int j = 0; j < rowCount; j++) {
            DataSetRow row = new DataSetRow();
            double[] inp = new double[inputs];
            for (int i = 0; i < (inputs); i++) {
                inp[i]=fx.generateInput();
                if(dgListener!=null){
                    dgListener.inputGenerated(inp[i]);
                }
            }
            row.setInput(inp);
            double[] outp = outputStrategy.generateOutput(inp);
            if(dgListener!=null){
                dgListener.outputsGenerated(outp);
            }
            row.setDesiredOutput(outp);
            ds.add(row);
        }
        return  ds;
    }

}
