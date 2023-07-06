package tn.eduVision.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.eduVision.entités.Admin;
import tn.eduVision.entités.Enseignant;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Note;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.Utilisateur;
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
         Utilisateur  currentUser = getCurrentUser(); 
    if (!noteManagementService.isEnseignant(currentUser)) {
        showAccessDeniedAlert();
        return;
    }
 
        colNomEtudiant.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUtilisateur().getNom()));
        colMatiere.setCellValueFactory(cellData -> cellData.getValue().getMatiere().getNomProperty());
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        etudiantList = FXCollections.observableArrayList(userServices.getEtudiants());
        matiereList = FXCollections.observableArrayList(matiereService.getAll());

        cmbEtudiants.setItems(etudiantList);
        cmbMatieres.setItems(matiereList);

        noteList = FXCollections.observableArrayList(noteManagementService.getAllNotes());
        tblNotes.setItems(noteList);

        tblNotes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cmbEtudiants.getSelectionModel().select(newSelection.getEtudiant());
                cmbMatieres.getSelectionModel().select(newSelection.getMatiere());
                txtNote.setText(Float.toString(newSelection.getNote()));
            }
        });

        if (!etudiantList.isEmpty()) {
            cmbEtudiants.getSelectionModel().selectFirst();
        }
    }
    //private Utilisateur getCurrentUser() {
    // Implement after Integration
  //  return AuthenticationService.getCurrentUser();
//}
   private Utilisateur getCurrentUser() {
    // Create a dummy user for testing purposes
    Utilisateur dummyUser = new Enseignant(); 
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
    private void saisirNote() {
        Etudiant etudiant = cmbEtudiants.getValue();
        Matiere matiere = cmbMatieres.getValue();
        float note = Float.parseFloat(txtNote.getText());

        if (validateNoteRange(note)) {
            if (!noteManagementService.isNoteExists(etudiant, matiere)) {
                noteManagementService.saisirNote(etudiant, matiere, note);
                showInfoAlert("Note Saisie", "La note a été saisie avec succès.");
                refreshTable();
                clearFields();
            } else {
                showErrorAlert("Erreur de Saisie", "La note existe déjà pour cette matière.");
            }
        } else {
            showErrorAlert("Erreur de Saisie", "La note doit être comprise entre 1 et20.");
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
