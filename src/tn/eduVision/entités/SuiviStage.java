/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class SuiviStage {
    private int idSuivi;
    private Utilisateur utilisateur;
    private Stage stage;
    private String rapportStage;
    private Boolean validationRapport;
    
    // Ajoutez ici les getters et les setters

    public SuiviStage(int idSuivi, Utilisateur utilisateur, Stage stage, String rapportStage, Boolean validationRapport) {
        this.idSuivi = idSuivi;
        this.utilisateur = utilisateur;
        this.stage = stage;
        this.rapportStage = rapportStage;
        this.validationRapport = validationRapport;
    }

    public int getIdSuivi() {
        return idSuivi;
    }

    public void setIdSuivi(int idSuivi) {
        this.idSuivi = idSuivi;
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

    public String getRapportStage() {
        return rapportStage;
    }

    public void setRapportStage(String rapportStage) {
        this.rapportStage = rapportStage;
    }

    public Boolean getValidationRapport() {
        return validationRapport;
    }

    public void setValidationRapport(Boolean validationRapport) {
        this.validationRapport = validationRapport;
    }
    
}