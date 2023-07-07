/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;


import java.io.IOException;
import java.sql.Connection;
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

/**
 *
 * @author Meher
 */
public class Liste_Stage_Enseignant_Accepte extends Application {
    
    
    
    
     public static void main(String[] args) {
        launch(args);
 
       
    }
    
    
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/ESPACE_ENSEIGNANT_LISTE_STAGE_Accepte.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
    
    
    public List<StageEtudiant> getListeStagesaccepteFromDatabase() {
    List<StageEtudiant> listeStages = new ArrayList<>();
    try {
        SessionManager sessionManager = SessionManager.getInstance();
        int userid = sessionManager.getUserId();
        System.out.println(userid);

        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");

        String query = "SELECT s.id_stage, s.id_utilisateur, s.nom_Entreprise, s.titre_Stage, s.description_Stage, s.Status, u.id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe, u.Role " +
                       "FROM stages s " +
                       "INNER JOIN Utilisateurs u ON s.id_utilisateur = u.id_utilisateur " +
                       "WHERE s.id_enseignant = ?";
                       
        PreparedStatement statement = dbManager.getConnection().prepareStatement(query);
        statement.setInt(1, userid);
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

            Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, motDePasse, role);
            StageEtudiant stage = new StageEtudiant(stageId, utilisateur, nomEntreprise, titreStage, descriptionStage, status);

            listeStages.add(stage);
            
        }

        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }

    return listeStages;
}

    
    
    
    
   public void EvaluationStage(int stageId, int userId, float note) {
    try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
        Connection conn = dbManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO evaluationstage (id_stage, id_utilisateur, note) VALUES (?, ?, ?)");

        stmt.setInt(1, stageId);
        stmt.setInt(2, userId);
        stmt.setFloat(3, note);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Data inserted successfully into evaluationstage table.");

            // Update the validation_rapport column in the suivistage table based on the note value
            String validationMessage = (note >= 10) ? "Votre stage est validé" : "Votre stage n'est pas validé";
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE suivistage SET validation_rapport = ? WHERE id_stage = ?");
            updateStmt.setString(1, validationMessage);
            updateStmt.setInt(2, stageId);
            int updateRowsAffected = updateStmt.executeUpdate();
            if (updateRowsAffected > 0) {
                System.out.println("Validation message updated in suivistage table.");
            } else {
                System.out.println("Failed to update validation message in suivistage table.");
            }
        } else {
            System.out.println("Failed to insert data into evaluationstage table.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}






}