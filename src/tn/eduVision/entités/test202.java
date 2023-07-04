/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.entités;

import tn.eduVision.services.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meher
 */
public class test202 {
    
    
        
    public static void main(String[] args) {
        
    
       try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
            String query = "SELECT * from stages";
            String query2 = "select * from Utilisateurs";
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
                    System.out.println("Nomentreprise: " + utilisateur.getIdUtilisateur());    
            
            
            

            while (resultSet.next()) {
                int stageId = resultSet.getInt("id_stage");
                int utilisateurId = resultSet.getInt("id_utilisateur");
                String nomEntreprise = resultSet.getString("nom_Entreprise");
                String titreStage = resultSet.getString("titre_Stage");
                String descriptionStage = resultSet.getString("description_Stage");
                String decision = resultSet.getString("Decision");
                
                StageEtudiant stage = new StageEtudiant(stageId, utilisateur, nomEntreprise, titreStage, descriptionStage, decision);
                stage.setStageId(stageId);
                stage.setUtilisateur(utilisateur);
                stage.setNomentreprise(nomEntreprise);
                stage.setTitrestage(titreStage);
                stage.setDescriptionstage(descriptionStage);
                stage.setDecision(decision);
                 System.out.println("Nomentreprise: " + stage.getNomentreprise());
                 System.out.println("Nomentreprise: " + stage.getUtilisateur());    
                  
                 
                 
                 
                 dbManager.closeConnection();
            }}
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    
   
    
}
