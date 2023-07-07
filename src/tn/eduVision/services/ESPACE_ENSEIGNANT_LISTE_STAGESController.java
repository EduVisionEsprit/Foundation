package tn.eduVision.services;

import java.io.IOException;
import tn.eduVision.services.DecisionButtonCell;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.eduVision.entités.StageEtudiant;

public class ESPACE_ENSEIGNANT_LISTE_STAGESController implements Initializable {
    
    SessionManager sessionManager = SessionManager.getInstance();

         int connecteduserid = sessionManager.getUserId();
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
    private TableColumn<StageEtudiant, Integer> id_user;
    @FXML
    private TableColumn<StageEtudiant, StageEtudiant> Status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nom_entreprise.setCellValueFactory(new PropertyValueFactory<>("nomentreprise"));
        titre_stage.setCellValueFactory(new PropertyValueFactory<>("titrestage"));
        description_stage.setCellValueFactory(new PropertyValueFactory<>("descriptionstage"));
        id_user.setCellValueFactory(new PropertyValueFactory<>("utilisateur"));
        id_user.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUtilisateur().getIdUtilisateur()).asObject());

        Status.setCellFactory(createDecisionCellFactory());
        description_stage.setCellFactory(createDownloadCellFactory());

        ListeStageEnseignant list = new ListeStageEnseignant();
        List<StageEtudiant> stages = list.getListeStagesFromDatabase();
        tableView.getItems().addAll(stages);
    }

    private Callback<TableColumn<StageEtudiant, StageEtudiant>, TableCell<StageEtudiant, StageEtudiant>> createDecisionCellFactory() {
        return new Callback<TableColumn<StageEtudiant, StageEtudiant>, TableCell<StageEtudiant, StageEtudiant>>() {
            @Override
            public TableCell<StageEtudiant, StageEtudiant> call(TableColumn<StageEtudiant, StageEtudiant> column) {
                return new TableCell<StageEtudiant, StageEtudiant>() {
                    @Override
                    protected void updateItem(StageEtudiant stage, boolean empty) {
                        super.updateItem(stage, empty);
                        if (!empty) {
                            Button acceptButton = new Button("Accepter");
                            Button rejectButton = new Button("Refuser");

                            acceptButton.setOnAction((ActionEvent event) -> {
                                // Gérer l'action d'acceptation ici
                        
                                StageEtudiant selectedStage = getTableView().getItems().get(getIndex());
                                System.out.println(selectedStage);
                                int userId = selectedStage.getUtilisateur().getIdUtilisateur();
                                int stageId = selectedStage.getIdStage();
                                
                                DecisionButtonCell decisionButton = new DecisionButtonCell();
                                String userEmail1 = decisionButton.EmailFromUserTable(connecteduserid);
                                String userEmail2 = decisionButton.EmailFromUserTable(userId);
                                String motdepasse = decisionButton.PasswordFromUserTable(connecteduserid);
                                
                                decisionButton.sendNotificationEmail(userEmail1,motdepasse,userEmail2, "Votre stage a été accepté");
                                decisionButton.updatedecesionaccept(stageId,userId);
                            });

                            rejectButton.setOnAction((ActionEvent event) -> {
                                // Gérer l'action de refus ici
                                StageEtudiant selectedStage = getTableView().getItems().get(getIndex());
                                int userId = selectedStage.getUtilisateur().getIdUtilisateur();
                                int stageId = selectedStage.getIdStage();
                                DecisionButtonCell decisionButton = new DecisionButtonCell();
                              String userEmail1 = decisionButton.EmailFromUserTable(connecteduserid);
                                String userEmail2 = decisionButton.EmailFromUserTable(userId);
                                String motdepasse = decisionButton.PasswordFromUserTable(connecteduserid);
                           
                                decisionButton.sendNotificationEmail(userEmail1,motdepasse,userEmail2, "Votre stage a été refusé");
                                decisionButton.updatedecesionreject(stageId);
                            });

                            setGraphic(createButtonPane(acceptButton, rejectButton));
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        };
    }

    private Callback<TableColumn<StageEtudiant, String>, TableCell<StageEtudiant, String>> createDownloadCellFactory() {
        return new Callback<TableColumn<StageEtudiant, String>, TableCell<StageEtudiant, String>>() {
            @Override
            public TableCell<StageEtudiant, String> call(TableColumn<StageEtudiant, String> column) {
                return new TableCell<StageEtudiant, String>() {
                    @Override
                    protected void updateItem(String description, boolean empty) {
                        super.updateItem(description, empty);
                        if (description != null && !empty) {
                            Button downloadButton = new Button("Télécharger");
 DecisionButtonCell decisionButton = new DecisionButtonCell();
                            downloadButton.setOnAction(event -> {
                               // Récupérer l'ID du stage de la ligne correspondante
                                StageEtudiant selectedStage = getTableView().getItems().get(getIndex());
                                int stageId = selectedStage.getIdStage();
                                  
                                // Récupérer le chemin du fichier à partir de la base de données
                                String filePath = decisionButton.getFilePathFromDatabase(stageId);
                                if (filePath != null) {
                                    // Télécharger le fichier
                                    decisionButton.downloadFile(filePath);
                                }
                                
                            });

                            setGraphic(downloadButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        };
    }

    private Pane createButtonPane(Button acceptButton, Button rejectButton) {
        HBox buttonPane = new HBox(acceptButton, rejectButton);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(5));

        return buttonPane;
    }
    
    
    
    @FXML
    private void stagehome(ActionEvent event) throws IOException {
        ESPACE_STAGE_ENSEIGNANT espacestageenseignant = new ESPACE_STAGE_ENSEIGNANT();
        espacestageenseignant.start(new Stage());

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
