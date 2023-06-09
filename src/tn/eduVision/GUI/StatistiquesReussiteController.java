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
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author bhsan
 */
public class StatistiquesReussiteController implements Initializable {

    @FXML
    private ComboBox<?> cmbMatieres;
    @FXML
    private ComboBox<?> cmbMatieresEcartType;
    @FXML
    private ComboBox<?> cmbMatieresTauxReussite;
    @FXML
    private Label lblResultat;
    @FXML
    private PieChart pieChartTauxReussite;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void calculerMoyenne(ActionEvent event) {
    }

    @FXML
    private void calculerEcartType(ActionEvent event) {
    }

    @FXML
    private void calculerTauxReussite(ActionEvent event) {
    }
    
}
