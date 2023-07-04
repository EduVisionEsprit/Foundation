package tn.eduVision.GUI;

import java.awt.Insets;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Note;
import tn.eduVision.services.UserServices;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import tn.eduVision.services.NoteManagementService;

public class ResultatEtudiantsController implements Initializable {

    @FXML
    private TableView<Etudiant> tblEtudiants;
    @FXML
    private TableColumn<Etudiant, String> colNom;
    @FXML
    private TableColumn<Etudiant, Double> colMoyenne;
    @FXML
    private TableColumn<Etudiant, String> colResultat;
    @FXML
    private TableColumn<Etudiant, Boolean> colSelection;
    @FXML
    private Button btnPublier;
    @FXML
private Button btnListeElites;

@FXML
    private Button btnShowChart;
    private UserServices userServices;

    public ResultatEtudiantsController() {
        userServices = new UserServices();
    }


@Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Etudiant> etudiants = userServices.getEtudiants();

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colMoyenne.setCellValueFactory(new PropertyValueFactory<>("moyenne"));

        // Set the cell value factory for the colResultat column
        colResultat.setCellValueFactory(cellData -> {
            Etudiant etudiant = cellData.getValue();
            double moyenne = etudiant.getMoyenne();
            String resultat = determineResultat(moyenne);
            return new SimpleStringProperty(resultat);
        });

        tblEtudiants.getItems().addAll(etudiants);

       btnShowChart.setOnAction(event -> {
    showChartPopup((Stage) tblEtudiants.getScene().getWindow());
});
           btnListeElites.setOnAction(event -> displayEliteStudents());


    }
private void showChartPopup(Stage primaryStage) {
    // Create the chart and set its properties
    javafx.scene.chart.CategoryAxis xAxis = new javafx.scene.chart.CategoryAxis();
    javafx.scene.chart.NumberAxis yAxis = new javafx.scene.chart.NumberAxis();
    javafx.scene.chart.BarChart<String, Number> chart = new javafx.scene.chart.BarChart<>(xAxis, yAxis);
    chart.setTitle("Taux Reussite ");
    chart.getXAxis().setLabel("Resultat");
    chart.getYAxis().setLabel("%");

    // Create the chart data
    ObservableList<Etudiant> etudiants = tblEtudiants.getItems();
    int totalStudents = etudiants.size();
    int redoubleCount = 0;
    int reussitCount = 0;

    for (Etudiant etudiant : etudiants) {
        String resultat = etudiant.getResultat();
        if (resultat.equals("Redouble")) {
            redoubleCount++;
        } else if (resultat.startsWith("Réussite")) {
            reussitCount++;
        }
    }

    double redoublePercentage = (double) redoubleCount / totalStudents * 100;
    double reussitPercentage = (double) reussitCount / totalStudents * 100;

    javafx.scene.chart.XYChart.Series<String, Number> series = new javafx.scene.chart.XYChart.Series<>();
    series.getData().add(new javafx.scene.chart.XYChart.Data<>("Redouble", redoublePercentage));
    series.getData().add(new javafx.scene.chart.XYChart.Data<>("Réussit", reussitPercentage));
    chart.getData().add(series);

    // Set background color of the chart
    chart.setStyle("-fx-background-color: #F0F0F0;");

    // Create the close button
    Button closeButton = new Button("X");
    closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-size: 12px;");

    // Create the popup and add the chart and close button
    VBox content = new VBox(chart, closeButton);
    content.setSpacing(10);

    Popup popup = new Popup();
    popup.getContent().add(content);

    // Enable popup dragging
    chart.setOnMousePressed(event -> {
        chart.setMouseTransparent(true);
        popup.setAnchorX(event.getScreenX() - primaryStage.getWidth() / 2);
        popup.setAnchorY(event.getScreenY() - primaryStage.getHeight() / 2);
        chart.setMouseTransparent(false);
    });

    chart.setOnMouseDragged(event -> {
        popup.setAnchorX(event.getScreenX() - primaryStage.getWidth() / 2);
        popup.setAnchorY(event.getScreenY() - primaryStage.getHeight() / 2);
    });

    // Set the close button action
    closeButton.setOnAction(event -> popup.hide());

    // Show the popup
    popup.show(primaryStage, primaryStage.getX() + primaryStage.getWidth() / 2, primaryStage.getY() + primaryStage.getHeight() / 2);
}



private Matiere getMatiereForEtudiant(Etudiant etudiant) {
    // Retrieve the Matiere object associated with the Etudiant's notes
    for (Note note : etudiant.getNotes()) {
        if (note.getMatiere() != null) {
            return note.getMatiere();
        }
    }
    return null; // Handle the case when no Matiere is found
}
private double calculateMoyenne(Etudiant etudiant, Matiere matiere) {
    // Calculate the average for the student based on the Matiere object
    double total = 0;
    int totalCoef = 0;

    // Retrieve the notes for the specific Matiere
    NoteManagementService noteService = new NoteManagementService();
    List<Note> notes = noteService.obtenirNotesParMatiere(matiere);

    // Iterate through the notes and coefficients
    for (Note note : notes) {
        // Check if the note belongs to the student
        if (note.getEtudiant().equals(etudiant)) {
            total += note.getNote() * matiere.getCoef();
            totalCoef += matiere.getCoef();
        }
    }

    // Calculate the average
    if (totalCoef != 0) {
        return total / totalCoef;
    } else {
        return 0.0; // Handle the case when no notes are available
    }
}

