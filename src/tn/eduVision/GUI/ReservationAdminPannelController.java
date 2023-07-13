import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import tn.eduVision.entités.Etat;
import tn.eduVision.entités.Reservation;
import tn.eduVision.entités.Ressource;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.services.ResvationMaterielService;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import tn.eduVision.entités.Role;

public class ReservationAdminPannelController implements Initializable {

    private final ResvationMaterielService reservationService = new ResvationMaterielService();
    private List<Reservation> reservations;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, String> utilisateurColumn;

    @FXML
    private TableColumn<Reservation, Date> dateReservationColumn;

    @FXML
    private TableColumn<Reservation, Etat> etatColumn;

    @FXML
    private Button showOptionsButton;

    private static List<Reservation> globalList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utilisateurColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> param) {
                Utilisateur utilisateur = param.getValue().getUtilisateur();
                String userName = utilisateur != null ? utilisateur.getNom() : "";
                return new SimpleStringProperty(userName);
            }
        });

        dateReservationColumn.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));

        globalList.addAll(reservationService.getAll(true));
        globalList.addAll(reservationService.getAll(false));
        reservations = globalList;
        reservationTable.setItems(FXCollections.observableArrayList(reservations));

        reservationTable.setRowFactory(tv -> {
            TableRow<Reservation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Reservation selectedReservation = row.getItem();
                    showOptionsPopup(selectedReservation);
                }
            });
            return row;
        });

        showOptionsButton.setOnAction(event -> {
            Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                showOptionsPopup(selectedReservation);
            }
        });
    }

    private void showOptionsPopup(Reservation reservation) {
        Dialog<Reservation> dialog = new Dialog<>();
        dialog.setTitle("Reservation Options");
        dialog.setHeaderText("Reservation Details");

        ButtonType updateButtonType = new ButtonType("Update");
        ButtonType deleteButtonType = new ButtonType("Delete");
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, deleteButtonType, ButtonType.CANCEL);

        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);

        Label idLabel = new Label("ID Reservation:");
        TextField idValueTextField = new TextField(String.valueOf(reservation.getIdReservation()));
        idValueTextField.setEditable(false);

        Label utilisateurLabel = new Label("Utilisateur:");
        TextField utilisateurValueTextField = new TextField(reservation.getUtilisateur().getNom());

        Label dateReservationLabel = new Label("Date Reservation:");
        DatePicker dateReservationValuePicker = new DatePicker();
        LocalDateTime dateTime = reservation.getDateReservation().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        dateReservationValuePicker.setValue(dateTime.toLocalDate());

        Label etatLabel = new Label("Etat:");
        ChoiceBox<Etat> etatValueChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(Etat.values()));
        etatValueChoiceBox.setValue(reservation.getEtat());

        content.addRow(0, idLabel, idValueTextField);
        content.addRow(1, utilisateurLabel, utilisateurValueTextField);
        content.addRow(2, dateReservationLabel, dateReservationValuePicker);
        content.addRow(3, etatLabel, etatValueChoiceBox);

        Button updateButton = (Button) dialog.getDialogPane().lookupButton(updateButtonType);
        Button deleteButton = (Button) dialog.getDialogPane().lookupButton(deleteButtonType);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);

        updateButton.setOnAction(event -> {
            if (!updateButton.isDisable()) {
                dialog.setResult(reservation);
                dialog.close();
                // Perform update logic here using the updated values
                reservation.setUtilisateur(new Utilisateur(1, "jobrane", "ben salah", "test@gmail.com", null, Role.ADMIN));
                LocalDateTime selectedDateTime = dateReservationValuePicker.getValue().atStartOfDay();
                Date dateReservation = Date.from(selectedDateTime.atZone(ZoneId.systemDefault()).toInstant());
                reservation.setDateReservation(dateReservation);
                reservation.setEtat(etatValueChoiceBox.getValue());
                // Call the update function with the updated reservation
                updateReservation(reservation);
            }
        });

        deleteButton.setOnAction(event -> {
            if (!deleteButton.isDisable()) {
                dialog.setResult(reservation);
                dialog.close();
                showDeleteConfirmation(reservation);
            }
        });

        dialog.getDialogPane().setContent(content);
        dialog.showAndWait().ifPresent(result -> {
            if (result == reservation) {
                // Dialog was closed, no action needed
            }
        });
    }

    private void updateReservation(Reservation reservation) {
        // Perform update logic here
    }

    private void showDeleteConfirmation(Reservation reservation) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete this reservation?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            globalList.remove(reservation);
            refreshTable();
        }
    }

    private void refreshTable() {
        reservations = new ArrayList<>(globalList);
        reservationTable.setItems(FXCollections.observableArrayList(reservations));
    }
}
