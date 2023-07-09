/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;
import java.sql.ResultSet;
import java.sql.SQLException;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.entités.Role;

/**
 *
 * @author Meher
 */
public class UtilisateurService {
    
    
    public Utilisateur getUtilisateurById(int id) {
        
        
        try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
        
        
        
        String query = "SELECT * FROM utilisateurs WHERE id_utilisateur = "+id;
    ResultSet resultSet = dbManager.executeQuery(query);
    
    if (resultSet.next()) {
       
        String nom = resultSet.getString("nom");
        String prenom = resultSet.getString("prenom");
        String email = resultSet.getString("email");
        String motDePasse = resultSet.getString("mot_de_passe");
        String specialite_ens = resultSet.getString("specialite_ens");
        String roleString = resultSet.getString("role");
        
        Role role = Role.valueOf(roleString.toUpperCase());
        
        Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, motDePasse, role,specialite_ens);
        return utilisateur;
    }
        
        
        } catch (SQLException e) {
        System.out.println(e);
    }
     
        return null;
    }
    
}
