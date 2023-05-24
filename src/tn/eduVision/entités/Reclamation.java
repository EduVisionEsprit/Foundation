/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class Reclamation {
    private int idReclamation;
    private Utilisateur utilisateur;
    private String contenuReclamation;
    private Etat etat;
    private String typeReclamation;

    public Reclamation(int idReclamation, Utilisateur utilisateur, String contenuReclamation, Etat etat, String typeReclamation) {
        this.idReclamation = idReclamation;
        this.utilisateur = utilisateur;
        this.contenuReclamation = contenuReclamation;
        this.etat = etat;
        this.typeReclamation = typeReclamation;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getContenuReclamation() {
        return contenuReclamation;
    }

    public void setContenuReclamation(String contenuReclamation) {
        this.contenuReclamation = contenuReclamation;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public String getTypeReclamation() {
        return typeReclamation;
    }

    public void setTypeReclamation(String typeReclamation) {
        this.typeReclamation = typeReclamation;
    }
    
   
}