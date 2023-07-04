package tn.eduVision.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Note;
import tn.eduVision.services.MatiereService;
import tn.eduVision.services.NoteManagementService;
import tn.eduVision.services.UserServices;

public class NoteManagementController {

    @FXML
    private ComboBox<Etudiant> cmbEtudiants;

    @FXML
    private ComboBox<Matiere> cmbMatieres;

    @FXML
    private TextField txtNote;

    @FXML
    private Button btnSaisirNote;

    @FXML
    private Button btnUpdateNote;

    @FXML
    private Button btnDeleteNote;

    @FXML
    private TableView<Note> tblNotes;

    @FXML
    private TableColumn<Note, String> colNomEtudiant;

    @FXML
    private TableColumn<Note, String> colMatiere;

    @FXML
    private TableColumn<Note, Float> colNote;

    private NoteManagementService noteManagementService;
    private UserServices userServices;
    private MatiereService matiereService;

    private ObservableList<Etudiant> etudiantList;
    private ObservableList<Matiere> matiereList;
    private ObservableList<Note> noteList;

    public NoteManagementController() {
        noteManagementService = new NoteManagementService();
        userServices = new UserServices();
        matiereService = new MatiereService();
    }

    @FXML
    private void initialize() {
        colNomEtudiant.setCellValueFactory(cellData -> {
            String nom = cellData.getValue().getUtilisateur().getNom();
            return new SimpleStringProperty(nom);
        });
        colMatiere.setCellValueFactory(cellData -> cellData.getValue().getMatiere().getNomProperty());
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        // Initialize the lists for ComboBoxes
        etudiantList = FXCollections.observableArrayList(userServices.getEtudiants());
        matiereList = FXCollections.observableArrayList(matiereService.getAll());

        // Set the ComboBox items
        cmbEtudiants.setItems(etudiantList);
        cmbMatieres.setItems(matiereList);

        // Set the table items
        noteList = FXCollections.observableArrayList(noteManagementService.getAllNotes());
        tblNotes.setItems(noteList);

        // Update the ComboBox selection when a table row is clicked
        tblNotes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cmbEtudiants.getSelectionModel().select(newSelection.getEtudiant());
                cmbMatieres.getSelectionModel().select(newSelection.getMatiere());
                txtNote.setText(Float.toString(newSelection.getNote()));
            }
        });

        // Set the first value in cmbEtudiants
        if (!etudiantList.isEmpty()) {
            cmbEtudiants.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void saisirNote() {
        Etudiant etudiant = cmbEtudiants.getValue();
        Matiere matiere = cmbMatieres.getValue();
        float note = Float.parseFloat(txtNote.getText());

        if (validateNoteRange(note)) {
            // Check if the note already exists for the selected matière
            if (!noteManagementService.isNoteExists(etudiant, matiere)) {
                noteManagementService.saisirNote(etudiant, matiere, note);
                showInfoAlert("Note Saisie", "La note a été saisie avec succès.");
                refreshTable();
                clearFields();
            } else {
                showErrorAlert("Erreur de Saisie", "La note existe déjà pour cette matière.");
            }
        } else {
            showErrorAlert("Erreur de Saisie", "La note doit être comprise entre 1 et 20.");
        }
    }

    @FXML
    private void updateNote() {
        Note selectedNote = tblNotes.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            float newNote = Float.parseFloat(txtNote.getText());

            if (validateNoteRange(newNote)) {
                noteManagementService.modifierNote(selectedNote.getIdNote(), newNote);
                showInfoAlert("Note Modifiée", "La note a été modifiée avec succès.");
                refreshTable();
                clearFields();
            } else {
                showErrorAlert("Erreur de Modification", "La note doit être comprise entre 1 et 20.");
            }
        }
    }

    @FXML
    private void deleteNote() {
        Note selectedNote = tblNotes.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            noteManagementService.supprimerNote(selectedNote.getIdNote());
            showInfoAlert("Note Supprimée", "La note a été supprimée avec succès.");
            refreshTable();
            clearFields();
        }
    }

    private void refreshTable() {
        noteList.clear();
        noteList.addAll(noteManagementService.getAllNotes());

        // Clear the current selection
        tblNotes.getSelectionModel().clearSelection();
    }

    private void clearFields() {
        cmbEtudiants.getSelectionModel().clearSelection();
        cmbMatieres.getSelectionModel().clearSelection();
        txtNote.clear();
    }

    private boolean validateNoteRange(float note) {
        return note >= 1 && note <= 20;
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
