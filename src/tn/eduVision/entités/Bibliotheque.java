/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class Bibliotheque {
    private int idBibliotheque;
    private Utilisateur utilisateur;
    private String rapportStage;

    public Bibliotheque(int idBibliotheque, Utilisateur utilisateur, String rapportStage) {
        this.idBibliotheque = idBibliotheque;
        this.utilisateur = utilisateur;
        this.rapportStage = rapportStage;
    }

    public int getIdBibliotheque() {
        return idBibliotheque;
    }

    public void setIdBibliotheque(int idBibliotheque) {
        this.idBibliotheque = idBibliotheque;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getRapportStage() {
        return rapportStage;
    }

    public void setRapportStage(String rapportStage) {
        this.rapportStage = rapportStage;
    }

    
}
