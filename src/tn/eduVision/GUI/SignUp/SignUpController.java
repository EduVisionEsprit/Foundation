/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI.SignUp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.eduVision.GUI.Login.LoginWindow;

/**
 * FXML Controller class
 *
 * @author thinkpad
 */
public class SignUpController implements Initializable {

    public void signUpFrom(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void signUpFrom(MouseEvent event) {
        try {
            
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/eduVision/GUI/SignUp/SignupForm.fxml"));
            Parent root = loader.load();

           
            SignUpFormController signupFormController = loader.getController();
            //signupFormController.initData("get data to pass");

            // Create a new stage for the signup form scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage if needed
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            System.out.println("SignUpFormController");
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/eduVision/GUI/SignUp/SignUpFrom.fxml"));
            Parent root= loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(loader.load()));           

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.close();
            stage.show();
*/
            /*            Parent root = FXMLLoader.load(getClass().getResource("/tn/eduVision/GUI/SignUp/SignUpFrom.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();       
             */
        } catch (IOException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}