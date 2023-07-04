package tn.eduVision.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;
import tn.eduVision.entités.Module;
import tn.eduVision.entités.ProgrammeEtude;
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

         
        programmeComboBox.setCellFactory(param -> new ListCell<ProgrammeEtude>() {
            @Override
            protected void updateItem(ProgrammeEtude programme, boolean empty) {
                super.updateItem(programme, empty);
                if (empty || programme == null) {
                    setText(null);
                } else {
                    setText(programme.getDescription());
                }
            }
        });

        programmeComboBox.setConverter(new StringConverter<ProgrammeEtude>() {
            @Override
            public String toString(ProgrammeEtude programme) {
                if (programme == null) {
                    return "";
                } else {
                    return programme.getDescription();
                }
            }

            @Override
            public ProgrammeEtude fromString(String string) {
                 
                return null;
            }
        });

         
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

    @FXML
    public void add() {
        String nomModule = txt_nomModule.getText();
        ProgrammeEtude selectedProgramme = programmeComboBox.getValue();

        if (nomModule.isEmpty() || selectedProgramme == null) {
             
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter a module name and select a programme.");
            return;
        }

        Module newModule = new Module(nomModule, selectedProgramme);
        moduleService.add(newModule);

        moduleList.add(newModule);

         
        txt_nomModule.clear();
        programmeComboBox.getSelectionModel().clearSelection();

         
        showAlert(AlertType.INFORMATION, "Module Added", "Module has been added successfully!");
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
}
    @FXML
    public void delete() {
        Module selectedModule = modulesTable.getSelectionModel().getSelectedItem();

        if (selectedModule == null) {
             
            return;
        }

        moduleService.delete(selectedModule);

        moduleList.remove(selectedModule);
    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
