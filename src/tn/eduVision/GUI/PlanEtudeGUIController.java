package tn.eduVision.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import tn.eduVision.entités.Groupe;
import tn.eduVision.entités.Module;
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.services.GroupeService;
import tn.eduVision.services.ModuleService;
import tn.eduVision.services.ProgrammeEtudeService;
import tn.eduVision.entités.Matiere;

public class PlanEtudeGUIController {

    @FXML
    private VBox contentVBox;
    @FXML
    private ComboBox<String> programmeEtudeComboBox;
    @FXML
    private TableView<Module> modulesTableView;
    @FXML
    private TableColumn<Module, String> moduleColumn;
    @FXML
    private TableColumn<Module, String> matieresColumn;
    @FXML
    private TableColumn<Module, Double> coefColumn;
    @FXML
    private Label departementLabel;
    @FXML
    private Label programmeEtudeLabel;
    @FXML
    private Label groupeLabel;
    @FXML
    private TextField email;
    @FXML
    private Button envoiButton;

    private ProgrammeEtudeService programmeEtudeService;
    private ModuleService moduleService;

    public void initialize() {
        moduleService = new ModuleService();

        programmeEtudeService = new ProgrammeEtudeService();
        List<ProgrammeEtude> programmeEtudeList = programmeEtudeService.getAll();
        List<String> programmeDescriptions = programmeEtudeList.stream()
                .map(ProgrammeEtude::getDescription)
                .collect(Collectors.toList());
        ObservableList<String> programmeEtudeOptions = FXCollections.observableArrayList(programmeDescriptions);
        programmeEtudeComboBox.setItems(programmeEtudeOptions);

        moduleColumn.setCellValueFactory(new PropertyValueFactory<>("nomModule"));

        matieresColumn.setCellValueFactory(data -> {
            Module module = data.getValue();
            if (module != null) {
                List<String> nomMatieres = moduleService.getNomMatiere(module);
                if (nomMatieres != null) {
                    String matieresString = nomMatieres.stream()
                            .collect(Collectors.joining("\n | "));
                    return new SimpleStringProperty(matieresString);
                }
            }
            return new SimpleStringProperty("N/A");
        });

        coefColumn.setCellValueFactory(data -> {
            Module module = data.getValue();
            if (module != null) {
                List<String> nomMatieres = moduleService.getNomMatiere(module);
                if (nomMatieres != null) {
                    double coefficient = nomMatieres.stream()
                            .mapToDouble(moduleService::getCoefMatiere)
                            .sum();
                    return new SimpleDoubleProperty(coefficient).asObject();
                }
            }
            return new SimpleDoubleProperty(0.0).asObject();
        });
    }

    @FXML
    private void handleProgrammeEtudeSelection() {
        String selectedProgrammeDescription = programmeEtudeComboBox.getValue();
        ProgrammeEtude programmeEtude = retrieveProgrammeByDescription(selectedProgrammeDescription);

        String vboxContent = getContentAsHTML();
        if (programmeEtude != null) {
            List<Module> modules = moduleService.getModulesByProgramme(programmeEtude);

            if (modules != null) {
                for (Module module : modules) {
                    List<Matiere> matieres = module.getMatieres();
                    List<String> nomMatieres = new ArrayList<>();
                    if (matieres != null) {
                        nomMatieres = matieres.stream()
                                .map(Matiere::getNomMatiere)
                                .collect(Collectors.toList());
                    }
                    module.setMatieres(nomMatieres);
                }
                modulesTableView.setItems(FXCollections.observableArrayList(modules));
                return;
            }
        }
        modulesTableView.setItems(null);
    }

    private ProgrammeEtude retrieveProgrammeByDescription(String description) {
        List<ProgrammeEtude> programmeEtudeList = programmeEtudeService.getAll();
        for (ProgrammeEtude pe : programmeEtudeList) {
            if (pe.getDescription().equals(description)) {
                pe.setId(programmeEtudeService.getProgrammeIdByDescription(description));
                return pe;
            }
        }
        return null;
    }

    private String getSelectedProgramDescription() {
        return programmeEtudeComboBox.getValue();
    }

    private String emailRecipient = "";

 @FXML
private void handleEnvoiButtonClicked() throws AddressException, MessagingException {
    emailRecipient = email.getText();
    String[] recipients = emailRecipient.split(";");
    String selectedProgrammeDescription = programmeEtudeComboBox.getValue();
    ProgrammeEtude programmeEtude = retrieveProgrammeByDescription(selectedProgrammeDescription);

    if (programmeEtude != null) {
        String vboxContent = getContentAsHTML();
        departementLabel.setText(programmeEtude.getDescription());
        programmeEtudeLabel.setText(programmeEtude.getDescription());
        groupeLabel.setText("");
        String senderEmail = "sana.benhammouda@esprit.tn";
        String senderPassword = "Linedata09896815.";
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        for (String recipient : recipients) {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient.trim()));
            message.setSubject("Plan d'étude");

            message.setContent(vboxContent, "text/html");
            Transport.send(message);
            System.out.println("Email envoyé à: " + recipient);
        }
        
    }
}

    
 private String getContentAsHTML() {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><head>");
    sb.append("<style>");
    sb.append("body { font-family: Arial, sans-serif; }");
    sb.append("h2 { color: #333333; }");
    sb.append("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
    sb.append("table th, table td { border: 1px solid #dddddd; padding: 8px; }");
    sb.append("table th { background-color: #f2f2f2; font-weight: bold; }");
    sb.append("</style>");
    sb.append("</head><body>");

    String selectedProgramme = programmeEtudeComboBox.getValue();
    if (selectedProgramme != null) {
        sb.append("<h2>Plan d'étude pour : ").append(selectedProgramme).append("</h2>");
    }

    sb.append("<p>Cher étudiant,</p>");
    sb.append("<p>Voici le plan d'étude pour le programme sélectionné :</p>");

    if (modulesTableView != null && modulesTableView.getItems() != null) {
    ObservableList<Module> modules = modulesTableView.getItems();
    sb.append("<table>");
    sb.append("<tr><th>Module</th><th>Matières</th><th>Coefficient</th></tr>");
    for (Module module : modules) {
        sb.append("<tr>");
        sb.append("<td>").append(module.getNomModule()).append("</td>");
        sb.append("<td>").append(matieresColumn.getCellData(module)).append("</td>");
        sb.append("<td>").append(coefColumn.getCellData(module)).append("</td>");
        sb.append("</tr>");
    }
    sb.append("</table>");
}

    sb.append("<p>Si vous avez des questions ou avez besoin de plus d'informations, n'hésitez pas à nous contacter.</p>");
    sb.append("<p>Cordialement,</p>");
    sb.append("<p>Votre université</p>");

    sb.append("</body></html>");
    return sb.toString();
}


}
