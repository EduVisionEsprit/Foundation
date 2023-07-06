package tn.eduVision.GUI;

import java.awt.Choice;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
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
import tn.eduVision.entités.Salle;
import tn.eduVision.services.SallesService;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import tn.eduVision.entités.TypeSalle;

public class SalleController implements Initializable {

    private final SallesService _SalleServiceInstance = new SallesService();

    @FXML
    private TableView<Salle> salleTable;

    @FXML
    private TableColumn<Salle, String> nomSalleColumn;

    @FXML
    private TableColumn<Salle, Integer> capaciteSalleColumn;
    
    @FXML
    private TableColumn<Salle, TypeSalle> typesalle;

    private ObservableList<Salle> salleData;

    private List<Salle> salles = _SalleServiceInstance.getAll();

    @FXML
    private void handleInsertButtonClicked(ActionEvent event) {
        showInsertPopup();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomSalleColumn.setCellValueFactory(new PropertyValueFactory<>("nomSalle"));
        capaciteSalleColumn.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        typesalle.setCellValueFactory(new PropertyValueFactory<>("typeSalle"));
        salleData = FXCollections.observableArrayList(salles);

        salleTable.setItems(salleData);
        salleTable.setRowFactory(tv -> {
        javafx.scene.control.TableRow<Salle> row = new javafx.scene.control.TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !row.isEmpty()) {
                Salle selectedMateriel = row.getItem();
                showReservationPopup(selectedMateriel);
            }
        });
        return row;
    });
        
    }

    @FXML
    private void handleRowClicked(ActionEvent event) {
        Salle selectedSalle = salleTable.getSelectionModel().getSelectedItem();

        if (selectedSalle != null) {
            showReservationPopup(selectedSalle);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Salle Selected");
            alert.setContentText("Please select a salle from the table.");
            alert.showAndWait();
        }
    }

    private void showReservationPopup(Salle salle) {
                Dialog<Salle> dialog = new Dialog<>();
        dialog.setTitle("Update");
        dialog.setHeaderText("update salle :" + salle.getNomSalle());

        ButtonType insertButtonType = new ButtonType("Update", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);

        TextField nomSalleField = new TextField();
        TextField capaciteSalleField = new TextField();
        ChoiceBox<TypeSalle> typeSalle = new ChoiceBox<>();
        typeSalle.getItems().addAll(TypeSalle.values());
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label nomSalleLabel = new Label("Nom Salle:");
        GridPane.setConstraints(nomSalleLabel, 0, 0);
        grid.getChildren().add(nomSalleLabel);

        GridPane.setConstraints(nomSalleField, 1, 0);
        grid.getChildren().add(nomSalleField);

        Label capaciteSalleLabel = new Label("Capacite Salle:");
        GridPane.setConstraints(capaciteSalleLabel, 0, 1);
        grid.getChildren().add(capaciteSalleLabel);

        GridPane.setConstraints(capaciteSalleField, 1, 1);
        grid.getChildren().add(capaciteSalleField);

        Label typeSalleLabel = new Label("Type Salle:");
        GridPane.setConstraints(typeSalleLabel, 0, 2);
        grid.getChildren().add(typeSalleLabel);

        GridPane.setConstraints(typeSalle, 1, 2);
        grid.getChildren().add(typeSalle);

        Node insertButton = dialog.getDialogPane().lookupButton(insertButtonType);
        insertButton.setDisable(true);
        insertButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> nomSalleField.getText().isEmpty() || capaciteSalleField.getText().isEmpty(),
                nomSalleField.textProperty(), capaciteSalleField.textProperty()));

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == insertButtonType) {
                return new Salle(nomSalleField.getText(),
                        Integer.parseInt(capaciteSalleField.getText()),
                        null,
                        null,
                        TypeSalle.Amphi,
                        0);
            }
            return null;
        });


        Optional<Salle> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String nomSalle = pair.getNomSalle() ;
            int capaciteSalle = pair.getCapacite();
            TypeSalle type = pair.getTypeSalle();

            if (nomSalle != null && capaciteSalle > 0) {
                Salle salleToInsert = new Salle(nomSalle, capaciteSalle, null, null, type, -1);
                _SalleServiceInstance.update(salleToInsert);
                refreshGridValues();
            }
        });
    }

    private void showInsertPopup() {
        Dialog<Salle> dialog = new Dialog<>();
        dialog.setTitle("Insert");
        dialog.setHeaderText("Insert New Salle");

        ButtonType insertButtonType = new ButtonType("Insert", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);

        TextField nomSalleField = new TextField();
        TextField capaciteSalleField = new TextField();
        ChoiceBox<TypeSalle> typeSalle = new ChoiceBox<>();
        typeSalle.getItems().addAll(TypeSalle.values());
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label nomSalleLabel = new Label("Nom Salle:");
        GridPane.setConstraints(nomSalleLabel, 0, 0);
        grid.getChildren().add(nomSalleLabel);

        GridPane.setConstraints(nomSalleField, 1, 0);
        grid.getChildren().add(nomSalleField);

        Label capaciteSalleLabel = new Label("Capacite Salle:");
        GridPane.setConstraints(capaciteSalleLabel, 0, 1);
        grid.getChildren().add(capaciteSalleLabel);

        GridPane.setConstraints(capaciteSalleField, 1, 1);
        grid.getChildren().add(capaciteSalleField);

        Label typeSalleLabel = new Label("Type Salle:");
        GridPane.setConstraints(typeSalleLabel, 0, 2);
        grid.getChildren().add(typeSalleLabel);

        GridPane.setConstraints(typeSalle, 1, 2);
        grid.getChildren().add(typeSalle);

        Node insertButton = dialog.getDialogPane().lookupButton(insertButtonType);
        insertButton.setDisable(true);
        insertButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> nomSalleField.getText().isEmpty() || capaciteSalleField.getText().isEmpty(),
                nomSalleField.textProperty(), capaciteSalleField.textProperty()));

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == insertButtonType) {
                return new Salle(nomSalleField.getText(),
                        Integer.parseInt(capaciteSalleField.getText()),
                        null,
                        null,
                        TypeSalle.Amphi,
                        0);
            }
            return null;
        });


        Optional<Salle> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String nomSalle = pair.getNomSalle() ;
            int capaciteSalle = pair.getCapacite();
            TypeSalle type = pair.getTypeSalle();

            if (nomSalle != null && capaciteSalle > 0) {
                Salle salleToInsert = new Salle(nomSalle, capaciteSalle, null, null, type, -1);
                _SalleServiceInstance.add(salleToInsert);
                refreshGridValues();
            }
        });
    }

    private void refreshGridValues() {
        salles = _SalleServiceInstance.getAll();
        salleData.setAll(salles);
    }
}
