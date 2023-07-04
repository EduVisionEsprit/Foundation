/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

import java.util.List;

/**
 *
 * @author Sayf
 */
public class StageEtudiant {
    private int idStage;
    private Utilisateur utilisateur;
    private String nomentreprise;
    private String titrestage;
     private String descriptionstage;
     private String decision;
     private String path;
    
    private List<Candidature> candidatures;
    // Ajoutez ici les getters et les setters

    public StageEtudiant(int idStage, Utilisateur utilisateur, String nomentreprise, String titrestage,String descriptionstage,String decision) {
        this.idStage = idStage;
        this.utilisateur = utilisateur;
        this.nomentreprise = nomentreprise;
        this.titrestage = titrestage;
        this.descriptionstage = descriptionstage;
        this.decision=decision;
        
        
    }

    

    public int getIdStage() {
        return idStage;
    }

    public void setStageId(int idStage) {
        this.idStage = idStage;
    }

   public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getNomentreprise() {
        return nomentreprise;
    }

    public void setNomentreprise(String nomentreprise) {
        this.nomentreprise = nomentreprise;
    }

    public String getTitrestage() {
        return titrestage;
    }

    public void setTitrestage(String titrestage) {
        this.titrestage = titrestage;
    }
    
    
    public String getDescriptionstage() {
        return descriptionstage;
    }

    public void setDescriptionstage(String Descriptionstage) {
        this.descriptionstage = descriptionstage;
    }

    
    
    public String getDecision() {
        return decision;
    }

    public void setDecision(String decesion) {
        this.decision = decision;
    }
    public List<Candidature> getCandidatures() {
        return candidatures;
    }

    public void setCandidatures(List<Candidature> candidatures) {
        this.candidatures = candidatures;
    }
    
    
    
    @Override
public String toString() {
    return "Stage ID: " + idStage + 
           "\nUtilisateur: " + utilisateur + 
           "\nNom Entreprise: " + nomentreprise + 
           "\nTitre de Stage: " + titrestage +
           "\nDescription de Stage: " + descriptionstage +
           "\nDecision: " + decision;
}

    
}
