/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI;

/**
 *
 * @author job_j
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.eduVision.GUI.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("calander.fxml"));
     // FXMLLoader loader = new FXMLLoader(getClass().getResource("Test.fxml"));
      // FXMLLoader loader = new FXMLLoader(getClass().getResource("cardTest.fxml"));
   // FXMLLoader loader = new FXMLLoader(getClass().getResource("TestEtudiant.fxml"));
      FXMLLoader loader = new FXMLLoader(getClass().getResource("TestQuizAdmin.fxml")); //dash pour ladmin
        Parent root = loader.load();

        primaryStage.setTitle("Calendar");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

