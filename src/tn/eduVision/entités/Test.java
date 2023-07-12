/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

import java.util.ArrayList;

/**
 *
 * @author Sayf
 */
public class Test extends Quiz {
    private int idTest;
    private Utilisateur utilisateur;
    private int nbEtudiantsPasses ;
    private int nbEtudiantsAdmis ;
    private int duree ;
    private String temps ;
    
    public Test() {
        
    }
    
    public Test(int id,String sujet,ArrayList<Question> questions){
        super(id, sujet, questions);
    }
    
    public Test(int id ,String sujet,int nbEtudiantsPasses,int nbEtudiantsAdmis){
        super(id, sujet);
        this.nbEtudiantsAdmis = nbEtudiantsAdmis ;
        this.nbEtudiantsPasses = nbEtudiantsPasses ;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public int getNbEtudiantsPasses() {
        return nbEtudiantsPasses;
    }

    public void setNbEtudiantsPasses(int nbEtudiantsPasses) {
        this.nbEtudiantsPasses = nbEtudiantsPasses;
    }

    public int getNbEtudiantsAdmis() {
        return nbEtudiantsAdmis;
    }

    public void setNbEtudiantsAdmis(int nbEtudiantsAdmis) {
        this.nbEtudiantsAdmis = nbEtudiantsAdmis;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }
    
    
}
