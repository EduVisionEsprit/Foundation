/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.entités;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.sql.*;
import tn.eduVision.entités.EspaceStageEtudiant;
import tn.eduVision.entités.ButtonCell;

/**
 *
 * @author Meher
 */
public class AjoutStage extends Application {
    
    
    
    private TextField idStageField;
      private TextField idutilisateurField;
    private TextField nomOffreStageField;
    private TextArea critereSelectionArea;

    public static void main(String[] args) {
        launch(args);
 
       
    }

    
    
    
    
    
   
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ajout Stage");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label idStageLabel = new Label("ID Stage:");
        GridPane.setConstraints(idStageLabel, 0, 0);
        idStageField = new TextField();
        idStageField.setPromptText("Entrez l'ID du stage");
        GridPane.setConstraints(idStageField, 1, 0);
        
        
      Label idutilisateurLabel = new Label("ID Utilisateur:");
    GridPane.setConstraints(idutilisateurLabel, 0, 3);
    idutilisateurField = new TextField();  // Removed the type declaration
    idutilisateurField.setPromptText("Entrez l'ID de l'utilisateur");
    GridPane.setConstraints(idutilisateurField, 1, 3);

        Label nomOffreStageLabel = new Label("Nom de l'offre de stage:");
        GridPane.setConstraints(nomOffreStageLabel, 0, 1);
        nomOffreStageField = new TextField();
        nomOffreStageField.setPromptText("Entrez le nom de l'offre de stage");
        GridPane.setConstraints(nomOffreStageField, 1, 1);

    Label critereSelectionLabel = new Label("Critère de sélection:");
        GridPane.setConstraints(critereSelectionLabel, 0, 2);
        critereSelectionArea = new TextArea();
        critereSelectionArea.setPromptText("Entrez les critères de sélection");
        critereSelectionArea.setPrefRowCount(4);
        GridPane.setConstraints(critereSelectionArea, 1, 2);
        
        Button addButton3 = new Button("accueil");
        addButton3.getStyleClass().add("CSS/button-style");
        
        
        
        
        

        Button addButton = new Button("Ajouter");
        addButton.getStyleClass().add("CSS/button-style");
        
       
        
        addButton.setOnAction(e -> ajouterStage());
        GridPane.setConstraints(addButton, 1, 4);
        GridPane.setConstraints(addButton3, 1, 5);

       grid.getChildren().addAll(idStageLabel, idStageField, nomOffreStageLabel, nomOffreStageField,
    critereSelectionLabel, critereSelectionArea, addButton, idutilisateurLabel, idutilisateurField);
       

        Scene scene = new Scene(grid, 400, 200);
    
      scene.getStylesheets().add("CSS/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
    
    
    private void ajouterStage() {
        String idStage = idStageField.getText();
        String idutilisateur = idutilisateurField.getText();
        String nomOffreStage = nomOffreStageField.getText();
        String critereSelection = critereSelectionArea.getText();

       
    try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
        String query = "INSERT INTO stages (id_stage, id_utilisateur, nom_offre_stage, critere_selection) " +
                       "VALUES ('" + idStage + "', '" + idutilisateur + "', '" + nomOffreStage + "', '" + critereSelection + "')";
        dbManager.executeUpdate(query);
        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }
    
    
   
      
    }      
      
      

   

    private void setTitle(String interface_Enseignant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setScene(Scene scene) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void show() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
