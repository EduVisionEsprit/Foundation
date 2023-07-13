/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

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
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.eduVision.GUI.Home.HomeController;
import tn.eduVision.GUI.Login.LoginController;
import tn.eduVision.GUI.Login.LoginWindow;

/**
 * FXML Controller class
 *
 * @author Meher
 */
public class ESPACE_STAGE_ETUDIANTController implements Initializable {

    @FXML
    private Button AjoutStage;
    @FXML
    private Button listestage;
    @FXML
    private Button suiviestage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
    @FXML
    private void ajouterstage(ActionEvent event) throws IOException {
        AjoutStage ajoutstage = new AjoutStage();
        ajoutstage.start(new Stage());

        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    
    @FXML
    private void listestage(ActionEvent event) throws IOException {
        ListeStageEtudiant listestageetudiant = new ListeStageEtudiant();
        listestageetudiant.start(new Stage());

        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    @FXML
    private void suiviestage(ActionEvent event) throws IOException {
        SuivieStageEtudiant suiviestageetudiant = new SuivieStageEtudiant();
        suiviestageetudiant.start(new Stage());

       
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void BackToMainScreen(ActionEvent event) {        
            
        Scene scene;
        try {                
            Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
