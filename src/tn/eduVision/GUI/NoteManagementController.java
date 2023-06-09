import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Note;
import tn.eduVision.services.NoteManagementService;
import tn.eduVision.services.SaisiNotesService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

public class NoteManagementController implements Initializable {

    private TextField txtEtudiantId;
    private TextField txtMatiereId;
    private TextField txtNote;
    @FXML
    private Button btnSaisirNote;
    @FXML
    private ListView<Note> lstNotes;

    private SaisiNotesService noteManagementService;
    @FXML
    private ComboBox<?> cmbEtudiants;
    @FXML
    private ComboBox<?> cmbMatieres;
    @FXML
    private Slider sliderNote;
    @FXML
    private Button btnUpdateNote;
    @FXML
    private Button btnDeleteNote;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noteManagementService = new NoteManagementService();
    }

    @FXML
    private void saisirNote(ActionEvent event) {
        int etudiantId = Integer.parseInt(txtEtudiantId.getText());
        int matiereId = Integer.parseInt(txtMatiereId.getText());
        float note = Float.parseFloat(txtNote.getText());

        Etudiant etudiant = new Etudiant(etudiantId);
        Matiere matiere = new Matiere(matiereId);

        noteManagementService.saisirNote(etudiant, matiere, note);
        refreshNotesList();
    }

    private void refreshNotesList() {
        int etudiantId = Integer.parseInt(txtEtudiantId.getText());
        Etudiant etudiant = new Etudiant(etudiantId);

        List<Note> notes = noteManagementService.obtenirNotesParEtudiant(etudiant);
        lstNotes.getItems().setAll(notes);
    }

    @FXML
    private void updateNote(ActionEvent event) {
    }

    @FXML
    private void deleteNote(ActionEvent event) {
    }
}
