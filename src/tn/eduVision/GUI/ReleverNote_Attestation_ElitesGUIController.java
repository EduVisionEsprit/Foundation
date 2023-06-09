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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author bhsan
 */
public class ReleverNote_Attestation_ElitesGUIController implements Initializable {

    @FXML
    private ComboBox<?> cmbSessionsExamen;
    @FXML
    private ComboBox<?> cmbMatieres;
    @FXML
    private Button creerReleveNotesButton;
    @FXML
    private Button genererAttestationReussiteButton;
    @FXML
    private Button genererListeElitesButton;
    @FXML
    private Label lblResultat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void creerReleveNotes(ActionEvent event) {
    }

    @FXML
    private void genererAttestationReussite(ActionEvent event) {
    }

    @FXML
    private void genererListeElites(ActionEvent event) {
    }
    
}
