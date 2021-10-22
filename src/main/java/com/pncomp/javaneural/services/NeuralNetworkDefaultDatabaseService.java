package com.pncomp.javaneural.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.neuroph.core.NeuralNetwork;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * Service class to save neural network into database
 * By default it uses postgresql database
 */
public class NeuralNetworkDefaultDatabaseService implements NetworkSaveReadService {

    final static String defaultFileName = "default_nn";

    private Logger log = LogManager.getLogger(getClass());


    private Connection createConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection("jdbc:postgresql://pncomp.com:5432/nn", "client", "derek");
    }

    /**
     * Store neural network in database
     * @param net neural network to save
     * @param name name of the network
     * @return 0 if process worked fine
     */
    @Override
    public int saveNetwork(NeuralNetwork net, String name) {
        Connection conn = null;
        try {
            conn=createConnection();
            conn.setAutoCommit(false);
            net.save(defaultFileName);
            log.info("File saved.");
            final String sqlInsertNN = "insert into neural_network(name, date_created, nn) values(?,current_timestamp,?)";
            try(PreparedStatement stmt = conn.prepareStatement(sqlInsertNN)){
                FileInputStream fis = new FileInputStream(new File(defaultFileName));
                stmt.setString(1, name);
                stmt.setBinaryStream(2, fis);
                //stmt.setObject(2, net);
                stmt.executeUpdate();
                log.info("Neural network saved in database.");
                conn.commit();
                fis.close();
            } catch(SQLException ex){
                log.warn(ex);
            } catch (IOException e) {
                log.warn("Could not read");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                Files.delete(Paths.get(defaultFileName));
            } catch (IOException e) {
                log.warn("File "+defaultFileName+" could not be deleted.");
            }
        }
        return 0;
    }

    /**
     * Reads neural network from database
     * @param networkName network name
     * @return neural network or null, if not found
     */
    @Override
    public NeuralNetwork readNetwork(String networkName) {
        Connection conn = null;
        try {
            conn = createConnection();
            final String readSql = "select nn from neural_network where name =?";
            try(PreparedStatement stmt = conn.prepareStatement(readSql)){
                stmt.setString(1, networkName);
                try(ResultSet rs = stmt.executeQuery()){
                    log.info("Neural network retrieved from database.");
                    while(rs.next()){
                        log.info("Trying to load network.");
                        byte[] b = rs.getBytes("nn");
                        log.info("Network byte length: "+String.valueOf(b.length));
                        try (ByteArrayInputStream str = new ByteArrayInputStream(b)) {
                            NeuralNetwork net = NeuralNetwork.load(str);
                            log.info("Neural network read. Number of weights:"+String.valueOf(net.getWeights().length));
                            str.close();
                            log.info("Stream closed. Network has "+String.valueOf(net.getInputsCount()+" inputs and "+String.valueOf(net.getOutputsCount())+" outputs."));
                            return net;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
