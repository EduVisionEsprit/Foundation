/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI.Home;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import tn.eduVision.GUI.SignUp.SignUpRoleController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.util.logging.Logger;
import java.util.logging.Level;
import tn.eduVision.GUI.Login.LoginController;
import tn.eduVision.services.SessionManager;

/**
 * FXML Controller class
 *
 * @author thinkpad
 */
public class HomeController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Text Emploi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    @FXML
    private void openEmploi(MouseEvent event) {
        try {
            //TODO: Add root for Emploi
            Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/calander.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @FXML
    private void openNote(MouseEvent event) {
        Parent root;
        try {
            //TODO: Add root for Note
            
            if(LoginController.sessionManager.getRole().equals("ENSEIGNANT"))
                root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/NoteManagementUI.fxml"));
            else
                root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/PublierResultatUI.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void openTest(MouseEvent event) {
        Parent root;
         try {
            //TODO: Add root for Tache
            if(LoginController.sessionManager.getRole().equals("ENSEIGNANT"))
             root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/Test.fxml"));
            else
              root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/TestEtudiant.fxml"));  
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void openStage(MouseEvent event) {
        Parent root;
        try {
            //TODO: Add root for Profile
            if(LoginController.sessionManager.getRole().equals("ENSEIGNANT"))
                root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/ESPACE_STAGE_ENSEIGNANT.fxml"));
            else
                root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/ESPACE_STAGE_ETUDIANT.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
