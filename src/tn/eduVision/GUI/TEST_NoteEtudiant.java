package tn.eduVision.GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 *
 * @author bhsan
 */
public class TEST_NoteEtudiant extends Application {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/**
 *
 * @author bhsan
 */
    
    @Override
public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("NoteEtudiant.fxml"));
    Parent root = loader.load();

    NoteEtudiantController  controller = loader.getController();
 
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
}


    public static void main(String[] args) {
        launch(args);
    }
    
}

    

