/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI.Login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author thinkpad
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfID;
    @FXML
    private Button btnLogin;
    @FXML
    private Label loginMessageLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Login(ActionEvent event) {
        if(tfID.getText().trim().isEmpty()==false  && tfPassword.getText().trim().isEmpty()==false)
            loginMessageLabel.setText("login ...");
        else
        if(tfID.getText().trim().isEmpty())
                loginMessageLabel.setText("Please Enter your login");
        else 
            if(tfPassword.getText().trim().isEmpty())
                loginMessageLabel.setText("Please Enter your password");
        else
                loginMessageLabel.setText("Please Enter your login and password!");
                
    }
}
