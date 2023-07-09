package tn.eduVision.services;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.eduVision.entités.StageEtudiant;

/**
 * FXML Controller class
 * 
 * Controller for the SUIVIE_STAGE_ETUDIANT.fxml view
 */
public class SUIVIE_STAGE_ETUDIANTController implements Initializable {
    
    
    
    
     SessionManager sessionManager = SessionManager.getInstance();

         int connecteduserid = sessionManager.getUserId();
         String nom = sessionManager.getNom();
         String prenom = sessionManager.getPrenom();
         

    DecisionButtonCell decisionButton = new DecisionButtonCell();

    @FXML
    public TableView<StageEtudiant> tableView;
    @FXML
    private TableColumn<StageEtudiant, String> nom_entreprise;
    @FXML
    private TableColumn<StageEtudiant, String> titre_stage;
    @FXML
    private TableColumn<StageEtudiant, String> description_stage;
    @FXML
    private TableColumn<StageEtudiant, String> Etudiant;
    @FXML
    private TableColumn<StageEtudiant, String> Enseignant;
    @FXML
    private TableColumn<StageEtudiant, StageEtudiant> Rapport;
    @FXML
    private TableColumn<StageEtudiant, String> Validation;
    @FXML
    private TableColumn<StageEtudiant, Float> Note;

   
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
nom_entreprise.setCellValueFactory(new PropertyValueFactory<>("nomentreprise"));
    titre_stage.setCellValueFactory(new PropertyValueFactory<>("titrestage"));
    description_stage.setCellValueFactory(new PropertyValueFactory<>("descriptionstage"));
    Etudiant.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUtilisateur().getNom() + " " + cellData.getValue().getUtilisateur().getPrenom()));
    Enseignant.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNomenseignant() + " " + cellData.getValue().getPrenomenseignant()));

    Validation.setCellFactory(createValidationCellFactory()); 
    Note.setCellFactory(createNoteCellFactory()); 

    Rapport.setCellFactory(createRapportCellFactory());

    SuivieStageEtudiant list = new SuivieStageEtudiant();
    List<StageEtudiant> stages = list.getSuivieStageEtudiant();

    tableView.getItems().addAll(stages);
    
    
    
   
    

    }

   
private Callback<TableColumn<StageEtudiant, StageEtudiant>, TableCell<StageEtudiant, StageEtudiant>> createRapportCellFactory() {
        return new Callback<TableColumn<StageEtudiant, StageEtudiant>, TableCell<StageEtudiant, StageEtudiant>>() {
            @Override
            public TableCell<StageEtudiant, StageEtudiant> call(TableColumn<StageEtudiant, StageEtudiant> column) {
                return new TableCell<StageEtudiant, StageEtudiant>() {
                    @Override
                    protected void updateItem(StageEtudiant stage, boolean empty) {
                        super.updateItem(stage, empty);
                        if (!empty) {
                            Button chooseButton = new Button("choisir rapport");
                            TextField path = new TextField();
                            Button uploadButton = new Button("upload rapport");
                            

                            chooseButton.setOnAction((ActionEvent event) -> {
                                
                        
                                        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            path.setText(selectedFile.getAbsolutePath());
            
        }

                            });

                            uploadButton.setOnAction((ActionEvent event) -> {
                                
                                SessionManager sessionManager = SessionManager.getInstance();

                                 int userid = sessionManager.getUserId();
                                 String filePath = path.getText();
                                 Path source = Paths.get(filePath);
                                 String fileName = source.getFileName().toString();
                                 String destinationPath = "C:\\xampp\\htdocs\\upload";
                                 Path destination = Paths.get(destinationPath, fileName);
                                 System.out.println(filePath);
                                  SuivieStageEtudiant suivieupload = new SuivieStageEtudiant();
                                  suivieupload.ajouterrapport(destination.toString());
         
                                
                                 
                                
        
        if (filePath != null && !filePath.isEmpty()) {
            
            suivieupload.uploadFile(filePath, "C:\\xampp\\htdocs\\upload");
            
            
            
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Votre stage a été ajouté avec succès!");
        alert.showAndWait();
        }
        
        else {
            
            
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez choisir un fichier.");
        alert.showAndWait();
        }
                                 StageEtudiant selectedStage = getTableView().getItems().get(getIndex());
                                
                                
    
                                int stageId = selectedStage.getIdStage();
                               String emailenseignant= suivieupload.getEmailForEnseignant(stageId);
                               System.out.println("email enseignant"+emailenseignant);
                               
                                String userEmail1 = decisionButton.EmailFromUserTable(connecteduserid);
                                String motdepasse = decisionButton.PasswordFromUserTable(connecteduserid);
                                decisionButton.sendNotificationEmail(userEmail1,motdepasse,emailenseignant, "le rapport de "+nom +"  "+prenom+"a été uploadé");
                                suivieupload.inserevalidation(stageId, connecteduserid);

                            });

                            setGraphic(createButtonPane(chooseButton,path,uploadButton));
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        };
    }

    
    private Pane createButtonPane(Button chooseButton,TextField path,Button uploadButton) {
        HBox buttonPane = new HBox(chooseButton, path,uploadButton);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(5));

        return buttonPane;
    }

    
    
    
    private Callback<TableColumn<StageEtudiant, String>, TableCell<StageEtudiant, String>> createValidationCellFactory() {
    return new Callback<TableColumn<StageEtudiant, String>, TableCell<StageEtudiant, String>>() {
        @Override
        public TableCell<StageEtudiant, String> call(TableColumn<StageEtudiant, String> column) {
            return new TableCell<StageEtudiant, String>() {
                @Override
                protected void updateItem(String validation, boolean empty) {
                    super.updateItem(validation, empty);
                    if (!empty) {
                        StageEtudiant stage = getTableView().getItems().get(getIndex());
                        int stageId = stage.getIdStage();
                        SuivieStageEtudiant suivistage = new SuivieStageEtudiant();
                        String validationText = suivistage.getValidationText(stageId); // Get the validation text from the database
                       
                        setText(validationText);
                    } else {
                        setText(null);
                    }
                }
            };
        }
    };
}
    
    
    
    
    
    
    
    
    
    
    
    
    private Callback<TableColumn<StageEtudiant, Float>, TableCell<StageEtudiant, Float>> createNoteCellFactory() {
    return new Callback<TableColumn<StageEtudiant, Float>, TableCell<StageEtudiant, Float>>() {
        @Override
        public TableCell<StageEtudiant, Float> call(TableColumn<StageEtudiant, Float> column) {
            return new TableCell<StageEtudiant, Float>() {
                @Override
                protected void updateItem(Float note, boolean empty) {
                    super.updateItem(note, empty);
                    if (!empty) {
                        StageEtudiant stage = getTableView().getItems().get(getIndex());
                        int stageId = stage.getIdStage();
                        SuivieStageEtudiant suivistage = new SuivieStageEtudiant();
                        Float noteValue = suivistage.getNoteValue(stageId); 
                        
                        setText(String.valueOf(noteValue));
                    } else {
                        setText(null);
                    }
                }
            };
        }
    };
}
    
    
    
    
    
    @FXML
    private void stagehome(ActionEvent event) throws IOException {
        ESPACE_STAGE_ETUDIANT espacestageenseignant = new ESPACE_STAGE_ETUDIANT();
        espacestageenseignant.start(new Stage());

        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
