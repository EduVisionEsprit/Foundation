/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tn.eduVision.GUI.Login.LoginWindow;

/**
 *
 * @author thinkpad
 */
public class Main extends Application {
    
     @Override
    public void start(Stage primaryStage) {
        
       //FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
       //Parent root = loader.load();
        
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/Login/login.fxml"));            
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("University management system");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
        //primaryStage.setResizable(false);
                
        
               
            
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
