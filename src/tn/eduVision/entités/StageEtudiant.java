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
public class Stage {
    private int idStage;
    private Utilisateur utilisateur;
    private String nomOffreStage;
    private String critereSelection;
    
    private List<Candidature> candidatures;
    // Ajoutez ici les getters et les setters

    public Stage(int idStage, Utilisateur utilisateur, String nomOffreStage, String critereSelection, List<Candidature> candidatures) {
        this.idStage = idStage;
        this.utilisateur = utilisateur;
        this.nomOffreStage = nomOffreStage;
        this.critereSelection = critereSelection;
        this.candidatures = candidatures;
    }

    public int getIdStage() {
        return idStage;
    }

    public void setIdStage(int idStage) {
        this.idStage = idStage;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getNomOffreStage() {
        return nomOffreStage;
    }

    public void setNomOffreStage(String nomOffreStage) {
        this.nomOffreStage = nomOffreStage;
    }

    public String getCritereSelection() {
        return critereSelection;
    }

    public void setCritereSelection(String critereSelection) {
        this.critereSelection = critereSelection;
    }

    public List<Candidature> getCandidatures() {
        return candidatures;
    }

    public void setCandidatures(List<Candidature> candidatures) {
        this.candidatures = candidatures;
    }
    
}
