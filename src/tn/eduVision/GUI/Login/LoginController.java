/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI.Login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.stage.StageStyle;
import tn.eduVision.GUI.Home.HomeController;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.services.SessionManager;
import tn.eduVision.services.UserServices;

import tn.eduVision.services.hashPassword;

/**
 * FXML Controller class
 *
 * @author thinkpad
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfID;
    @FXML
    private Button btnLogin;
    @FXML
    private Label loginMessageLabel;
    private Connection connect;

    private PreparedStatement sqlQuery;
    private ResultSet res;
    private Parent root;
    private Stage stage;
    private Scene scene;    
    static Boolean UserIsconnected = false;
    public static SessionManager sessionManager = SessionManager.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Login(ActionEvent event) {        
        UserServices us = UserServices.getInstance();
        hashPassword hashedpass=hashPassword.getHashInstance();
        List<Utilisateur> users = us.getAllUsers();
        if (tfID.getText().trim().isEmpty() == false && tfPassword.getText().trim().isEmpty() == false) {
            String login = tfID.getText();
            String password = tfPassword.getText();
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";            
            if (login.matches(emailRegex)) {
                for (Utilisateur user : users) {
                    if (user.getEmail().equals(tfID.getText()) && user.getMotDePasse().equals(hashedpass.encrypt(tfPassword.getText()))) {
                        UserIsconnected = true;
                                            int id = user.getIdUtilisateur();
                    String role = String.valueOf(user.getRole());
                    String nom = (user.getNom());
                    String prenom = (user.getPrenom());
                    String typeenseignant = (user.getSpecialite_ens());                                                           
                    sessionManager.setUserId(id);
                    sessionManager.setRole(role);
                    sessionManager.setNom(nom);
                    sessionManager.setPrenom(prenom);
                    sessionManager.setTypeenseignant(typeenseignant);

                        if(user.getRole().toString().equals("ADMIN")){
                            try {
                            root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/AdminDashboard/AdminDashboad.fxml"));
                            scene = new Scene(root);
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                            } catch (Exception e) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
                        }
                        }
                        
                        else{                                                    
                        try {
                            root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/Home/Home.fxml"));
                            scene = new Scene(root);
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                        } catch (Exception e) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
                        }
                        }

                        /*
                         
                                 try {                   
                 sqlQuery.setString(1, login);
                sqlQuery.setString(2, password);
                res=sqlQuery.executeQuery();  
                if (res.next()) {
                    loginMessageLabel.setText("login ...");
                System.out.println("Login Successfully!");
                }
                } catch (Exception e) {   
                                         
                }
                         */
                    }
                }
                if (!UserIsconnected) {
                    loginMessageLabel.setText("Login or password incorrect");
                }

            } else {
                loginMessageLabel.setText("Please enter a valid email!");

            }
        } else if (tfID.getText().trim().isEmpty() && tfPassword.getText().trim().isEmpty() == false) {
            loginMessageLabel.setText("Please Enter your login");
        } else if (tfPassword.getText().trim().isEmpty() && tfID.getText().trim().isEmpty() == false) {
            loginMessageLabel.setText("Please Enter your password!");
        } else {
            loginMessageLabel.setText("Please Enter your login and password!");
        }

    }

    public void signUp(ActionEvent event) {
        try {
            /*
            Stage primaryStage = (Stage) root.getScene().getWindow();
             primaryStage.setScene(signUpScene);   
             */

            Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/SignUp/signRoleUp.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
