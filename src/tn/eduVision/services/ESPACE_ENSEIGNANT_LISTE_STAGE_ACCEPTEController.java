/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import static com.sun.xml.internal.ws.api.pipe.Fiber.current;
import java.io.File;
import java.io.IOException;
import tn.eduVision.services.DecisionButtonCell;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static java.util.concurrent.ThreadLocalRandom.current;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
 * @author Meher
 */
public class ESPACE_ENSEIGNANT_LISTE_STAGE_ACCEPTEController implements Initializable {
    
    
    
    
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
    private TableColumn<StageEtudiant, Integer> Etudiant;
    @FXML
    private TableColumn<StageEtudiant, StageEtudiant> Rapport;
    @FXML
    private TableColumn<StageEtudiant, Float> Note;
    @FXML
    private Button stagehome;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
         nom_entreprise.setCellValueFactory(new PropertyValueFactory<>("nomentreprise"));
        titre_stage.setCellValueFactory(new PropertyValueFactory<>("titrestage"));
        description_stage.setCellValueFactory(new PropertyValueFactory<>("descriptionstage"));
        Etudiant.setCellValueFactory(new PropertyValueFactory<>("etudiant"));
       
    
        id_user.setCellValueFactory(new PropertyValueFactory<>("utilisateur"));
        id_user.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUtilisateur().getIdUtilisateur()).asObject());
        
        Etudiant.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getUtilisateur().getNom() + "  " +cellData.getValue().getUtilisateur().getPrenom() ));
        
        
       Rapport.setCellFactory(createRapportCellFactory());
       
       Note.setCellFactory(createNoteCellFactory());
        Liste_Stage_Enseignant_Accepte list = new Liste_Stage_Enseignant_Accepte();
        List<StageEtudiant> stages = list.getListeStagesaccepteFromDatabase();
        tableView.getItems().addAll(stages);
        
       
    }    
    
    
    
    
    
    
    
    @FXML
    private void stagehome(ActionEvent event) throws IOException {
        ESPACE_STAGE_ENSEIGNANT espacestageenseignant = new ESPACE_STAGE_ENSEIGNANT();
        espacestageenseignant.start(new Stage());

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
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
                            Button downloadButton = new Button("téléchargher le rapport");
                           
                            

                            downloadButton.setOnAction((ActionEvent event) -> {
                                StageEtudiant selectedStage = getTableView().getItems().get(getIndex());
                                int stageid = selectedStage.getIdStage();
                        DecisionButtonCell decisionButton = new DecisionButtonCell();
                        
                              String filepath =  decisionButton.getFilePathFromsuivistage(stageid);
                              
                              decisionButton.downloadFile(filepath);
                               

                            });

                           

                            setGraphic(createButtonPane(downloadButton));
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        };
    }
    
    
   
    
    private Pane createButtonPane(Button download) {
        HBox buttonPane = new HBox(download);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(5));

        return buttonPane;
    }
    
    
    
  
private Callback<TableColumn<StageEtudiant, Float>, TableCell<StageEtudiant, Float>> createNoteCellFactory() {
    return new Callback<TableColumn<StageEtudiant, Float>, TableCell<StageEtudiant, Float>>() {
        @Override
        public TableCell<StageEtudiant, Float> call(TableColumn<StageEtudiant, Float> column) {
            return new TableCell<StageEtudiant, Float>() {
                private final HBox cellPane;
                private final TextField textField;
                private final Button saveButton;

                {
                    cellPane = new HBox();
                    textField  = new TextField();
                    saveButton = new Button("Enregistrer la note");

                    cellPane.getChildren().addAll(textField, saveButton);

                    textField.setOnAction(event -> {
                        commitEdit(Float.parseFloat(textField.getText()));
                    });

                    saveButton.setOnAction(event -> {
                        
                        StageEtudiant selectedStage = getTableView().getItems().get(getIndex());
                                int stageid = selectedStage.getIdStage();
                                int userid = selectedStage.getUtilisateur().getIdUtilisateur();
                               
                        Float note = Float.parseFloat(textField.getText());
                        commitEdit(note);
                        Liste_Stage_Enseignant_Accepte lsea = new Liste_Stage_Enseignant_Accepte();
                        lsea.EvaluationStage(stageid, userid, note);
                        textField.setDisable(true);
                    });
                }

                @Override
protected void updateItem(Float note, boolean empty) {
    super.updateItem(note, empty);
    if (!empty) {
        setGraphic(cellPane);
        if (note != null) {
            textField.setText(String.valueOf(note));
        } else {
            textField.setText("");
        }
    } else {
        setGraphic(null);
    }
}

                @Override
                public void startEdit() {
                    super.startEdit();
                    textField.requestFocus();
                }

                @Override
                public void cancelEdit() {
                    super.cancelEdit();
                    textField.setText(String.valueOf(getItem()));
                }

                @Override
                public void commitEdit(Float newValue) {
                    super.commitEdit(newValue);
                    // Perform any necessary actions when the value is committed
                    // For example, update the value in the underlying data model
                }
            };
        }
    };
    
    
    
    
    
    

}



   
   

}
