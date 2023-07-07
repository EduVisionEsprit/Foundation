package tn.eduVision.services;

import java.io.IOException;
import java.sql.PreparedStatement;
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

public class ListeStageEtudiant extends Application{
    
    
    
    public static void main(String[] args) {
        launch(args);
 
       
    }
    
    
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/ESPACE_ETUDIANT_LISTE_STAGES.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public List<StageEtudiant> getListeStagesFromDatabase() {
        List<StageEtudiant> listeStages = new ArrayList<>();
         try {
             SessionManager sessionManager = SessionManager.getInstance();
             int userId = sessionManager.getUserId();
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
            String query = "SELECT * from stages where id_utilisateur="+userId;
            String query2 = "select * from Utilisateurs where id_utilisateur="+userId;
            ResultSet resultSet = dbManager.executeQuery(query);
            ResultSet resultSet2 = dbManager.executeQuery(query2);
            
            while (resultSet2.next()) {
                    int id = resultSet2.getInt("id_utilisateur");
                    String nom = resultSet2.getString("nom");
                    String prenom = resultSet2.getString("prenom");
                    String email = resultSet2.getString("email");
                    String motDePasse = resultSet2.getString("mot_de_passe");
                    String roleStr = resultSet2.getString("Role"); // Assuming role is stored as a string in the database
                    Role role = Role.valueOf(roleStr.toUpperCase()); // Convert the string to the Role enum
                    
                    Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, motDePasse, role);
                    
                    utilisateur.setIdUtilisateur(id);
                    utilisateur.setNom(nom);
                    utilisateur.setPrenom(prenom);
                    utilisateur.setEmail(email);
                    utilisateur.setMotDePasse(motDePasse);
                    utilisateur.setRole(role);
            
            
            

            while (resultSet.next()) {
                int stageId = resultSet.getInt("id_stage");
                int utilisateurId = resultSet.getInt("id_utilisateur");
                String nomEntreprise = resultSet.getString("nom_Entreprise");
                String titreStage = resultSet.getString("titre_Stage");
                String descriptionStage = resultSet.getString("description_Stage");
                String Status = resultSet.getString("Status");
                
                
                StageEtudiant stage = new StageEtudiant(stageId, utilisateur, nomEntreprise, titreStage, descriptionStage, Status);
                stage.setStageId(stageId);
                stage.setUtilisateur(utilisateur);
                stage.setNomentreprise(nomEntreprise);
                stage.setTitrestage(titreStage);
                stage.setDescriptionstage(descriptionStage);
                stage.setStatus(Status);
                 
                
                 listeStages.add(stage);
                 
                 
                      
                 
            }}
dbManager.closeConnection();
            
        } catch (SQLException e) {
            System.out.println(e);
        }

        return listeStages;
    }

  
    
    
   public void updateStageInDatabase(StageEtudiant stage) {
       
     System.out.println(stage.getNomentreprise());
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");

            String query = "UPDATE stages SET nom_entreprise = ?, titre_stage = ?, description_stage = ? WHERE id_stage = ?";

            try (PreparedStatement statement = dbManager.getConnection().prepareStatement(query)) {
                statement.setString(1, stage.getNomentreprise());
                statement.setString(2, stage.getTitrestage());
                statement.setString(3, stage.getDescriptionstage());
                statement.setInt(4, stage.getIdStage());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Stage updated successfully.");
                } else {
                    System.out.println("Failed to update stage.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    
    
    
    
    
    
    
    
    
     
}

