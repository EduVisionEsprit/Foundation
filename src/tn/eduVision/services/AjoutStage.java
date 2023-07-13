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
import javafx.stage.Stage;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;




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
    
    
    public void ajouterStage(int userid,String nomentreprise,String titrestage,String descriptionStage,String status,String path,String typestage) {
        
       
     try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");

       
        String stageExistsQuery = "SELECT COUNT(*) FROM stages WHERE id_utilisateur = ?";
        PreparedStatement existsStatement = dbManager.getConnection().prepareStatement(stageExistsQuery);
        existsStatement.setInt(1, userid);
        ResultSet existsResultSet = existsStatement.executeQuery();
        existsResultSet.next();
        int count = existsResultSet.getInt(1);

        if (count > 0) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Vous avez déjà ajouté un stage.");
            alert.showAndWait();
        } else {
          
            String insertQuery = "INSERT INTO stages (id_utilisateur,TypeStage, nom_Entreprise, Titre_Stage, Description_Stage, Status, path) " +
                "VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement insertStatement = dbManager.getConnection().prepareStatement(insertQuery);
            insertStatement.setInt(1, userid);
            insertStatement.setString(2, typestage);
            insertStatement.setString(3, nomentreprise);
            insertStatement.setString(4, titrestage);
            insertStatement.setString(5, descriptionStage);
            insertStatement.setString(6, status);
            insertStatement.setString(7, path);
            
            insertStatement.executeUpdate();
            
           
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

           
            if (!destinationFolderFile.exists()) {
                destinationFolderFile.mkdirs();
            }

           
            Path destinationPath = destinationFolderFile.toPath().resolve(sourceFile.getName());
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            

            
          
        } catch (IOException e) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors du téléchargement du fichier.");
            alert.showAndWait();
            e.printStackTrace();
        }
        
    }

  
      

  

   
}
