package tn.eduVision.GUI;

import javafx.beans.property.SimpleFloatProperty;
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
import tn.eduVision.entités.Admin;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Module;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.services.MatiereService;
import tn.eduVision.services.ModuleService;

public class GestionMatieresController {

    @FXML
    private TableView<Matiere> MatieresTable;

    @FXML
    private TableColumn<Matiere, String> nom_col;

    @FXML
    private TableColumn<Matiere, Module> module_col;

    @FXML
    private TableColumn<Matiere, Float> coef_col;

    @FXML
    private TextField txt_nomMatiere;

    @FXML
    private TextField txt_coef;

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
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Accès refusé");
    alert.setHeaderText(null);
    alert.setContentText("Accès refusé. Vous devez être un administrateur pour accéder à cette fonctionnalité.");
    alert.showAndWait();
}

    public void initialize() {

        nom_col.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
        module_col.setCellValueFactory(new PropertyValueFactory<>("module"));
        coef_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Matiere, Float>, ObservableValue<Float>>() {
            @Override
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<Matiere, Float> param) {
                Matiere matiere = param.getValue();
                String nomMatiere = matiere.getNomMatiere();
                float coef = matiereService.getCoefMatiere(nomMatiere);
                return new SimpleFloatProperty(coef).asObject();
            }
        });

        loadMatieresData();
        loadModuleNames();

        MatieresTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txt_nomMatiere.setText(newSelection.getNomMatiere());
                        String nomMatiere = newSelection.getNomMatiere();

 float coef = (float) matiereService.getCoefMatiere(nomMatiere);
        txt_coef.setText(Float.toString(coef));
        modulesComboBox.getSelectionModel().select(newSelection.getModule());
            } else {
                txt_nomMatiere.clear();
                txt_coef.clear();
                modulesComboBox.getSelectionModel().clearSelection();
            }
        });
    }

    private void loadModuleNames() {
        try {
            ObservableList<Module> moduleList = FXCollections.observableArrayList(moduleService.getAll());
            modulesComboBox.setItems(moduleList);

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
            MatieresTable.setItems(matiereData);
        } catch (NoDataFoundException e) {
            e.printStackTrace();
        }
    }
@FXML
private void add() {
    String nomMatiere = txt_nomMatiere.getText();
    String coefText = txt_coef.getText();
    Module selectedModule = modulesComboBox.getValue();

    if (!nomMatiere.isEmpty() && !coefText.isEmpty() && selectedModule != null) {
        try {
            float coef = Float.parseFloat(coefText);

            // Check if the matiere name already exists
            if (isMatiereNameUnique(nomMatiere)) {
                Matiere matiere = new Matiere();
                matiere.setNomMatiere(nomMatiere);
                matiere.setCoef(coef);
                matiere.setModule(selectedModule);

                matiereService.add(matiere);

                txt_nomMatiere.clear();
                txt_coef.clear();
                modulesComboBox.getSelectionModel().clearSelection();

                loadMatieresData();
                showPopup("Matière ajoutée avec succès!");
            } else {
                showPopup("Le nom de la matière existe déjà!");
            }
        } catch (NumberFormatException e) {
            showPopup("Le coefficient doit être un nombre valide!");
        }
    } else {
        showPopup("Veuillez remplir tous les champs!");
    }
}

private boolean isMatiereNameUnique(String nomMatiere) {
    for (Matiere matiere : matiereData) {
        if (matiere.getNomMatiere().equalsIgnoreCase(nomMatiere)) {
            return false;
        }
    }
    return true;
}

    @FXML
    private void update() {
        Matiere selectedMatiere = MatieresTable.getSelectionModel().getSelectedItem();
        if (selectedMatiere != null) {
            String nomMatiere = txt_nomMatiere.getText();
            Module selectedModule = modulesComboBox.getValue();
            String coefText = txt_coef.getText();

            if (!nomMatiere.isEmpty() && selectedModule != null && !coefText.isEmpty()) {
                try {
                    float coef = Float.parseFloat(coefText);

                    selectedMatiere.setNomMatiere(nomMatiere);
                    selectedMatiere.setModule(selectedModule);
                    selectedMatiere.setCoef(coef);

                    matiereService.updateMatiere(selectedMatiere);

                    txt_nomMatiere.clear();
                    txt_coef.clear();
                    modulesComboBox.getSelectionModel().clearSelection();

                    loadMatieresData();
                    showPopup("Matière mise à jour avec succès!");
                } catch (NumberFormatException e) {
                    showPopup("Le coefficient doit être un nombre valide!");
                }
            } else {
                showPopup("Veuillez remplir tous les champs!");
            }
        } else {
            showPopup("Veuillez sélectionner une matière à mettre à jour!");
        }
    }

    @FXML
    private void delete() {
        Matiere selectedMatiere = MatieresTable.getSelectionModel().getSelectedItem();

        if (selectedMatiere != null) {
            matiereService.delete(selectedMatiere);

            txt_nomMatiere.clear();
            txt_coef.clear();
            modulesComboBox.getSelectionModel().clearSelection();

            loadMatieresData();
        }
    }

    private void showPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
