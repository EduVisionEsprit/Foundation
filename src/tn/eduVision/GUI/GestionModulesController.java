package tn.eduVision.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import tn.eduVision.entités.Admin;
import tn.eduVision.entités.Module;
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.services.ModuleService;
import tn.eduVision.services.ProgrammeEtudeService;

public class GestionModulesController {
    @FXML
    private TableView<Module> modulesTable;

    @FXML
    private TableColumn<Module, String> nom_col;

    @FXML
    private TableColumn<Module, String> idProg_col;

    @FXML
    private TextField txt_nomModule;

    @FXML
    private ComboBox<ProgrammeEtude> programmeComboBox;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    private ModuleService moduleService;
    private ProgrammeEtudeService programmeService;

    private ObservableList<Module> moduleList;

    public GestionModulesController() {
        moduleService = new ModuleService();
        programmeService = new ProgrammeEtudeService();
    }

    public void initialize() {
         Utilisateur  currentUser = getCurrentUser(); 
    if (!moduleService.isAdmin(currentUser)) {
        showAccessDeniedAlert();
        return;
    }
        nom_col.setCellValueFactory(cellData -> cellData.getValue().nomModuleProperty());
        idProg_col.setCellValueFactory(cellData -> {
            ProgrammeEtude programme = cellData.getValue().getProgramme();
            String description = (programme != null) ? programme.getDescription() : "";
            return new SimpleStringProperty(description);
        });

        moduleList = FXCollections.observableArrayList(moduleService.getAll());
        modulesTable.setItems(moduleList);

        ObservableList<ProgrammeEtude> programmeList = FXCollections.observableArrayList(programmeService.getAll());
        programmeComboBox.setItems(programmeList);

        modulesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txt_nomModule.setText(newSelection.getNomModule());
                programmeComboBox.setValue(newSelection.getProgramme());
            } else {
                txt_nomModule.clear();
                programmeComboBox.getSelectionModel().clearSelection();
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
    dummyUser.setRole(Role.admin); 

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
    public void add() {
        String nomModule = txt_nomModule.getText();
        ProgrammeEtude selectedProgramme = programmeComboBox.getValue();

        if (nomModule.isEmpty() || selectedProgramme == null) {
            showAlert(AlertType.ERROR, "Erreur de saisie", "Veuillez entrer un nom de module et sélectionner un programme.");
            return;
        }

        Module newModule = new Module(nomModule, selectedProgramme);
        moduleService.add(newModule);

        moduleList.add(newModule);

        txt_nomModule.clear();
        programmeComboBox.getSelectionModel().clearSelection();

        showAlert(AlertType.INFORMATION, "Module ajouté", "Le module a été ajouté avec succès !");
    }

    @FXML
    public void update() {
        Module selectedModule = modulesTable.getSelectionModel().getSelectedItem();

        if (selectedModule == null) {
            return;
        }

        String newNomModule = txt_nomModule.getText();
        ProgrammeEtude newProgramme = programmeComboBox.getValue();

        if (newNomModule.isEmpty() || newProgramme == null) {
            return;
        }

        selectedModule.setNomModule(newNomModule);
        selectedModule.setProgramme(newProgramme);

        moduleService.update(selectedModule);

        moduleList.setAll(moduleService.getAll());

        txt_nomModule.clear();
        programmeComboBox.getSelectionModel().clearSelection();

        showAlert(AlertType.INFORMATION, "Module mis à jour", "Le module a été mis à jour avec succès !");
    }

    @FXML
    public void delete() {
        Module selectedModule = modulesTable.getSelectionModel().getSelectedItem();

        if (selectedModule == null) {
            return;
        }

        boolean confirmDelete = showConfirmationAlert("Confirmation de suppression", "Êtes-vous sûr de vouloir supprimer le module sélectionné ?");

        if (confirmDelete) {
            try {
                moduleList.remove(selectedModule);

                txt_nomModule.clear();
                programmeComboBox.getSelectionModel().clearSelection();

                showAlert(AlertType.INFORMATION, "Module supprimé", "Le module a été supprimé avec succès !");
            } catch (NoDataFoundException e) {
                showAlert(AlertType.ERROR, "Échec de la suppression", "Impossible de supprimer le module.");
            }
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}
