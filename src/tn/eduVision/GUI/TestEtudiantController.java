/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import tn.eduVision.entités.Quiz;
import tn.eduVision.entités.Test;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.services.QuizService;
import tn.eduVision.services.TestService;



/**
 * FXML Controller class
 *
 * @author sebte
 */
public class TestEtudiantController implements Initializable {
    
    
    
   private Utilisateur currentUser ;
    
    @FXML
    private Label welcomeLabel;
    @FXML
    private GridPane testContainer;
    
    private List<Quiz> quiz ;
    private List<Test> test ;
    @FXML
    private Label idEtudiant;  
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox<String> comboTrier;
    
    public void initData(Utilisateur u){
             welcomeLabel.setText("Bienvenue Imen ");   
             idEtudiant.setText(1+"");
        //welcomeLabel.setText("Bienvenue "+u.getNom() +"  "+ u.getPrenom());   
        //idEtudiant.setText(u.getId()+"");
        idEtudiant.setVisible(false);     
    }
    
    public Utilisateur getCurrentUser(){
        return currentUser ;
    }
    
    private List<Quiz> getAllQuiz_Test(String search){
        List<Quiz> list  ;
        QuizService qdao = new QuizService() ;
        list = qdao.getAllQuiz(search) ;       
       
       return list ;
    }
    
    private List<Test> getAllTest(String search){     
        List<Test> listTest ;
        TestService tdao = new TestService() ;
        listTest = tdao.getAllTest(search) ;
        return listTest; 
    }
    
    private List<Quiz> getQuizAndTest(){
         quiz = new ArrayList<>(getAllQuiz_Test("")) ;
         test = new ArrayList<>(getAllTest("")) ;
         quiz.addAll(test) ;
      return quiz ;
    }
    
    private void setData(List<Quiz> listQ){
         
        int column = 0;
        int row = 1 ;
        boolean isTheFisrtTest = false ;
         try {
               for(Quiz q : listQ){
                   if(isTheFisrtTest == false){
                        if(q.getClass().getSimpleName().toLowerCase().equals("test")){
                            isTheFisrtTest = true ;
                                column = 0 ;
                                ++row ;
                         }
                    }
                    FXMLLoader fxmlloader = new FXMLLoader() ;
                    fxmlloader.setLocation(getClass().getResource("cardTest.fxml"));
                    VBox testBox = fxmlloader.load() ;
                    CardTestController cardTestController = fxmlloader.getController() ;
                  //  UserService us = UserService.getInstance() ;
                    QuizService qs = new QuizService();
                    Utilisateur u =  qs.getUserById(q.getIdFormateur()) ;
                    cardTestController.setData(q,u) ;
                             
                    if(column == 3){
                    column = 0 ;
                    ++row ;
                   }
                    
                    testContainer.add(testBox, column++, row);
                    GridPane.setMargin(testBox, new Insets(10));

                }             
               
        } catch (IOException ex) {
                Logger.getLogger(TestEtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       setData(getQuizAndTest()) ;
          ObservableList<String> options = FXCollections.observableArrayList("Quiz","Test");
          comboTrier.setItems(options);
    }

    @FXML
    private void trierQuiz(ActionEvent event) {
        String options = comboTrier.getValue() ;
        if(options.toLowerCase().equals("quiz")){
            testContainer.getChildren().clear();
            quiz = getAllQuiz_Test(tfSearch.getText()) ;
            setData(quiz);
        }
        if(options.toLowerCase().equals("test")){
           testContainer.getChildren().clear();
           List<Test> list =  getAllTest(tfSearch.getText()) ;
           quiz = new ArrayList<>();
           quiz.addAll(list) ;
           setData(quiz);
       }
        
    }

    @FXML
    private void searchQuiz(KeyEvent event) {
        testContainer.getChildren().clear();
        String search = tfSearch.getText() ;
        quiz = getAllQuiz_Test(search);
        List<Test> listTest = getAllTest(search) ;   
        quiz.addAll(listTest) ;
        setData(quiz);
    }


    
}

    

