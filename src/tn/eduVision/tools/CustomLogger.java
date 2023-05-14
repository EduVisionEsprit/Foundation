/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.tools;

/**
 *
 * @author job_j
 */
public class CustomLogger {
    
    private static CustomLogger instance;
    private Logger logger;
    private ConsoleHandler consoleHandler;

    private CustomLogger() {
        logger = Logger.getLogger(CustomLogger.class.getName());
        logger.setUseParentHandlers(false);
        consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
    }

    public static synchronized CustomLogger getInstance() {
        if (instance == null) {
            instance = new CustomLogger();
        }
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }
}
