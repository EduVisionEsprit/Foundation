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
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.StageEtudiant;
import tn.eduVision.entités.Utilisateur;

/**
 *
 * @author Meher
 */
public class SuivieStageEtudiant extends Application {
    
    
    public static void main(String[] args) {
        launch(args);
 
       
    }
    
    
    SessionManager sessionManager = SessionManager.getInstance();
        int connectedUserId = sessionManager.getUserId();
    
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/SUIVIE_STAGE_ETUDIANT.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
   
    public List<StageEtudiant> getSuivieStageEtudiant() {
    List<StageEtudiant> listeStages = new ArrayList<>();
    try {
        

        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");

        String query = "SELECT s.id_stage, s.id_utilisateur, s.nom_Entreprise, s.titre_Stage, s.description_Stage, s.Status, " +
                       "u.id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe, u.Role, " +
                       "e.nom AS enseignant_nom, e.prenom AS enseignant_prenom " +
                       "FROM stages s " +
                       "INNER JOIN Utilisateurs u ON s.id_utilisateur = u.id_utilisateur " +
                       "INNER JOIN Utilisateurs e ON s.id_enseignant = e.id_utilisateur " +
                       "WHERE s.id_utilisateur = ?";
                       
        PreparedStatement statement = dbManager.getConnection().prepareStatement(query);
        statement.setInt(1, connectedUserId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int stageId = resultSet.getInt("s.id_stage");
            int utilisateurId = resultSet.getInt("u.id_utilisateur");
            String nomEntreprise = resultSet.getString("s.nom_Entreprise");
            String titreStage = resultSet.getString("s.titre_Stage");
            String descriptionStage = resultSet.getString("s.description_Stage");
            String status = resultSet.getString("s.Status");
            int id = resultSet.getInt("u.id_utilisateur");
            String nom = resultSet.getString("u.nom");
            String prenom = resultSet.getString("u.prenom");
            String email = resultSet.getString("u.email");
            String motDePasse = resultSet.getString("u.mot_de_passe");
            String roleStr = resultSet.getString("u.Role");
            Role role = Role.valueOf(roleStr.toUpperCase());
            String enseignantNom = resultSet.getString("enseignant_nom");
            String enseignantPrenom = resultSet.getString("enseignant_prenom");

            Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, motDePasse, role);
            utilisateur.setNom(enseignantNom);
            utilisateur.setPrenom(enseignantPrenom);

            StageEtudiant stage = new StageEtudiant(stageId, utilisateur, nomEntreprise, titreStage, descriptionStage, status);

            // Set nom and prenom of connected user
            stage.getUtilisateur().setNom(nom);
            stage.getUtilisateur().setPrenom(prenom);

            listeStages.add(stage);
        }

        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }

    return listeStages;
}

    
    
    public void ajouterrapport(String path) {
        
        
    
         
        
       
     try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");

        // Update the rapport_stage column in suivistage table
        String updateQuery = "UPDATE suivistage SET rapport_stage = ? WHERE id_utilisateur = ?";
                
        PreparedStatement updateStatement = dbManager.getConnection().prepareStatement(updateQuery);
        updateStatement.setString(1, path);
        updateStatement.setInt(2, connectedUserId);
            
        updateStatement.executeUpdate();
            
            // Close the connection
            dbManager.closeConnection();
        }
    catch (SQLException e) {
        System.out.println(e);
    }
    
    

      
    }      
    
    
    
    
    
     
    
    

      
    public void inserevalidation(int stageId, int connecteduser) {
        
        System.out.println("Stage ID: " + stageId);
System.out.println("Connected User ID: " + connecteduser);
    String validation ="Validation en cours";
         
        
       
     try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");

        // Update the rapport_stage column in suivistage table
        String updateQuery = "UPDATE suivistage SET validation_rapport = ? WHERE id_utilisateur = ? AND id_stage=?";
                
        PreparedStatement updateStatement = dbManager.getConnection().prepareStatement(updateQuery);
        updateStatement.setString(1, validation);
        updateStatement.setInt(2, connecteduser);
         updateStatement.setInt(3, stageId);
            
        updateStatement.executeUpdate();
            
            // Close the connection
            dbManager.closeConnection();
        }
    catch (SQLException e) {
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors du téléchargement du fichier.");
            alert.showAndWait();
            e.printStackTrace();
        }
        
    }
    
    
    
    public String getEmailForEnseignant(int stageId) {
    String email = null;

    try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");

        // Retrieve the id_enseignant from the stages table
        String enseignantQuery = "SELECT id_enseignant FROM stages WHERE id_stage = ?";
        PreparedStatement enseignantStatement = dbManager.getConnection().prepareStatement(enseignantQuery);
        enseignantStatement.setInt(1, stageId);
        ResultSet enseignantResult = enseignantStatement.executeQuery();

        if (enseignantResult.next()) {
            int idEnseignant = enseignantResult.getInt("id_enseignant");

            // Retrieve the email from the utilisateurs table for the id_enseignant
            String emailQuery = "SELECT email FROM utilisateurs WHERE id_utilisateur = ?";
            PreparedStatement emailStatement = dbManager.getConnection().prepareStatement(emailQuery);
            emailStatement.setInt(1, idEnseignant);
            ResultSet emailResult = emailStatement.executeQuery();

            if (emailResult.next()) {
                email = emailResult.getString("email");
            }
        }

        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }

    return email;
}
    
    
    
    
    
    
   public String getValidationText(int stageId) {
    try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
        Connection conn = dbManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT validation_rapport FROM suivistage WHERE id_stage = ?");
        stmt.setInt(1, stageId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("validation_rapport");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return ""; // Default value if the validation text cannot be retrieved
}

public Float getNoteValue(int stageId) {
    try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
        Connection conn = dbManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT note FROM evaluationstage WHERE id_stage = ?");
        stmt.setInt(1, stageId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Float noteValue = rs.getFloat("note");
            
            return noteValue;
            
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0f; // Default value if the note value cannot be retrieved
    
    
}


}
