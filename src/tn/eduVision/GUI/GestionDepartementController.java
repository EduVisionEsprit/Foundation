package tn.eduVision.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.eduVision.entités.Departement;
import tn.eduVision.entités.Groupe;
import tn.eduVision.services.ServiceDepartement;

import java.sql.SQLException;

public class GestionDepartementController {

    @FXML
    private TableColumn<Departement, Integer> id_dep;

    @FXML
    private TextField txt_titre;

    @FXML
    private Button btn_add;

    @FXML
    private TableColumn<Departement, String> titredep;

    @FXML
    private TableColumn<Departement, String> descriptiondep;

    @FXML
    private TextField txt_id;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_update;

    @FXML
    private TextField txt_description;

    @FXML
    private TableView<Departement> Departements_table;

    @FXML
    private void initialize () {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Employee objects should be used for the particular column.
        The arrow -> indicates that we're using a Java 8 feature called Lambdas.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe
        We're only using StringProperty values for our table columns in this example.
        When you want to use IntegerProperty or DoubleProperty, the setCellValueFactory(...)
        must have an additional asObject():
        */
        ShowDepartements();
    }
    ServiceDepartement a=new ServiceDepartement();
    public void ShowDepartements() {
        id_dep.setCellValueFactory(new PropertyValueFactory<>("id"));
        titredep.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptiondep.setCellValueFactory(new PropertyValueFactory<>("description"));
            Departements_table.setItems(a.getAll());

    }

    @FXML
    void add(ActionEvent event) {
        Departement dep=new Departement();
        dep.setId(Integer.valueOf(txt_id.getText()));
        dep.setTitre(txt_titre.getText());
        dep.setDescription(txt_description.getText());
        a.add(dep);
        ShowDepartements();
    }

    @FXML
    void update(ActionEvent event) throws SQLException {
        Departement dep=new Departement();
        dep.setId(Integer.valueOf(txt_id.getText()));
        dep.setTitre(txt_titre.getText());
        dep.setDescription(txt_description.getText());
        a.update(dep.getId(),dep);
        ShowDepartements();

    }

    @FXML
    void delete(ActionEvent event) {
        Departement dep =Departements_table.getSelectionModel().getSelectedItem();
        a.delete(dep.getId());
        ShowDepartements();
    }

}
