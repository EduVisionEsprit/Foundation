/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.io.IOException;
import tn.eduVision.services.DecisionButtonCell;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.eduVision.entités.StageEtudiant;
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

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    
    @FXML
    private void stagelistClicked(ActionEvent event) throws IOException {
        Liste_Stage_Enseignant_Accepte listeStageAccepte = new Liste_Stage_Enseignant_Accepte();
        listeStageAccepte.start(new Stage());

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
}
