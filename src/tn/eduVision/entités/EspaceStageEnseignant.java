
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
import tn.eduVision.entités.ListeStagesEnseignant;

/**
 *
 * @author Meher
 */
public class EspaceStageEnseignant extends Application {
    
    
    public static void main(String[] args) {
        launch(args);
 
       
    }
    
    
    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ESPACE STAGE ENSEIGNANT");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        
        Button addButton1 = new Button("Liste des stages");
        addButton1.getStyleClass().add("CSS/button-style");
        
        addButton1.setOnAction(e -> {
        
         ListeStagesEnseignant liste = new ListeStagesEnseignant();
         liste.start(primaryStage);
        
    
        });
        
        GridPane.setConstraints(addButton1, 1, 1);
        
        
        
         Scene scene = new Scene(grid, 800, 600);
    
         grid.getChildren().add(addButton1);

      scene.getStylesheets().add("CSS/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        
     }

    
    
    
    
    
    

    
    
}
