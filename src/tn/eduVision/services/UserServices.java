/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.tools.SqlConnectionManager;


/**
 *
 * @author thinkpad
 */
public class UserServices {

    private static UserServices instance;
    private Statement statement;
    private ResultSet res;

    private UserServices() {
        SqlConnectionManager scm = SqlConnectionManager.getInstance();
        try {
            statement = scm.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static UserServices getInstance() {
        if (instance == null) {
            instance = new UserServices();
        }
        return instance;
    }

    public List<Utilisateur> getAllUsers() {
        String request = "select * from utilisateurs";
        List<Utilisateur> userlist = new ArrayList<>();

        try {
            res = statement.executeQuery(request);
            while (res.next()) {
                Utilisateur user = new Utilisateur();
                user.setIdUtilisateur(res.getInt("id_utilisateur"));
                user.setNom(res.getString("nom"));
                user.setPrenom(res.getString("prenom"));
                user.setMotDePasse(res.getString("mot_de_passe"));
                user.setEmail(res.getString("email"));
                user.setRole(Role.valueOf(res.getString("Role")));
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
            res = statement.executeQuery(request);

            if (res.next()) {
                user = new Utilisateur();
                user.setIdUtilisateur(res.getInt("id_utilisateur"));
                user.setNom(res.getString("nom"));
                user.setPrenom(res.getString("prenom"));
                user.setMotDePasse(res.getString("mot_de_passe"));
                user.setEmail(res.getString("email"));
                user.setRole(Role.valueOf(res.getString("Role")));
            }

            res.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public List<Utilisateur> getUsersByRole(Role role) {
        String request = "SELECT * FROM utilisateurs WHERE Role ='"+role.name()+"'";
        List<Utilisateur> userList = new ArrayList<>();

        try {                   
            res = statement.executeQuery(request);

            while (res.next()) {
                Utilisateur user = new Utilisateur();
                user.setIdUtilisateur(res.getInt("id_utilisateur"));
                user.setNom(res.getString("nom"));
                user.setPrenom(res.getString("prenom"));
                user.setMotDePasse(res.getString("mot_de_passe"));
                user.setEmail(res.getString("email"));
                user.setRole(Role.valueOf(res.getString("Role")));
                user.setEtat(res.getInt("approved"));
                userList.add(user);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userList;
    }

    public void insertEtudiant(String userName, String userLastName, String userEmail, String userPass, String userRole) {
        String req = "insert into utilisateurs (nom,prenom,email,mot_de_passe,Role) values ('" + userName + "','" + userLastName + "','" + userEmail + "','" + userPass + "','" + userRole + "')";
        try {
            statement.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
