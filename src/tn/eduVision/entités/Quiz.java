/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.entités;

import java.util.ArrayList;

/**
 *
 * @author sebte
 */
public class Quiz {
    protected int id ;
    protected String sujet ;
    protected int idFormation ;
    protected int idFormateur ;
    protected ArrayList<Question> questions ;
    
   public Quiz(){
        this.questions = new ArrayList<>() ;
    }
    
    public Quiz(int id, String sujet) {
        this.id = id;
        this.sujet = sujet;
        this.questions = new ArrayList<>();
    }

    public Quiz(int id, String sujet, ArrayList<Question> questions) {
        this.id = id;
        this.sujet = sujet;
        this.questions = questions;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public int getIdFormateur() {
        return idFormateur;
    }

    public void setIdFormateur(int idFormateur) {
        this.idFormateur = idFormateur;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }


    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
        public boolean verifierQuestion(Question q){
        for(Question qq : this.questions){
            if(qq.getQuestionPosee().equals(q.getQuestionPosee()) && qq.getReponseCorrecte().equals(q.getReponseCorrecte()) &&
                    qq.getReponseFausse1().equals(q.getReponseFausse1()) && qq.getReponseFausse2().equals(q.getReponseFausse2())
                    && qq.getReponseFausse3().equals(q.getReponseFausse3())){
                return true ;
            }
        }
        return false ;
    }
        
            public boolean addQuestion(Question q){
        if(!checkQuestion(q)){
            this.questions.add(q);
            return true ;
        }
        return false ;
    }
            
                
    public boolean checkQuestion(Question q){
        if(this.questions.isEmpty())
            return false ;
        if(this.questions.contains(q)){
            return true ;
        }
     return false ;
    }
        public Question getQuestion(int index){
       return this.questions.get(index);
    }
    
            public int getTotalNote(){
        int sumNote = 0 ;
        for(Question q : this.questions){
            sumNote += q.getNote() ;
        }
        return sumNote ;
    }
    
    public float getMoyenneTest(){
        return this.getTotalNote()/2 ;
    }
    
    
}
