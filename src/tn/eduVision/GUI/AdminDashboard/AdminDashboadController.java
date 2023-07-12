package tn.eduVision.GUI.AdminDashboard;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.services.UserServices;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.eduVision.entités.Role;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author thinkpad
 */
public class AdminDashboadController implements Initializable {

    @FXML
    private Button etudiantB;
    @FXML
    private Button ensB;
    @FXML
    private Button adminB;
    @FXML
    private Button paramB;
    @FXML
    private TableColumn<Utilisateur, String> nomtab;
    @FXML
    private TableColumn<Utilisateur, String> prenomtab;
    @FXML
    private TableColumn<Utilisateur, String> etattab;
    @FXML
    private TableColumn<Utilisateur, Void> actiontab;
    @FXML
    private TableView<Utilisateur> userTable;
    @FXML
    private Label buttonPath;
    @FXML
    private Label ButtonTitle;
    @FXML
    private TextField seachfield;
    @FXML
    private FontAwesomeIconView searchIcon;
    @FXML
    private TableColumn<?, ?> actiontab1;
    @FXML
    private Button Reservation;
    @FXML
    private Button Note;
    @FXML
    private Button Stage;
    @FXML
    private Button Classes;
    @FXML
    private Button Test;
    @FXML
    private Button Scholarité;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonPath.setText("Acceuil");
        ButtonTitle.setText("Acceuil");
        userTable.setVisible(false);
        seachfield.setVisible(false);
        searchIcon.setVisible(false);
        Scholarité.setVisible(true);
        Test.setVisible(true);
        Classes.setVisible(true);
        Stage.setVisible(true);
        Note.setVisible(true);
        Reservation.setVisible(true);        

    }

    @FXML
    private void ClickEtudiant(ActionEvent event) {
        loadData("etudiant");
        buttonPath.setText("Acceuil/Etudinant");
        ButtonTitle.setText("Etudinant");
        userTable.setVisible(true);
        seachfield.setVisible(true);
        searchIcon.setVisible(true);
        Scholarité.setVisible(false);
        Test.setVisible(false);
        Classes.setVisible(false);
        Stage.setVisible(false);
        Note.setVisible(false);
        Reservation.setVisible(false);
    }

    @FXML
    private void ClickEns(ActionEvent event) {
        loadData("enseignant");
        buttonPath.setText("Acceuil/Ensignant");
        ButtonTitle.setText("Ensignant");
       buttonPath.setText("Acceuil/Etudinant");
        ButtonTitle.setText("Etudinant");
        userTable.setVisible(true);
        seachfield.setVisible(true);
        searchIcon.setVisible(true);
        Scholarité.setVisible(false);
        Test.setVisible(false);
        Classes.setVisible(false);
        Stage.setVisible(false);
        Note.setVisible(false);
        Reservation.setVisible(false);
    }

    @FXML
    private void ClickAdmin(ActionEvent event) {
        buttonPath.setText("Acceuil/Admin");
        ButtonTitle.setText("Admin");
        userTable.setVisible(false);
        seachfield.setVisible(false);
        searchIcon.setVisible(false);    
        Scholarité.setVisible(false);
        Test.setVisible(false);
        Classes.setVisible(false);
        Stage.setVisible(false);
        Note.setVisible(false);
        Reservation.setVisible(false);
    }

    @FXML
    private void ClickPara(ActionEvent event) {
       initialize(null,null);
    }

    @FXML
    private void showMenu(MouseEvent event) {
    }

    private void loadData(String role) {
        UserServices us = UserServices.getInstance();
        List<Utilisateur> users = us.getUsersByRole(Role.valueOf(role));
        userTable.getItems().clear();
        //nom refere to the utilisateur attribute
        nomtab.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomtab.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        //etattab.setCellValueFactory(new PropertyValueFactory<>("approved"));

        etattab.setCellValueFactory(cellData -> {
            String value = "";
            if (cellData.getValue().getEtat() == 1) {
                value = "Approuvé";
            } else if (cellData.getValue().getEtat() == 0) {
                value = "En attente";

            }
            return new SimpleStringProperty(value);
        });
        
    }
    
     public void addUserSearch() {  
                
    }



}
