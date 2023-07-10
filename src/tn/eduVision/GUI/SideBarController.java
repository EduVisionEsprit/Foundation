package tn.eduVision.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SideBarController {

    @FXML
    private Button btnProgramme;

    @FXML
    private Button btnMatiere;

    @FXML
    private Button btnModule;

    @FXML
    private Button btnPlanEtude;

    @FXML
    private Button btnStatistiques;

    @FXML
    private void handleProgrammeClick() {
        // Handle the "Programme" button click
        loadFXML("GestionProgrammeEtudes.fxml");
    }

    @FXML
    private void handleMatiereClick() {
        // Handle the "Matiere" button click
        loadFXML("GestionMatieres.fxml");
    }

    @FXML
    private void handleModuleClick() {
        // Handle the "Module" button click
        loadFXML("GestionModules.fxml");
    }

    @FXML
    private void handlePlanEtudeClick() {
        // Handle the "Plan Etude" button click
        loadFXML("PlanEtudeGUI.fxml");
    }

    @FXML
    private void handleStatistiquesClick() {
        // Handle the "Statistiques" button click
        loadFXML("PublierResultatUI.fxml");
    }

    private void loadFXML(String fxmlFileName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFileName));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
