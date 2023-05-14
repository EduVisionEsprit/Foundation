/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author job_j
 */
public class SqlConnectionManager {
    private static SqlConnectionManager instance;
    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(SqlConnectionManager.class.getName());
    private final Properties properties = loadProperties("app.settings");
    
    private SqlConnectionManager() {
        // Private constructor to prevent instantiation outside the class
    }

    public static synchronized SqlConnectionManager getInstance() {
        if (instance == null) {
            instance = new SqlConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                
                String url = properties.getProperty("connectionString");
                String username = properties.getProperty("username");
                String password = properties.getProperty("password");

                connection = DriverManager.getConnection(url, username, password);
                LOGGER.log(Level.INFO, "conencted to {0}", url);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error found while trying to connect to the database reason : {0}", e.getMessage());
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static Properties loadProperties(String filePath) {
    Properties properties = new Properties();
    try (FileInputStream fis = new FileInputStream(filePath)) {
        properties.load(fis);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return properties;
}
    
}
