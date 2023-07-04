/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.StageEtudiant;
import tn.eduVision.entités.Utilisateur;
/**
 *
 * @author Meher
 */
public class ESPACE_STAGE_ETUDIANT extends Application {
    
    
    
     public static void main(String[] args) {
        launch(args);
 
       
    }
    
    
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/ESPACE_STAGE_ETUDIANT.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
