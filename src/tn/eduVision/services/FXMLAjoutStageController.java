/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author Meher
 * 
 * 

 */




public class FXMLAjoutStageController implements Initializable {
    
    
    private String typeStageSelectionne;
    
    
    public static void main(String[] args) {
        launch(args);
 
       
    }
    
    
    
     @FXML
    private TextField nom_entreprise;
    @FXML
    private TextField titre_stage;
    
    @FXML
    private TextField description_stage;
@FXML
    private TextField user_id;


@FXML
    private TextField path;



@FXML
    private Button choisir_fichier;
  

@FXML private ChoiceBox<String> typestage;
private String[] typestg={"Informatique","Mecanique","Electronique"};

    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        SessionManager sessionManager = SessionManager.getInstance();

         int userid = sessionManager.getUserId();
         
         
        
        String nomentreprise = nom_entreprise.getText();  
        String titrestage = titre_stage.getText();
        String descstage = description_stage.getText();
        String choisirfichier = path.getText();
        
        System.out.println(choisirfichier);
        
        
    if (((nomentreprise.isEmpty() || titrestage.isEmpty()) || descstage.isEmpty()) || choisirfichier.isEmpty()) {
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs.");
        alert.showAndWait();
        return; 
    } 
        
                   String choisirFichier = path.getText();
                   Path source = Paths.get(choisirFichier);
                   String fileName = source.getFileName().toString();
                   String status = "Pending";
                   String destinationPath = "C:\\xampp\\htdocs\\upload";
                    Path destination = Paths.get(destinationPath, fileName);
                    AjoutStage ajst = new AjoutStage();
                         ajst.ajouterStage(userid,nomentreprise, titrestage, descstage, status, destination.toString(),typeStageSelectionne);

        
        
        
        String filePath = path.getText();
        if (filePath != null && !filePath.isEmpty()) {
            
            
            ajst.uploadFile(filePath, destinationPath);
            
            
           
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Votre stage a été ajouté avec succès!");
        alert.showAndWait();
        }
        
        else {
            
            
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez choisir un fichier.");
        alert.showAndWait();
        }
        
    }

    
    
    @FXML
    private void handleFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            path.setText(selectedFile.getAbsolutePath());
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       typestage.getItems().addAll(typestg);
       
       typestage.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        typeStageSelectionne = newValue;
    });
    }    
    
    
    
    
    @FXML
    private void stagehome(ActionEvent event) throws IOException {
        ESPACE_STAGE_ETUDIANT espacestageenseignant = new ESPACE_STAGE_ETUDIANT();
        espacestageenseignant.start(new Stage());

        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
