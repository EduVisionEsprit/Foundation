/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author thinkpad
 */
public class UserServices {  
        private  Connection _connection = SqlConnectionManager.getInstance().getConnection();

    private static UserServices instance;
    private Statement statement;
    private ResultSet res;
    
    public UserServices() {
         SqlConnectionManager scm=SqlConnectionManager.getInstance();
        try {
            statement=scm.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static UserServices getInstance(){
        if(instance==null) 
            instance=new UserServices();
        return instance;
    }
        
    
    public List<Utilisateur> getAllUsers() {
        String request="select * from utilisateurs";
        List<Utilisateur> userlist=new ArrayList<>();
        
        try {
            res=statement.executeQuery(request);
            while (res.next()) {
    Utilisateur user = new Utilisateur(
        res.getInt("id_utilisateur"),
        res.getString("nom"),
        res.getString("prenom"),
        res.getString("email"),
        res.getString("mot_de_passe"),
        Role.valueOf(res.getString("Role")),
        res.getString("specialite_ens")
    );
    userlist.add(user);
}
            
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userlist;
    }
    
    public Utilisateur getUserById(int userId) {
    String request = "SELECT * FROM utilisateurs WHERE id_utilisateur = ?";
    Utilisateur user = null;

    try {        
         res=statement.executeQuery(request);

        if (res.next()) {
    user = new Utilisateur(
        res.getInt("id_utilisateur"),
        res.getString("nom"),
        res.getString("prenom"),
        res.getString("email"),
        res.getString("mot_de_passe"),
        Role.valueOf(res.getString("Role")),
        res.getString("specialite_ens")
    );
}
        res.close();
        statement.close();
    } catch (SQLException ex) {
        Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
    }

    return user;
}

  public List<Utilisateur> getUsersByRole(Role role) {
    String request = "SELECT * FROM utilisateurs WHERE Role = ?";
    List<Utilisateur> userList = new ArrayList<>();

    try {
        PreparedStatement preparedStatement = _connection.prepareStatement(request);
        preparedStatement.setString(1, role.name());
        ResultSet resultSet = preparedStatement.executeQuery();

      while (res.next()) {
    Utilisateur user = new Utilisateur(
        res.getInt("id_utilisateur"),
        res.getString("nom"),
        res.getString("prenom"),
        res.getString("email"),
        res.getString("mot_de_passe"),
        Role.valueOf(res.getString("Role")),
            res.getString("specialite_ens")
    );
    userList.add(user);
}
        resultSet.close();
        preparedStatement.close();
    } catch (SQLException ex) {
        Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
    }

    return userList;
}


    public List<Etudiant> getEtudiants() throws SQLException {
    String request = "SELECT * FROM utilisateurs WHERE Role = ?";
    List<Etudiant> etudiantList = new ArrayList<>();

    try {
        PreparedStatement preparedStatement = _connection.prepareStatement(request);
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (res.next()) {
    Utilisateur user = new Utilisateur(
        res.getInt("id_utilisateur"),
        res.getString("nom"),
        res.getString("prenom"),
        res.getString("email"),
        res.getString("mot_de_passe"),
        Role.valueOf(res.getString("Role")),
        res.getString("specialite_ens")
    );
    etudiantList.add((Etudiant) user);
        }
        
        resultSet.close();
        preparedStatement.close();
        

        
    } catch (SQLException ex) {
        Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
    }

            return null;
      
}



}