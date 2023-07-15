package tn.eduVision.GUI;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.eduVision.entités.Departement;
import tn.eduVision.entités.Groupe;
import tn.eduVision.services.ServiceDepartement;
import tn.eduVision.services.ServiceGroupe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GestionGroupeController {

    @FXML
    private TableColumn<Groupe, Integer> num_grp1;

    @FXML
    private Button supprnon;

    @FXML
    private TextField txt_niveau;

    @FXML
    private ChoiceBox<String> DepChoiceBox;

    @FXML
    private TableColumn<Groupe,Integer> num_grp;

    @FXML
    private TextField txt_email;

    @FXML
    private Button supproui;

    @FXML
    private TableColumn<Groupe,String> spec;

    @FXML
    private TableColumn<Groupe, Integer> dep;

    @FXML
    private TextField txt_num;

    @FXML
    private Button btn_add;

    @FXML
    private Button modifoui;

    @FXML
    private TextField id_txt;

    @FXML
    private Button btn_delete;

    @FXML
    private TableColumn<Groupe, Integer> niv;

    @FXML
    private TextField txt_specialite;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<Groupe, String> email;

    @FXML
    private Button modifnon;

    @FXML
    private TableView<Groupe> GroupesTable;
    ServiceGroupe s = new ServiceGroupe();
    ServiceDepartement d = new ServiceDepartement();
    // create a alert
    Alert a = new Alert(Alert.AlertType.NONE);

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
        ObservableList<String> list = (FXCollections.observableArrayList());
        for (Departement departement : d.getAll()) {
            String lib = String.valueOf(departement.getId()) +'/' + departement.getTitre();
            list.add(lib);
        }
        DepChoiceBox.setItems(list);
        ShowGroupes();
    }

    public void ShowGroupes() {
        num_grp1.setCellValueFactory(new PropertyValueFactory<>("id"));
        num_grp.setCellValueFactory(new PropertyValueFactory<>("num"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        spec.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        dep.setCellValueFactory(new PropertyValueFactory<>("titre_dep"));
        niv.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        GroupesTable.setItems(s.getAll());

    }
    @FXML
    void add(ActionEvent event) {

        try{
        var g = new Groupe();
        g.setId(Integer.valueOf(id_txt.getText()));
        g.setNum(Integer.valueOf(txt_num.getText()));
        g.setEmail(txt_email.getText());
        g.setNiveau(Integer.valueOf(txt_niveau.getText()));
        g.setSpecialite(txt_specialite.getText());
        g.setDepartement(Integer.valueOf(DepChoiceBox.getValue().substring(0,DepChoiceBox.getValue().indexOf('/'))));
        s.add(g);
        ShowGroupes();}
        catch(Exception e){
            // set alert type
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            // show the dialog
            a.show();
        }
    }

    @FXML
    void update(ActionEvent event) {
        try{
            validateInput();
        Groupe g = new Groupe();
        g.setId(Integer.valueOf(id_txt.getText()));
        g.setNum(Integer.valueOf(txt_num.getText()));
        g.setEmail(txt_email.getText());
        g.setNiveau(Integer.valueOf(txt_niveau.getText()));
        g.setSpecialite(txt_specialite.getText());
        g.setDepartement(Integer.valueOf(DepChoiceBox.getValue().substring(0,DepChoiceBox.getValue().indexOf('/'))));
        s.update(g.getId(),g);
        ShowGroupes();}
        catch(Exception e){
            // set alert type
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            // show the dialog
            a.show();
        }

    }

    public static boolean isNumeric(String string) {
        int intValue;


        if(string == null || string.equals("")) {
            return false;
        }
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error Validation");
        }
        return true;
    }

    boolean validateInput() throws Exception {
        if(!isNumeric(id_txt.getText())){
            throw new Exception("id invalide");
        }

        if(!isNumeric(txt_num.getText())){
            throw new Exception("numero invalide");
        }
        if(!isNumeric(txt_niveau.getText())){
            throw new Exception("nieau invalide");
        }
        if(!isEmailValid(txt_email.getText())){
            throw new Exception("email invalide");
        }
        return true;
    }

    Boolean isEmailValid(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    @FXML
    void delete(ActionEvent event) {

        try{
        Groupe dep =GroupesTable.getSelectionModel().getSelectedItem();
        s.delete(dep.getId());
        ShowGroupes();}
       catch(Exception e){
                // set alert type
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText(e.getMessage());
                // show the dialog
                a.show();
            }
    }

    @FXML
    void yes_modify(ActionEvent event) {

    }

    @FXML
    void no_modify(ActionEvent event) {

    }

    @FXML
    void yes_del(ActionEvent event) {

    }

    @FXML
    void no_del(ActionEvent event) {

    }

}
