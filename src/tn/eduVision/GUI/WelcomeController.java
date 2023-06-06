package tn.eduVision.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.Node;

public class WelcomeController {

    @FXML
    private void openGestionModules(ActionEvent event) throws IOException {
        Parent gestionModulesRoot = FXMLLoader.load(getClass().getResource("GestionModules.fxml"));
        Scene gestionModulesScene = new Scene(gestionModulesRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(gestionModulesScene);
        stage.show();
    }

    @FXML
    private void openGestionProgrammeEtudes(ActionEvent event) throws IOException {
        Parent gestionProgrammeEtudesRoot = FXMLLoader.load(getClass().getResource("GestionProgrammeEtudes.fxml"));
        Scene gestionProgrammeEtudesScene = new Scene(gestionProgrammeEtudesRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(gestionProgrammeEtudesScene);
        stage.show();
    }

    @FXML
    private void openGestionMatieres(ActionEvent event) throws IOException {
        Parent gestionMatieresRoot = FXMLLoader.load(getClass().getResource("GestionMatieres.fxml"));
        Scene gestionMatieresScene = new Scene(gestionMatieresRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(gestionMatieresScene);
        stage.show();
    }
}
