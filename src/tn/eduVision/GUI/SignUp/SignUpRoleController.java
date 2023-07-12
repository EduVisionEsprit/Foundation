/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI.SignUp;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.eduVision.GUI.Login.LoginWindow;

/**
 * FXML Controller class
 *
 * @author thinkpad
 */
public class SignUpRoleController implements Initializable {
    
    private Stage stage;
    private Stage primaryStage;
    private Scene scene;   
    @FXML
    private AnchorPane EnsAnchorPane;    
    @FXML
    private AnchorPane StuAnchorPane;
    @FXML
    private ImageView teaB;
    @FXML
    private ImageView stdB;
    @FXML
    private Button exit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.primaryStage = stage;
        // TODO
    }

    @FXML
    private void signUpFromEns(MouseEvent event) {

        SignUpFormController.role = "enseignant".toLowerCase();
                navigateToSignupPage(event);            
    }

    @FXML
    private void signUpFromEtu(MouseEvent event) {
        SignUpFormController.role = "etudiant".toLowerCase();
                navigateToSignupPage(event);

    }

    private void navigateToSignupPage(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/SignUp/SignupForm.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root,600,400);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void exitRole(MouseEvent event) {
         try {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/Login/login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            } catch (Exception ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
