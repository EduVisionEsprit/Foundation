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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
public class ESPACE_ENSEIGNANT_LISTE_STAGE_ACCEPTEController implements Initializable {
    
    
    
    
    DecisionButtonCell decisionButton = new DecisionButtonCell();

    @FXML
    public TableView<StageEtudiant> tableView;
    @FXML
    private TableColumn<StageEtudiant, String> nom_entreprise;
    @FXML
    private TableColumn<StageEtudiant, String> titre_stage;
    @FXML
    private TableColumn<StageEtudiant, String> description_stage;
    @FXML
    private TableColumn<StageEtudiant, Integer> id_user;
    @FXML
    private TableColumn<StageEtudiant, Integer> Etudiant;
    @FXML
    private TableColumn<StageEtudiant, StageEtudiant> Rapport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
         nom_entreprise.setCellValueFactory(new PropertyValueFactory<>("nomentreprise"));
        titre_stage.setCellValueFactory(new PropertyValueFactory<>("titrestage"));
        description_stage.setCellValueFactory(new PropertyValueFactory<>("descriptionstage"));
        Etudiant.setCellValueFactory(new PropertyValueFactory<>("etudiant"));
       
    
        id_user.setCellValueFactory(new PropertyValueFactory<>("utilisateur"));
        id_user.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUtilisateur().getIdUtilisateur()).asObject());
        
        Etudiant.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getUtilisateur().getNom() + "  " +cellData.getValue().getUtilisateur().getPrenom() ));
        
        

        Liste_Stage_Enseignant_Accepte list = new Liste_Stage_Enseignant_Accepte();
        List<StageEtudiant> stages = list.getListeStagesaccepteFromDatabase();
        tableView.getItems().addAll(stages);
    }    
    
}
