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

    /**
     * Generate a dataset of data values defined by DataGeneratorFunction and DataProducerOutputGenerationStrategy
     * @param inputs number of inputs
     * @param outputs number of outputs
     * @param rowCount number of rows to generate
     * @param fx class producing input data
     * @param outputStrategy class producing output data
     * @param dgListener listener to the process, fired after every input is generated, and after every output is generated
     * @return data set
     */
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
            double[] output = outputStrategy.generateOutput(inp);
            if(dgListener!=null){
                dgListener.outputsGenerated(output);
            }
            row.setDesiredOutput(output);
            ds.add(row);
        }
        return  ds;
    }

}
