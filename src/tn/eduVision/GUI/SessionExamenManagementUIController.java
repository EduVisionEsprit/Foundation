/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author bhsan
 */
public class SessionExamenManagementUIController implements Initializable {

    @FXML
    private ComboBox<?> cmbMatieres;
    @FXML
    private DatePicker datePickerDebut;
    @FXML
    private DatePicker datePickerFin;
    @FXML
    private ComboBox<?> cmbSessionsExamen;
    @FXML
    private DatePicker datePickerNouveauDebut;
    @FXML
    private DatePicker datePickerNouveauFin;
    @FXML
    private ComboBox<?> cmbSessionsExamenSupprimer;
    @FXML
    private ListView<?> lstSessionsExamen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void creerSessionExamen(ActionEvent event) {
    }

    @FXML
    private void modifierSessionExamen(ActionEvent event) {
    }

    @FXML
    private void supprimerSessionExamen(ActionEvent event) {
    }
    
}
