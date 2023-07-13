package tn.eduVision.GUI;
import java.awt.Desktop;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Module;
import tn.eduVision.entités.Note;
import tn.eduVision.services.MatiereService;
import tn.eduVision.services.NoteManagementService;
import tn.eduVision.services.UserServices;
import javafx.stage.FileChooser;
import java.io.File;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tn.eduVision.entités.Admin;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.Utilisateur;

public class NoteEtudiantController implements Initializable {

    @FXML
    private TableView<Note> tblNotes;
    @FXML
    private TableColumn<Note, String> colModule;
    @FXML
    private TableColumn<Note, String> colMatiere;
    @FXML
    private TableColumn<Note, Float> colNote;
    @FXML
    private ComboBox<Matiere> cmbMatieres;

    private NoteManagementService noteManagementService;
    private UserServices userServices;
    private MatiereService matiereService;

    private ObservableList<Note> noteList;
    private Etudiant etudiant;

    public NoteEtudiantController() {
        noteManagementService = new NoteManagementService();
        userServices = new UserServices();
        matiereService = new MatiereService();
    }


    @FXML
    private void afficherNotes() {
        Matiere selectedMatiere = cmbMatieres.getValue();
        if (etudiant != null && selectedMatiere != null) {
            List<Note> notes = noteManagementService.obtenirNotesParEtudiantEtMatiere(etudiant, selectedMatiere);
            if (notes != null) {
                noteList = FXCollections.observableArrayList(notes);
                tblNotes.setItems(noteList);
            }
        }
    }
@Override
public void initialize(URL location, ResourceBundle resources) {
  
    etudiant = (Etudiant) userServices.getEtudiantById(2);
    System.out.println(etudiant.getNom());
    List<Matiere> matieres = matiereService.getAll();
    
     
    colModule.setCellValueFactory(cellData -> {
        Matiere matiere = cellData.getValue().getMatiere();
        Module module = matiereService.getModuleOfMatiere(matiere.getNomMatiere());
        String moduleName = module != null ? module.getNomModule() : "";
        return new SimpleStringProperty(moduleName);
    });

    colMatiere.setCellValueFactory(cellData -> {
        SimpleStringProperty matiereProperty = new SimpleStringProperty(cellData.getValue().getMatiere().getNomMatiere());
        return matiereProperty;
    });

    colNote.setCellValueFactory(cellData -> {
        Float noteValue = cellData.getValue().getNote();
        ObservableValue<Float> observableValue = new SimpleFloatProperty(noteValue).asObject();
        return observableValue;
    });

    // Retrieve and display all notes initially
    List<Note> notes = noteManagementService.getAllNotes();
    if (notes != null) {
        noteList = FXCollections.observableArrayList(notes);
        tblNotes.setItems(noteList);
    }

   
}


    @FXML
    private void generateTranscript() {
        if (noteList != null && !noteList.isEmpty()) {
            StringBuilder transcriptBuilder = new StringBuilder();

            transcriptBuilder.append("Relevé de Notes\n\n");
            transcriptBuilder.append("Etudiant: ").append(etudiant.getNom()).append("\n\n");

            transcriptBuilder.append("Module\tMatiere\tNote\tCoefficient\tMoyenne\tMention\n");

            double totalCoefficient = 0.0;
            double totalMoyenne = 0.0;

            for (Note note : noteList) {
                Matiere matiere = note.getMatiere();
                Module module = matiereService.getModuleOfMatiere(matiere.getNomMatiere());

                double coefficient = matiereService.getCoefMatiere(matiere.getNomMatiere());
                double moyenne = note.getNote() * coefficient;

                totalCoefficient += coefficient;
                totalMoyenne += moyenne;

                transcriptBuilder.append(module.getNomModule()).append("\t");
                transcriptBuilder.append(matiere.getNomMatiere()).append("\t");
                transcriptBuilder.append(note.getNote()).append("\t");
                transcriptBuilder.append(coefficient).append("\t");
                transcriptBuilder.append(moyenne).append("\t");

                if (moyenne >= 10.0) {
                    transcriptBuilder.append("Passable");
                } else {
                    transcriptBuilder.append("Redouble");
                }

                transcriptBuilder.append("\n");
            }

            double moyenneGenerale = totalMoyenne / totalCoefficient;

            transcriptBuilder.append("\nMoyenne générale: ").append(moyenneGenerale).append("\n");

            if (moyenneGenerale >= 10.0) {
                transcriptBuilder.append("Mention: Passable");
            } else {
                transcriptBuilder.append("Mention: Redouble");
            }

            String transcript = transcriptBuilder.toString();
            System.out.println(transcript);

            saveTranscriptAsHtml(transcript);
        }
    }
private void saveTranscriptAsHtml(String transcript) {
    FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("mon_relevée.html"); // Set the default file name

    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
    File file = fileChooser.showSaveDialog(new Stage());
    if (file != null) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>Transcript</title>");
            writer.println("<style>");
            writer.println("body { font-family: Arial, sans-serif; }");
            writer.println("table { border-collapse: collapse; width: 100%; }");
            writer.println("th, td { border: 1px solid #ddd; padding: 8px; }");
            writer.println("th { background-color: #f2f2f2; }");
            writer.println("</style>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h2>Relevé de Notes</h2>");
            writer.println("<h3>Etudiant: " + etudiant.getNom() + "</h3>");
            writer.println("<table>");
            writer.println("<tr>");
            writer.println("<th>Module</th>");
            writer.println("<th>Matiere</th>");
            writer.println("<th>Note</th>");
            writer.println("<th>Coefficient</th>");
            writer.println("<th>Moyenne</th>");
            writer.println("</tr>");

            double totalCoefficient = 0.0;
            double totalMoyenne = 0.0;

            for (Note note : noteList) {
                Matiere matiere = note.getMatiere();
                Module module = matiereService.getModuleOfMatiere(matiere.getNomMatiere());

                double coefficient = matiereService.getCoefMatiere(matiere.getNomMatiere());
                double moyenne = note.getNote() * coefficient;

                totalCoefficient += coefficient;
                totalMoyenne += moyenne;

                writer.println("<tr>");
                writer.println("<td>" + module.getNomModule() + "</td>");
                writer.println("<td>" + matiere.getNomMatiere() + "</td>");
                writer.println("<td>" + note.getNote() + "</td>");
                writer.println("<td>" + coefficient + "</td>");
                writer.println("<td>" + moyenne + "</td>");
                writer.println("</tr>");
            }

            double moyenneGenerale = totalMoyenne / totalCoefficient;

            writer.println("<tr>");
            writer.println("<td colspan=\"6\">Moyenne générale: " + moyenneGenerale + "</td>");
            writer.println("</tr>");
            writer.println("<tr>");
            writer.println("<td colspan=\"6\">Mention: " + (moyenneGenerale >= 10.0 ? "Passable" : "Redouble") + "</td>");
            writer.println("</tr>");

            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
                        Desktop.getDesktop().open(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

 //private Utilisateur getCurrentUser() {
    // Implement after Integration
  //  return AuthenticationService.getCurrentUser();
//}
   private Utilisateur getCurrentUser() {
    // Create a dummy user for testing purposes
    Utilisateur dummyUser = new Etudiant(); 
    dummyUser.setRole(Role.ETUDIANT); 

    return dummyUser;
}
private void showAccessDeniedAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Accès refusé");
    alert.setHeaderText(null);
    alert.setContentText("Accès refusé. Vous devez être un administrateur pour accéder à cette fonctionnalité.");
    alert.showAndWait();
}
}
 