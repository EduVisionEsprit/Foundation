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
    
    
    public void ajouterStage(Utilisateur user,String nomentreprise,String titrestage,String descstage,String decision,String path) {
        
        System.out.print(user);
        System.out.println(path);
    
       
        
       
       
    try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
        String query = "INSERT INTO stages ( id_utilisateur,nom_Entreprise, Titre_Stage,Description_Stage,path) " +
                       "VALUES (?,?,?,?,?)";
        
        PreparedStatement statement = dbManager.getConnection().prepareStatement(query);
        statement.setInt(1, user.getIdUtilisateur());
        statement.setString(2, nomentreprise);
        statement.setString(3, titrestage);
        statement.setString(4, descstage);
        statement.setString(5, path);
        statement.executeUpdate();
        dbManager.closeConnection();
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
            Files.move(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            
          
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
