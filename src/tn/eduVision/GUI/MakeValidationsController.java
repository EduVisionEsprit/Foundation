package tn.eduVision.GUI;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.eduVision.entités.Etat;
import tn.eduVision.entités.Reservation;
import tn.eduVision.services.ResvationMaterielService;
import tn.eduVision.tools.CustomLogger;

public class MakeValidationsController implements Initializable {

    private final ResvationMaterielService _reservationServiceInstance = new ResvationMaterielService();
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private VBox buttonContainer;

    @FXML
    private TableView<Reservation> materielTable;
    @FXML
    private TableColumn<Reservation, String> ressource;
    @FXML
    private TableColumn<Reservation, String> etat;
    @FXML
    private TableColumn<Reservation, String> date;
    @FXML
    private TableColumn<Reservation, String> heureDebut;
    @FXML
    private TableColumn<Reservation, String> heureFin;
    @FXML
    private Button insertButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configure table columns to bind with Reservation object properties
        ressource.setCellValueFactory(new PropertyValueFactory<>("ressource"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        heureDebut.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
        heureFin.setCellValueFactory(new PropertyValueFactory<>("heureFin"));

        List<Reservation> reservations = _reservationServiceInstance.getAll(true)
                .stream()
                .filter(r -> r.getEtat() == Etat.attente)
                .sorted(Comparator.comparing(Reservation::getDateReservation))
                .collect(Collectors.toList());

        materielTable.getItems().addAll(reservations);
        materielTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                addValidationButtons();
            }
        });
    }

    @FXML
    private void handleInsertButtonClicked() {
        // Handle insert button click event
    }

    private void addValidationButtons() {
    Reservation selectedReservation = materielTable.getSelectionModel().getSelectedItem();

    if (selectedReservation != null) {
        Button validerButton = new Button("Valider");
        validerButton.setPrefWidth(100);
        validerButton.setOnAction((ActionEvent event) -> {
            handleValiderButtonClicked(selectedReservation);
        });

        Button refuserButton = new Button("Refuser");
        refuserButton.setPrefWidth(100);
        refuserButton.setOnAction((ActionEvent event) -> {
            handleRefuserButtonClicked(selectedReservation);
        });

        if (buttonContainer == null) {
            buttonContainer = new VBox(10);
            ((VBox) materielTable.getParent()).getChildren().add(buttonContainer);
        } else {
            buttonContainer.getChildren().clear();
        }

        HBox buttonRow = new HBox(10);
        buttonRow.getChildren().addAll(validerButton, refuserButton);
        buttonContainer.getChildren().add(buttonRow);
        buttonContainer.setVisible(true);
    }
}

    private void handleValiderButtonClicked(Reservation reservation) {
        try {
            _reservationServiceInstance.makeReservationVlidations(Etat.confirme, reservation);
            refreshGrid();
            _logger.log(Level.INFO, "reservation with id : {0} has been approuved", reservation.getIdReservation());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Reservation with id: " + reservation.getIdReservation() + " and resource: " + reservation.getRessource() + " has been confirmed.");
            alert.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(MakeValidationsController.class.getName()).log(Level.SEVERE, "error validating the reservation", ex);
        }
    }

    private void handleRefuserButtonClicked(Reservation reservation) {
        try {
            _reservationServiceInstance.makeReservationVlidations(Etat.refuse, reservation);
            refreshGrid();
            _logger.log(Level.INFO, "reservation with id : {0} has been refused", reservation.getIdReservation());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Refus");
            alert.setHeaderText(null);
            alert.setContentText("Reservation with id: " + reservation.getIdReservation() + " with resource: " + reservation.getRessource() + " has been refused.");
            alert.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(MakeValidationsController.class.getName()).log(Level.SEVERE, "error refusing the reservation", ex);
        }
    }
    
    public void refreshGrid() {
    List<Reservation> reservations = _reservationServiceInstance.getAll(true)
            .stream()
            .filter(r -> r.getEtat() == Etat.attente)
            .sorted(Comparator.comparing(Reservation::getDateReservation))
            .collect(Collectors.toList());
    materielTable.getSelectionModel().clearSelection();
    materielTable.getItems().clear();
    materielTable.getItems().addAll(reservations);
    if (!reservations.isEmpty()) {
        materielTable.getSelectionModel().selectFirst();
    }
}
}
