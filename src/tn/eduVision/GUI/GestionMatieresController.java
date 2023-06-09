package tn.eduVision.GUI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Module;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.services.MatiereService;
import tn.eduVision.services.ModuleService;

public class GestionMatieresController {

    @FXML
    private TableView<Matiere> MatieresTable;

    @FXML
    private TableColumn<Matiere, String> nom_col;

    @FXML
    private TableColumn<Matiere, Module> Module_col;

    @FXML
    private TextField txt_nomMatiere;

    @FXML
    private ComboBox<Module> modulesComboBox;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    private ObservableList<Matiere> matiereData = FXCollections.observableArrayList();

    private MatiereService matiereService = new MatiereService();
    private ModuleService moduleService = new ModuleService();

 public void initialize() {
    // Set up table columns
    nom_col.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
    Module_col.setCellValueFactory(new PropertyValueFactory<>("module"));

    // Load matieres data
    loadMatieresData();

    // Load module names into the modulesComboBox
    loadModuleNames();

    // Add listener to table selection
    MatieresTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Set selected matiere values in the input fields
            txt_nomMatiere.setText(newSelection.getNomMatiere());
            modulesComboBox.getSelectionModel().select(newSelection.getModule());
        } else {
            // Clear input fields
            txt_nomMatiere.clear();
            modulesComboBox.getSelectionModel().clearSelection();
        }
    });
}









private void loadModuleNames() {
    try {
        ObservableList<Module> moduleList = FXCollections.observableArrayList(moduleService.getAll());
        modulesComboBox.setItems(moduleList);

        // Set the cell factory and converter for the combo box
        modulesComboBox.setCellFactory(param -> new ListCell<Module>() {
            @Override
            protected void updateItem(Module module, boolean empty) {
                super.updateItem(module, empty);
                if (empty || module == null) {
                    setText(null);
                } else {
                    setText(module.getNomModule());
                }
            }
        });

        modulesComboBox.setConverter(new StringConverter<Module>() {
            @Override
            public String toString(Module module) {
                if (module == null) {
                    return "";
                } else {
                    return module.getNomModule();
                }
            }

            @Override
            public Module fromString(String string) {
                // Not needed for this scenario
                return null;
            }
        });
    } catch (NoDataFoundException e) {
        e.printStackTrace();
    }
}


   private void loadMatieresData() {
    try {
        matiereData = FXCollections.observableArrayList(matiereService.getAll());
        
        // Update the module names in the matiereData list
        for (Matiere matiere : matiereData) {
            String name = matiere.getNomMatiere();
            if (name != null) {
                matiere.setNomMatiere(name);
            }
        }
        
        MatieresTable.setItems(matiereData);
    } catch (NoDataFoundException e) {
        e.printStackTrace();
    }
}
@FXML
private void add() {
    String nomMatiere = txt_nomMatiere.getText();
    Module selectedModule = modulesComboBox.getValue();

    if (nomMatiere != null && selectedModule != null) {
        Matiere matiere = new Matiere();
        matiere.setNomMatiere(nomMatiere);
        matiere.setModule(selectedModule);

        matiereService.add(matiere);

        // Clear input fields
        txt_nomMatiere.clear();
        modulesComboBox.getSelectionModel().clearSelection();

        // Refresh table data and display popup
        loadMatieresData();
        showPopup("Matière ajoutée avec succès!");

    } else {
        // Display an error popup if the input fields are not filled
        showPopup("Veuillez remplir tous les champs!");
    }
}

private void showPopup(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}



    @FXML
    private void update() {
        Matiere selectedMatiere = MatieresTable.getSelectionModel().getSelectedItem();

        if (selectedMatiere != null) {
            String newNomMatiere = txt_nomMatiere.getText();
            Module newModule = modulesComboBox.getValue();

            selectedMatiere.setNomMatiere(newNomMatiere);
            selectedMatiere.setModule(newModule);

            matiereService.update(selectedMatiere);

            // Clear input fields
            txt_nomMatiere.clear();
            modulesComboBox.getSelectionModel().clearSelection();

            // Refresh table data
            loadMatieresData();
        }
    }

    @FXML
    private void delete() {
        Matiere selectedMatiere = MatieresTable.getSelectionModel().getSelectedItem();

        if (selectedMatiere != null) {
            matiereService.delete(selectedMatiere);

            // Clear input fields
            txt_nomMatiere.clear();
            modulesComboBox.getSelectionModel().clearSelection();

            // Refresh table data
            loadMatieresData();
        }
    }

}
