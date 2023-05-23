/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.tools;

import java.io.File;
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
    private static Connection connection;
    private static final Logger LOGGER = CustomLogger.getInstance().getLogger();
    private final Properties properties = loadProperties("app.settings");
    
    private SqlConnectionManager() {
        if (connection == null) {
            try {
                
                String url = properties.getProperty("connectionString");
                String username = properties.getProperty("username");
                String password = properties.getProperty("password");

                connection = DriverManager.getConnection(url, username, password);
                LOGGER.log(Level.INFO, "cpnnected to database you can find details in the app.settings");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error found while trying to connect to the database reason : {0}", e.getMessage());
            }
        }
        
    }

    public static synchronized SqlConnectionManager getInstance() {
        if (instance == null) {
            instance = new SqlConnectionManager();
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Connection can not be closed!");
            }
        }
    }
    
    private static Properties loadProperties(String filePath) {
    Properties properties = new Properties();
    try (FileInputStream fis = new FileInputStream(filePath)) {
        properties.load(fis);
    } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "app.settings can't be opened");
    }
    return properties;
}
    
}
