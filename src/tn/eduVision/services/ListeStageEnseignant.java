
package tn.eduVision.entités;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import tn.eduVision.entités.ListeStagesEnseignant;

/**
 *
 * @author Meher
 */
public class ListeStagesEnseignant extends Application{
    
    
    public static void main(String[] args) {
        launch(args);
 
       
    }
    
    
    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Liste des Stages");

        GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(5);
    grid.setHgap(5);
    
    
    // Ajouter le titre
    Label titleLabel = new Label("Liste des Stages");
    titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
   
     TableView<List<String>> tableView = new TableView<>();
 

    StackPane root = new StackPane(); // Conteneur principal
    
     
    TableColumn<List<String>, Void> actionsColumn = new TableColumn<>("Actions");
actionsColumn.setCellFactory(col -> new ButtonCell());

// ...

// Add columns to the table view
tableView.getColumns().addAll(actionsColumn);

    // Create table columns
    TableColumn<List<String>, String> idColumn = new TableColumn<>("ID STAGE");
    TableColumn<List<String>, String> utilisateurColumn = new TableColumn<>("Utilisateur");
    TableColumn<List<String>, String> nomOffreStageColumn = new TableColumn<>("Nom Offre Stage");
    TableColumn<List<String>, String> critereSelectionColumn = new TableColumn<>("Critère de Sélection");
        
    
     // Set preferred widths for table columns
    idColumn.setPrefWidth(150); // Adjust the width as needed
    utilisateurColumn.setPrefWidth(150);
    nomOffreStageColumn.setPrefWidth(200);
    critereSelectionColumn.setPrefWidth(250);

    // Set preferred height for the table view
    tableView.setPrefHeight(400); // Adjust the height as needed
    
    // Set value factories for the columns
    idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
    utilisateurColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
    nomOffreStageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
    critereSelectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));

    
    
    // Add columns to the table view
    tableView.getColumns().addAll(idColumn, utilisateurColumn, nomOffreStageColumn, critereSelectionColumn);

    // Retrieve the stages data from the database
    List<List<String>> stagesData = getListeStagesFromDatabase();

    // Add the stages data to the table view
    tableView.getItems().addAll(stagesData);

    // Add the table view to the grid pane
    grid.add(tableView, 0, 0);

    Scene scene = new Scene(grid, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.show();
        
     }

        public List<List<String>> getListeStagesFromDatabase() {
        
         List<List<String>> listeStages = new ArrayList<>();
         try {
    DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
    
    
    String query = " select * from stages" ;
    
    
    
   ResultSet resultSet = dbManager.executeQuery(query);
    
    while (resultSet.next()){
    int idStage = resultSet.getInt("id_stage");
     int idutilisateur = resultSet.getInt("id_utilisateur");
    String nomOffreStage = resultSet.getString("nom_offre_stage");
    String critereSelection = resultSet.getString("critere_selection");
  
    
    
    
List<String> stageData = new ArrayList<>();
     stageData.add(String.valueOf(idStage));
      stageData.add(String.valueOf(idutilisateur));
        stageData.add((nomOffreStage));
         stageData.add(critereSelection);
      
    
    listeStages.add(stageData);
     
    }
    
    dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }
        
         return listeStages;
        
        }
        
        
        
         
    
}
