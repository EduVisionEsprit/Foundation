/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.services.NotificationResultatsService;
import tn.eduVision.services.NotificationResultatsManagementService;

public class PublierResultatController {
    @FXML
    private ComboBox<Etudiant> cmbEtudiants;

    @FXML
    private ComboBox<Matiere> cmbMatieres;

    private NotificationResultatsService notificationResultatsService;

    public PublierResultatController() {
        notificationResultatsService = new NotificationResultatsManagementService();
    }

    @FXML
    private void publierResultats() {
        Etudiant selectedEtudiant = cmbEtudiants.getValue();
        Matiere selectedMatiere = cmbMatieres.getValue();

        notificationResultatsService.PublierResultats(selectedEtudiant, selectedMatiere);
    }
}
