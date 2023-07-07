/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert;
    import javafx.scene.control.Alert.AlertType;
import tn.eduVision.entités.Utilisateur;



/**
 *
 * @author Meher
 */
public class AjoutStage extends Application {
    
    
    
    public static void main(String[] args) {
        launch(args);
 
       
    }
    
    
  public void start(Stage primaryStage) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/AjoutStage.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    
    public void ajouterStage(int userid,String nomentreprise,String titrestage,String descriptionStage,String status,String path) {
        
       
     try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");

        // Check if the user already has a stage
        String stageExistsQuery = "SELECT COUNT(*) FROM stages WHERE id_utilisateur = ?";
        PreparedStatement existsStatement = dbManager.getConnection().prepareStatement(stageExistsQuery);
        existsStatement.setInt(1, userid);
        ResultSet existsResultSet = existsStatement.executeQuery();
        existsResultSet.next();
        int count = existsResultSet.getInt(1);

        if (count > 0) {
            // User already has a stage, show error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Vous avez déjà ajouté un stage.");
            alert.showAndWait();
        } else {
            // User doesn't have a stage, add it to the database
            String insertQuery = "INSERT INTO stages (id_utilisateur, nom_Entreprise, Titre_Stage, Description_Stage, Status, path) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = dbManager.getConnection().prepareStatement(insertQuery);
            insertStatement.setInt(1, userid);
            insertStatement.setString(2, nomentreprise);
            insertStatement.setString(3, titrestage);
            insertStatement.setString(4, descriptionStage);
            insertStatement.setString(5, status);
            insertStatement.setString(6, path);
            insertStatement.executeUpdate();
            
            // Close the connection
            dbManager.closeConnection();
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    
    

      
    }      
    
    
    
    public void uploadFile(String filePath, String destinationFolder) {
        try {
            File sourceFile = new File(filePath);
            File destinationFolderFile = new File(destinationFolder);

            // Create the destination folder if it doesn't exist
            if (!destinationFolderFile.exists()) {
                destinationFolderFile.mkdirs();
            }

            // Move the file to the destination folder
            Path destinationPath = destinationFolderFile.toPath().resolve(sourceFile.getName());
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            

            
          
        } catch (IOException e) {
            // Show an error message if the file upload fails
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors du téléchargement du fichier.");
            alert.showAndWait();
            e.printStackTrace();
        }
        
    }

  
      

  

   
}
