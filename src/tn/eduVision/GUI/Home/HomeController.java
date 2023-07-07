/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI.Home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.eduVision.GUI.SignUp.SignUpController;
import tn.eduVision.services.ListeStageEnseignant;
import tn.eduVision.services.ESPACE_STAGE_ENSEIGNANT;
import tn.eduVision.services.ESPACE_STAGE_ETUDIANT;
import tn.eduVision.services.EspaceStageEtudiant;
import tn.eduVision.services.SessionManager;


/**
 * FXML Controller class
 *
 * @author thinkpad
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    public void switchTesting(ActionEvent event){
         try {           
            Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/SignUp/SignUpFrom.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();       
        } catch (Exception ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    @FXML
private void gotostage(ActionEvent event) throws IOException {
    
    SessionManager sessionManager = SessionManager.getInstance();

     String role = sessionManager.getRole();
     
    
     
    if (role.equals("ENSEIGNANT")) {
        ESPACE_STAGE_ENSEIGNANT espaceStageEnseignant = new ESPACE_STAGE_ENSEIGNANT();
    espaceStageEnseignant.start(new Stage());

    // Fermer la fenêtre actuelle
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    currentStage.close();
    } else if (role.equals("ETUDIANT")) {
        ESPACE_STAGE_ETUDIANT espacestageetudiant = new ESPACE_STAGE_ETUDIANT();
    espacestageetudiant.start(new Stage());

    // Fermer la fenêtre actuelle
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    currentStage.close();
}
    }
}
    
    
    
    
    
    

    
    
    
   
     
