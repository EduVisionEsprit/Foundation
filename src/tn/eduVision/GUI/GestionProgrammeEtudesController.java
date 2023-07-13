package tn.eduVision.GUI;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import tn.eduVision.entités.Admin;
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.Stage;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.services.ProgrammeEtudeService;

public class GestionProgrammeEtudesController {
    @FXML
    private TableView<ProgrammeEtude> programmesTable;

   

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
    Utilisateur  currentUser = getCurrentUser(); 
    if (!programmeEtudeService.isAdmin(currentUser)) {
        showAccessDeniedAlert();
        return;
    }
 


    desc_col.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

    programmesTable.setItems(FXCollections.observableArrayList(programmeEtudeService.getAll()));

    programmesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            txt_description.setText(newValue.getDescription());
        }
    });
}
//private Utilisateur getCurrentUser() {
    // Implement after Integration
  //  return AuthenticationService.getCurrentUser();
//}
   private Utilisateur getCurrentUser() {
    // Create a dummy user for testing purposes
    Utilisateur dummyUser = new Admin(); 
    dummyUser.setRole(Role.ADMIN); 

    return dummyUser;
}
private void showAccessDeniedAlert() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Accès refusé");
    alert.setHeaderText(null);
    alert.setContentText("Accès refusé. Vous devez être un administrateur pour accéder à cette fonctionnalité.");
    alert.showAndWait();
}

    @FXML
private void add() {
    String description = txt_description.getText();

    // Check if the ProgrammeEtude already exists
    if (isProgrammeEtudeExists(description)) {
        // Display a popup message indicating that the ProgrammeEtude already exists
        displayErrorMessage("Erreur", "Ce programme d'étude existe déjà", "Veuillez entrer un programme d'étude différent.");
    } else {
        ProgrammeEtude programmeEtude = new ProgrammeEtude();
        programmeEtude.setDescription(description);
        programmeEtudeService.add(programmeEtude);
        programmesTable.getItems().add(programmeEtude);
        clearFields();
        displaySuccessMessage("Succès", "Insertion réussie", "Le programme d'étude a été inséré avec succès.");
    }
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
        displaySuccessMessage("Succès", "Mise à jour réussie", "Le programme d'étude a été mis à jour avec succès.");
    }
}

@FXML
private void delete() {
    ProgrammeEtude selectedProgrammeEtude = programmesTable.getSelectionModel().getSelectedItem();
    if (selectedProgrammeEtude != null) {
        programmeEtudeService.delete(selectedProgrammeEtude);
        programmesTable.getItems().remove(selectedProgrammeEtude);
        clearFields();
        displaySuccessMessage("Succès", "Suppression réussie", "Le programme d'étude a été supprimé avec succès.");
    }
}

private void displayErrorMessage(String title, String header, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}

private void displaySuccessMessage(String title, String header, String content) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}


private boolean isProgrammeEtudeExists(String description) {
    List<ProgrammeEtude> programmeEtudeList = programmeEtudeService.getAll();
    for (ProgrammeEtude programmeEtude : programmeEtudeList) {
        if (programmeEtude.getDescription().equals(description)) {
            return true;
        }
    }
    return false;
}


    private void clearFields() {
        txt_description.clear();
        programmesTable.getSelectionModel().clearSelection();
    }
}