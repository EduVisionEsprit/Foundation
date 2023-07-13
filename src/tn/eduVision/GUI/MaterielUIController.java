/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.eduVision.entités.Materiel;
import tn.eduVision.services.MaterielService;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import tn.eduVision.entités.TypeRessource;
/**
 * FXML Controller class
 *
 * @author job_j
 */
public class MaterielUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private final MaterielService _MaterielServiceInstance = new MaterielService();
    @FXML
    private TableView<Materiel> materielTable;
    
    @FXML
    private TableColumn<Materiel, String> nomMaterielColumn;
    
    @FXML
    private TableColumn<Materiel, String> typeRessourceColumn;
    
    @FXML
    private TableColumn<Materiel, Integer> quantiteMaterielColumn;
    
    private ObservableList<Materiel> materielData;
    
    private  List<Materiel> materiels = _MaterielServiceInstance.getAll();
    
    @FXML
    private void handleInsertButtonClicked(ActionEvent event) {
        
        showInsertPopup();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomMaterielColumn.setCellValueFactory(new PropertyValueFactory<>("nomMateriel"));
        typeRessourceColumn.setCellValueFactory(new PropertyValueFactory<>("typeRessource"));
        quantiteMaterielColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        materielData = FXCollections.observableArrayList(materiels);
       
        materielTable.setItems(materielData);
        materielTable.setRowFactory(tv -> {
        javafx.scene.control.TableRow<Materiel> row = new javafx.scene.control.TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !row.isEmpty()) {
                Materiel selectedMateriel = row.getItem();
                showReservationPopup(selectedMateriel);
            }
        });
        return row;
    });
    }
    
 
    
    @FXML
    private void handleRowClicked(ActionEvent event) {
        Materiel selectedMateriel = materielTable.getSelectionModel().getSelectedItem();
        
        if (selectedMateriel != null) {
            showReservationPopup(selectedMateriel);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Materiel Selected");
            alert.setContentText("Please select a materiel from the table.");
            alert.showAndWait();
        }
    }
    
    private void showReservationPopup(Materiel materiel) {
     
 
    Dialog<Pair<String, Integer>> dialog = new Dialog<>();
    dialog.setTitle("update");
    dialog.setHeaderText("update " + materiel.getNomMateriel());


    ButtonType updateButtonType = new ButtonType("Update");
    dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);


    TextField nomMaterielField = new TextField(materiel.getNomMateriel());
    ChoiceBox<TypeRessource> typeRessourceField = new ChoiceBox<>();
    typeRessourceField.getItems().addAll(TypeRessource.values());
    typeRessourceField.setValue(materiel.getTypeRessource());
    TextField quantiteMaterielField = new TextField(String.valueOf(materiel.getQuantite()));

    GridPane grid = new GridPane();
    grid.add(new Label("Nom Materiel:"), 0, 0);
    grid.add(nomMaterielField, 1, 0);
    grid.add(new Label("Type Ressource:"), 0, 1);
    grid.add(typeRessourceField, 1, 1);
    grid.add(new Label("Quantite Materiel:"), 0, 2);
    grid.add(quantiteMaterielField, 1, 2);

    Node updateButton = dialog.getDialogPane().lookupButton(updateButtonType);
    updateButton.setDisable(true);
    updateButton.disableProperty().bind(Bindings.createBooleanBinding(
        () -> nomMaterielField.getText().isEmpty() || typeRessourceField.getValue() == null
                || quantiteMaterielField.getText().isEmpty(),
        nomMaterielField.textProperty(), typeRessourceField.valueProperty(),
        quantiteMaterielField.textProperty()));

dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == updateButtonType) {
            return new Pair<>(nomMaterielField.getText(), Integer.parseInt(quantiteMaterielField.getText()));
        }
        return null;
    });

    Optional<Pair<String, Integer>> result = dialog.showAndWait();
    result.ifPresent(pair -> {
        
        try{
        int newQuantity = Integer.parseInt(quantiteMaterielField.getText());
        }
        catch(NumberFormatException ex){
            Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Unsupported Operation");
                alert.setHeaderText("Alert");
                alert.setContentText("Quentity must be integer");
                alert.showAndWait();
        }
        Materiel MatrielUpdated = new Materiel(nomMaterielField.getText(), materiel.getIdRessource(), typeRessourceField.getValue(), Integer.parseInt(quantiteMaterielField.getText()));        
        String updatedNomMateriel = pair.getKey();
        int updatedQuantiteMateriel = pair.getValue();
        _MaterielServiceInstance.update(MatrielUpdated);
        refreshGridValues();
        
    });
    }
    private void showInsertPopup() {
    Dialog<Pair<String, Integer>> dialog = new Dialog<>();
    dialog.setTitle("Insert");
    dialog.setHeaderText("Insert New Materiel");

    ButtonType insertButtonType = new ButtonType("Insert", ButtonData.APPLY);
    dialog.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);

    TextField nomMaterielField = new TextField();
    ChoiceBox<TypeRessource> typeRessourceField = new ChoiceBox<>();
    typeRessourceField.getItems().addAll(TypeRessource.Materiel);
    TextField quantiteMaterielField = new TextField();

    VBox content = new VBox();
    content.getChildren().addAll(
            new Label("Nom Materiel:"), nomMaterielField,
            new Label("Type Ressource:"), typeRessourceField,
            new Label("Quantite Materiel:"), quantiteMaterielField
    );

    dialog.getDialogPane().setContent(content);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == insertButtonType) {
            // Handle Insert button click
            String nomMateriel = nomMaterielField.getText();
            int quantiteMateriel = Integer.parseInt(quantiteMaterielField.getText());
            
            return new Pair<>(nomMateriel, quantiteMateriel);
        }
        return null;
    });
    content.setPrefWidth(400);
    dialog.showAndWait().ifPresent(pair -> {
        String nomMateriel = pair.getKey();
        int quantiteMateriel = pair.getValue();
        if(nomMateriel != null && quantiteMateriel > 0 && typeRessourceField.getValue() != null){
        
            Materiel matToInsert = new Materiel(nomMateriel, -1, typeRessourceField.getValue(), quantiteMateriel);
            _MaterielServiceInstance.add(matToInsert);
            refreshGridValues();
        } else{
             Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Unsupported Operation");
                alert.setHeaderText("Alert");
                alert.setContentText("All filds must be set and no negative or zero is accepted");
                alert.showAndWait();
        }
    });
}


    private void refreshGridValues() {
        materielData.clear();
        List<Materiel> updatedMateriels = _MaterielServiceInstance.getAll();
        materielData.addAll(updatedMateriels);
        materielTable.refresh();
    }

    
}
