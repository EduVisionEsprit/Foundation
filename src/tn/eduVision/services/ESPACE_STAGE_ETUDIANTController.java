/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Meher
 */
public class ESPACE_STAGE_ETUDIANTController implements Initializable {

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

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    
    @FXML
    private void listestage(ActionEvent event) throws IOException {
        ListeStageEtudiant listestageetudiant = new ListeStageEtudiant();
        listestageetudiant.start(new Stage());

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    @FXML
    private void suiviestage(ActionEvent event) throws IOException {
        SuivieStageEtudiant suiviestageetudiant = new SuivieStageEtudiant();
        suiviestageetudiant.start(new Stage());

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
}
