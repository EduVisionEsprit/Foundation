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
public class ESPACE_STAGE_ENSEIGNANTController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }    
    
    
     @FXML
    private void listButtonClicked(ActionEvent event) throws IOException {
        ListeStageEnseignant listeStageEnseignant = new ListeStageEnseignant();
        listeStageEnseignant.start(new Stage());

        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    
    @FXML
    private void stagelistClicked(ActionEvent event) throws IOException {
        Liste_Stage_Enseignant_Accepte listeStageAccepte = new Liste_Stage_Enseignant_Accepte();
        listeStageAccepte.start(new Stage());

        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    
    
    
    
}
