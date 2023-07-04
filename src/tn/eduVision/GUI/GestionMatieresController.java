package tn.eduVision.GUI;

import javafx.beans.property.SimpleDoubleProperty;
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
    private TableColumn<Matiere, Module> module_col;

    @FXML
    private TableColumn<Matiere, Double> coef_col;

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

    public void initialize() {
         
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
        module_col.setCellValueFactory(new PropertyValueFactory<>("module"));
        coef_col.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCoef()).asObject());

         
        loadMatieresData();

         
        loadModuleNames();

         
        MatieresTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 
                txt_nomMatiere.setText(newSelection.getNomMatiere());
                txt_coef.setText(Double.toString(newSelection.getCoef()));
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
        String coefText = txt_coef.getText();
        Module selectedModule = modulesComboBox.getValue();

        if (!nomMatiere.isEmpty() && !coefText.isEmpty() && selectedModule != null) {
            try {
                double coef = Double.parseDouble(coefText);
                Matiere matiere = new Matiere();
                matiere.setNomMatiere(nomMatiere);
                matiere.setCoef((float) coef);
                matiere.setModule(selectedModule);

                matiereService.add(matiere);

                 
                txt_nomMatiere.clear();
                txt_coef.clear();
                modulesComboBox.getSelectionModel().clearSelection();

                 
                loadMatieresData();
                showPopup("Matière ajoutée avec succès!");
            } catch (NumberFormatException e) {
                showPopup("Le coefficient doit être un nombre valide!");
            }
        } else {
             
            showPopup("Veuillez remplir tous les champs!");
        }
    }

    @FXML
    private void update() {
        Matiere selectedMatiere = MatieresTable.getSelectionModel().getSelectedItem();

        if (selectedMatiere != null) {
            String newNomMatiere = txt_nomMatiere.getText();
            String newCoefText = txt_coef.getText();
            Module newModule = modulesComboBox.getValue();

            if (!newNomMatiere.isEmpty() && !newCoefText.isEmpty() && newModule != null) {
                try {
                    double newCoef = Double.parseDouble(newCoefText);
                    selectedMatiere.setNomMatiere(newNomMatiere);
                    selectedMatiere.setCoef((float) newCoef);
                    selectedMatiere.setModule(newModule);

                    matiereService.update(selectedMatiere);

                     
                    txt_nomMatiere.clear();
                    txt_coef.clear();
                    modulesComboBox.getSelectionModel().clearSelection();

                     
                    loadMatieresData();
                } catch (NumberFormatException e) {
                    showPopup("Le coefficient doit être un nombre valide!");
                }
            } else {
                 
                showPopup("Veuillez remplir tous les champs!");
            }
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
