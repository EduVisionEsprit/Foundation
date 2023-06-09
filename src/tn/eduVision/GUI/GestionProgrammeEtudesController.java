package tn.eduVision.GUI;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.entités.Stage;
import tn.eduVision.services.ProgrammeEtudeService;

public class GestionProgrammeEtudesController {
    @FXML
    private TableView<ProgrammeEtude> programmesTable;

    @FXML
    private TableColumn<ProgrammeEtude, Integer> id_col;

    @FXML
    private TableColumn<ProgrammeEtude, String> desc_col;

    @FXML
    private TextField txt_description;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    private ProgrammeEtudeService programmeEtudeService;

    public GestionProgrammeEtudesController() {
        programmeEtudeService = new ProgrammeEtudeService();
    }

    @FXML
    public void initialize() {
      id_col.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
desc_col.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        // Charger les données dans la table
programmesTable.setItems(FXCollections.observableArrayList(programmeEtudeService.getAll()));

        // Gérer la sélection d'un programme d'études dans la table
        programmesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txt_description.setText(newValue.getDescription());
            }
        });
    }

@FXML
private void add() {
    String description = txt_description.getText();
    ProgrammeEtude programmeEtude = new ProgrammeEtude();
    programmeEtude.setDescription(description);
    programmeEtudeService.add(programmeEtude);
    programmesTable.getItems().add(programmeEtude);
    clearFields();
}

    @FXML
    private void update() {
        ProgrammeEtude selectedProgrammeEtude = programmesTable.getSelectionModel().getSelectedItem();
        if (selectedProgrammeEtude != null) {
            String newDescription = txt_description.getText();
            selectedProgrammeEtude.setDescription(newDescription);
            programmeEtudeService.update(selectedProgrammeEtude);
            programmesTable.refresh();
            clearFields();
        }
    }

    @FXML
    private void delete() {
        ProgrammeEtude selectedProgrammeEtude = programmesTable.getSelectionModel().getSelectedItem();
        if (selectedProgrammeEtude != null) {
            programmeEtudeService.delete(selectedProgrammeEtude);
            programmesTable.getItems().remove(selectedProgrammeEtude);
            clearFields();
        }
    }
 



    private void clearFields() {
        txt_description.clear();
        programmesTable.getSelectionModel().clearSelection();
    }
}