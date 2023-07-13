package tn.eduVision.services;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.StageEtudiant;
import tn.eduVision.entités.Utilisateur;


public class ListeStageEnseignant extends Application{
    
    SessionManager sessionManager = SessionManager.getInstance();
    
    String typeens = sessionManager.getTypeenseignant();
    
    public static void main(String[] args) {
        launch(args);
 
       
    }
    
    
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/ESPACE_ENSEIGNANT_LISTE_STAGES.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public List<StageEtudiant> getListeStagesFromDatabase() {
    List<StageEtudiant> listeStages = new ArrayList<>();
    try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
        SessionManager sessionManager = SessionManager.getInstance();
        

        String query = "SELECT s.*, u.* FROM stages s, utilisateurs u WHERE s.Status = 'Pending' AND s.TypeStage = u.specialite_ens AND u.specialite_ens = ?";

        PreparedStatement statement = dbManager.getConnection().prepareStatement(query);
        statement.setString(1, typeens); // Définir la spécialité récupérée en tant que paramètre

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
           
            int stageId = resultSet.getInt("id_stage");
            int utilisateurId = resultSet.getInt("id_utilisateur");
            String nomEntreprise = resultSet.getString("nom_Entreprise");
            String titreStage = resultSet.getString("titre_Stage");
            String descriptionStage = resultSet.getString("description_Stage");
            String typeStage = resultSet.getString("typestage");
            String status = resultSet.getString("Status");

            
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String email = resultSet.getString("email");
            String motDePasse = resultSet.getString("mot_de_passe");
            String specialite_ens = resultSet.getString("specialite_ens");
            String roleStr = resultSet.getString("Role");
            Role role = Role.valueOf(roleStr.toUpperCase());
            Utilisateur utilisateur = new Utilisateur(utilisateurId, nom, prenom, email, motDePasse, role, specialite_ens);
            String nomenseiignant=null;
            String prenomenseignant=null;
          
            StageEtudiant stage = new StageEtudiant(stageId, utilisateur, typeStage, nomEntreprise, titreStage, descriptionStage, status,nomenseiignant,prenomenseignant);
            listeStages.add(stage);
        }

        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }

    return listeStages;
}

  
   
}
