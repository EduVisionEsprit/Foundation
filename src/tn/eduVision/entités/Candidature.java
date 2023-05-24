/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class Candidature {
    private int idCandidature;
    private Utilisateur utilisateur;
    private Stage stage;

    public Candidature(int idCandidature, Utilisateur utilisateur, Stage stage) {
        this.idCandidature = idCandidature;
        this.utilisateur = utilisateur;
        this.stage = stage;
    }

    
    public int getIdCandidature() {
        return idCandidature;
    }

    public void setIdCandidature(int idCandidature) {
        this.idCandidature = idCandidature;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    
}