/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 *
 * @author Meher
 */
public class EspaceStageEtudiant extends Application{
    
    
    
    public static void main(String[] args) {
        launch(args);
 
       
    }

    
    
     public void start(Stage primaryStage) {
        primaryStage.setTitle("ESPACE STAGE ETUDIANT");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        
        Button addButton1 = new Button("Ajouter un Stage");
        addButton1.getStyleClass().add("CSS/button-style");
        
        Button addButton2 = new Button("Liste des Stages");
        addButton2.getStyleClass().add("CSS/button-style");
        
        addButton1.setOnAction(e -> {
        
         AjoutStage liste = new AjoutStage();
         liste.start(primaryStage);
        
    
        });
        
        addButton2.setOnAction(e -> {
        
         ListeStageEtudiant liste = new ListeStageEtudiant();
            try {
                liste.start(primaryStage);
            } catch (IOException ex) {
                Logger.getLogger(EspaceStageEtudiant.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    
        });
        
        GridPane.setConstraints(addButton1, 1, 1);
        GridPane.setConstraints(addButton2, 1, 2);
        
        
        
         Scene scene = new Scene(grid, 400, 200);
    
         grid.getChildren().add(addButton1);
         grid.getChildren().add(addButton2);

      scene.getStylesheets().add("CSS/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        
     }

    
    
     
     
    
}
