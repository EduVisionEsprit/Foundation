package tn.eduVision.services;

import java.io.IOException;
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
        String query = "SELECT * FROM stages where Status='Pending'";
        String query2 = "SELECT * FROM Utilisateurs";
        ResultSet resultSet = dbManager.executeQuery(query);
        ResultSet resultSet2 = dbManager.executeQuery(query2);

        while (resultSet.next()) {
            int stageId = resultSet.getInt("id_stage");
            int utilisateurId = resultSet.getInt("id_utilisateur");

            // Find the corresponding user in the resultSet2
            while (resultSet2.next()) {
                int id = resultSet2.getInt("id_utilisateur");
                if (utilisateurId == id) {
                    String nom = resultSet2.getString("nom");
                    String prenom = resultSet2.getString("prenom");
                    String email = resultSet2.getString("email");
                    String motDePasse = resultSet2.getString("mot_de_passe");
                    String roleStr = resultSet2.getString("Role");
                    Role role = Role.valueOf(roleStr.toUpperCase());
                    Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, motDePasse, role);

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

                    System.out.println("id-utilisateur: " + utilisateur.getIdUtilisateur());

                    listeStages.add(stage);

                    break; // Stop iterating over resultSet2 once the user is found
                }
            }
            resultSet2.beforeFirst(); // Reset the resultSet2 cursor
        }

        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }

    return listeStages;
}

  
   
}
