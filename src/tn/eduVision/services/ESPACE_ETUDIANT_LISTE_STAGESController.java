package tn.eduVision.services;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import tn.eduVision.entités.StageEtudiant;

public class ESPACE_ETUDIANT_LISTE_STAGESController implements Initializable {

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
    private TableColumn<StageEtudiant, StageEtudiant> decision;

    

    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button eraseButton;
    @FXML
    private Button choosefile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nom_entreprise.setCellValueFactory(new PropertyValueFactory<>("nomentreprise"));
        titre_stage.setCellValueFactory(new PropertyValueFactory<>("titrestage"));
        description_stage.setCellValueFactory(new PropertyValueFactory<>("descriptionstage"));
        id_user.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUtilisateur().getIdUtilisateur()).asObject());
        decision.setCellValueFactory(new PropertyValueFactory<>("decision"));

        tableView.setEditable(false); // Disable editing for the TableView

       nom_entreprise.setCellFactory(TextFieldTableCell.forTableColumn());
nom_entreprise.setOnEditCommit(event -> {
    StageEtudiant stage = event.getRowValue();
    stage.setNomentreprise(event.getNewValue());
    ListeStageEtudiant lst = new ListeStageEtudiant();
    System.out.println(event.getNewValue());
    lst.updateStageInDatabase(stage);
});

description_stage.setCellFactory(TextFieldTableCell.forTableColumn());
description_stage.setOnEditCommit(event -> {
    StageEtudiant stage = event.getRowValue();
    stage.setNomentreprise(event.getNewValue());
    ListeStageEtudiant lst = new ListeStageEtudiant();
    lst.updateStageInDatabase(stage);
    
    
});


titre_stage.setCellFactory(TextFieldTableCell.forTableColumn());
titre_stage.setOnEditCommit(event -> {
    StageEtudiant stage = event.getRowValue();
    stage.setNomentreprise(event.getNewValue());
    ListeStageEtudiant lst = new ListeStageEtudiant();
    lst.updateStageInDatabase(stage);
    
    
});





        ListeStageEtudiant list = new ListeStageEtudiant();
        List<StageEtudiant> stages = list.getListeStagesFromDatabase();
        tableView.getItems().addAll(stages);
    }

    @FXML
    private void editButtonClicked(ActionEvent event) {
        StageEtudiant selectedStage = tableView.getSelectionModel().getSelectedItem();
        if (selectedStage != null) {
            
            tableView.setEditable(true);
            tableView.edit(tableView.getSelectionModel().getSelectedIndex(), nom_entreprise); // Start editing the selected row in the "nom_entreprise" column
            
        }
    }

    @FXML
    private void saveButtonClicked(ActionEvent event) {
       StageEtudiant selectedStage = tableView.getSelectionModel().getSelectedItem();
    if (selectedStage != null) {
        String newNmetr = selectedStage.getNomentreprise();
        String newTitstg = selectedStage.getTitrestage();
        String newDesstg = selectedStage.getDescriptionstage();

        
        System.out.println(newNmetr);
        selectedStage.setNomentreprise(newNmetr);
        selectedStage.setTitrestage(newTitstg);
        selectedStage.setDescriptionstage(newDesstg);

        ListeStageEtudiant lst = new ListeStageEtudiant();
        lst.updateStageInDatabase(selectedStage);
    }
    }

    

    @FXML
    private void eraseButtonClicked(ActionEvent event) {
         StageEtudiant selectedStage = tableView.getSelectionModel().getSelectedItem();
    if (selectedStage != null) {
        int stageId = selectedStage.getIdStage();
        System.out.println(stageId);
        DecisionButtonCell delete = new DecisionButtonCell();
        delete.deleteStageFromDatabase(stageId);
        tableView.getItems().remove(selectedStage);
    }
    }

    

    @FXML
    private void chooseFileButtonClicked(ActionEvent event) {
        // Handle the choose file button action
    }
}
