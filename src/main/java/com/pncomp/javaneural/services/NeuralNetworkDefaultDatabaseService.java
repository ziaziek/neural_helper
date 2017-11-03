package com.pncomp.javaneural.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.neuroph.core.NeuralNetwork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NeuralNetworkDefaultDatabaseService implements NetworkSaveReadService {

    final static String defaultFileName = "default_nn";

    private Logger log = LogManager.getLogger(getClass());


    private Connection createConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection("jdbc:postgresql://pncomp.com:5432/nn", "client", "derek");
    }

    @Override
    public int saveNetwork(NeuralNetwork net, String name) {
        Connection conn = null;
        try {
            conn=createConnection();
            net.save(defaultFileName);
            log.info("File saved.");
            final String sqlInsertNN = "insert into neural_network(name, date_created, nn) values(?,current_timestamp,?)";
            try(PreparedStatement stmt = conn.prepareStatement(sqlInsertNN)){
                stmt.setString(1, name);
                stmt.setBytes(2, net.toString().getBytes());
                //stmt.setObject(2, net);
                stmt.execute();
                log.info("Neural network saved in database.");
            } catch(SQLException ex){
                log.warn(ex);
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

    @Override
    public NeuralNetwork readNetwork(String networkName) {
        //first read data from database into a file

        //build network from the file

        //delete the file
        return null;
    }
}