private String determineResultat(double moyenne) {
    if (moyenne >= 10.0) {
        if (moyenne >= 16.0) {
            return "Réussite avec mention Très Bien";
        } else if (moyenne >= 14.0) {
            return "Réussite avec mention Bien";
        } else {
            return "Réussite Passable";
        }
    } else {
        return "Redouble";
    }
}
private void displayEliteStudents() {
    List<Etudiant> etudiants = userServices.getEtudiants();

    // Sort the students based on their averages
    etudiants.sort(Comparator.comparingDouble(Etudiant::getMoyenne).reversed());

    // Retrieve the top 3 elite students
    List<Etudiant> eliteStudents = etudiants.subList(0, Math.min(3, etudiants.size()));

    // Create the table for elite students
    TableView<Etudiant> tblElites = new TableView<>();

    // Create the "Nom" column
    TableColumn<Etudiant, String> colNomElites = new TableColumn<>("Nom");
    colNomElites.setCellValueFactory(new PropertyValueFactory<>("nom"));

    // Create the "Email" column
    TableColumn<Etudiant, String> colEmailElites = new TableColumn<>("Email");
    colEmailElites.setCellValueFactory(new PropertyValueFactory<>("email"));

    tblElites.getColumns().addAll(colNomElites, colEmailElites);
    tblElites.getItems().addAll(eliteStudents);

    // Create the "Informer" button
    Button informerButton = new Button("Informer");
    informerButton.setOnAction(event -> sendEmailToEliteStudent(tblElites.getSelectionModel().getSelectedItem()));

    // Create a new stage for the popup
    Stage popupStage = new Stage();
    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.setTitle("Liste des Elites");
    popupStage.setResizable(false);

    // Create a layout pane for the popup
    AnchorPane popupPane = new AnchorPane();
    popupPane.setPrefSize(600, 400);

    // Add the table and informer button to the popup pane
    AnchorPane.setTopAnchor(tblElites, 28.0);
    AnchorPane.setLeftAnchor(tblElites, 44.0);
    AnchorPane.setRightAnchor(tblElites, 44.0);
    AnchorPane.setBottomAnchor(tblElites, 60.0); // Adjust the bottom anchor to accommodate the button
    AnchorPane.setBottomAnchor(informerButton, 16.0);
    AnchorPane.setLeftAnchor(informerButton, 44.0);
    popupPane.getChildren().addAll(tblElites, informerButton);

    // Create a scene for the popup pane and set it on the stage
    Scene popupScene = new Scene(popupPane);
    popupStage.setScene(popupScene);

    // Show the popup window
    popupStage.show();
}


@FXML
private void sendEmailToEliteStudent(Etudiant selectedItem) {
    // Get the selected elite student from the table
    Etudiant selectedStudent = tblEtudiants.getSelectionModel().getSelectedItem();

    if (selectedStudent == null) {
        // No student is selected, show an alert to inform the user
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucun étudiant sélectionné");
        alert.setHeaderText("Aucun étudiant sélectionné");
        alert.setContentText("Veuillez sélectionner un étudiant avant de cliquer sur 'Informer'.");
        alert.showAndWait();
        return;
    }

    // Prepare the email content
    String subject = "Félicitations, vous faites partie des élites !";
    String message = "Cher " + selectedStudent.getNom() + ",\n\n"
            + "Nous sommes ravis de vous informer que vous êtes l'un des élèves les plus performants de l'établissement. "
            + "Votre travail acharné et votre dévouement ont été récompensés.\n"
            + "Veuillez contacter l'administration pour recevoir votre récompense.\n\n"
            + "Félicitations encore une fois et continuez votre excellent travail !\n\n"
            + "Cordialement,\nL'équipe de l'administration";

    // Send the email
    boolean emailSent = sendEmail(selectedStudent.getEmail(), subject, message);

    if (emailSent) {
        // Email sent successfully, show a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Email envoyé");
        alert.setHeaderText("Email envoyé avec succès");
        alert.setContentText("Un email a été envoyé à l'étudiant sélectionné pour l'informer de sa distinction.");
        alert.showAndWait();
    } else {
        // Failed to send the email, show an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur d'envoi de l'email");
        alert.setHeaderText("Une erreur s'est produite lors de l'envoi de l'email");
        alert.setContentText("Veuillez vérifier votre connexion Internet et réessayer.");
        alert.showAndWait();
    }
}
private boolean sendEmail(String recipient, String subject, String message) {
    // Set the email properties
 String senderEmail = "sana.benhammouda@esprit.tn";
        String senderPassword = "Linedata09896815.";
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

    // Create a session with the sender's credentials
    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    });

    try {
        // Create a new email message
        Message emailMessage = new MimeMessage(session);
        emailMessage.setFrom(new InternetAddress(senderEmail));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        // Send the email
        Transport.send(emailMessage);

        return true; // Email sent successfully
    } catch (MessagingException e) {
        e.printStackTrace();
        return false; // Failed to send the email
    }
}


}
