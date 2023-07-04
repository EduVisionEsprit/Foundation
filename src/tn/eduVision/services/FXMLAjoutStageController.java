/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Application.launch;
import static javafx.application.ConditionalFeature.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import tn.eduVision.services.AjoutStage;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.services.UtilisateurService;

/**
 * FXML Controller class
 *
 * @author Meher
 * 
 * 

 */




public class FXMLAjoutStageController implements Initializable {
    
    
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
    @FXML
    
    
    private void handleButtonAction(ActionEvent event) {
        
        String nomentreprise = nom_entreprise.getText();  
        String titrestage = titre_stage.getText();
        String descstage = description_stage.getText();
        int iduser = Integer.parseInt((user_id.getText()));
        String choisirfichier = path.getText();
        
        System.out.println(choisirfichier);
        
        // Vérifier que tous les champs sont saisis
    if (((nomentreprise.isEmpty() || titrestage.isEmpty()) || descstage.isEmpty()) || user_id.getText().equals("")) {
        // Afficher un message d'erreur si des champs sont vides
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs.");
        alert.showAndWait();
        return; // Arrêter l'exécution de la méthode si des champs sont vides
    } 
        UtilisateurService us = new UtilisateurService();
        Utilisateur user = us.getUtilisateurById(iduser);
        
        String dec=null;
        
        AjoutStage ajst = new AjoutStage();
        ajst.ajouterStage(user, nomentreprise, titrestage, descstage,dec,choisirfichier);
        
        
        // Get the selected file path
        String filePath = path.getText();
        if (filePath != null && !filePath.isEmpty()) {
            // Upload the file to the specified folder
            ajst.uploadFile(filePath, "C:\\Users\\Meher\\Documents\\esprit\\upload");
            
            
            // Show success message after uploading the file
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Votre stage a été ajouté avec succès!");
        alert.showAndWait();
        }
        
        else {
            
            // Show error message if no file is chosen
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
        // TODO
    }    
    
}
