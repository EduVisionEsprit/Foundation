/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI.SignUp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.MouseEvent;
import tn.eduVision.services.UserServices;
import tn.eduVision.services.hashPassword;

/**
 * FXML Controller class
 *
 * @author thinkpad
 */
public class SignUpFormController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root; 
    private static SignUpFormController instance;
    public static String role;
    public static SignUpFormController getInstance() {
        if (instance == null) {
            instance = new SignUpFormController();
        }
        return instance;
    }
   
    @FXML
    private TextField nomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField prenomField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button confirmButton;
    @FXML
    private Button Annuler;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ConfirmAction(MouseEvent event) {                
        hashPassword pass = hashPassword.getHashInstance();
        UserServices user = UserServices.getInstance();
        SignUpFormController signUpFormControllerPage = SignUpFormController.getInstance();
        if (nomField.getText().trim().isEmpty() == false && emailField.getText().trim().isEmpty() == false && prenomField.getText().trim().isEmpty() == false && passwordField.getText().trim().isEmpty() == false) {
            String encryptedpass = pass.encrypt(passwordField.getText());
            String userName = nomField.getText();
            String userLastName = prenomField.getText();
            String userEmail = emailField.getText();
            String userRole = SignUpFormController.role;
            user.insertEtudiant(userName, userLastName, userEmail, encryptedpass, userRole);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/Login/login.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {

            }
        }
    }    

    @FXML
    private void returnToRole(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/SignUp/signRoleUp.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(SignUpFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   

}
