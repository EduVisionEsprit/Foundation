/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.entités;

/**
 *
 * @author bhsan
 */
public class Resultats {
    private int idResultat;
    private Utilisateur utilisateur;
    private String sujet;
    private String contenu;
    private float moyenne;

    public Resultats(int idResultat, Utilisateur utilisateur, String sujet, String contenu, float moyenne) {
        this.idResultat = idResultat;
        this.utilisateur = utilisateur;
        this.sujet = sujet;
        this.contenu = contenu;
        this.moyenne = moyenne;
    }

    public int getIdResultat() {
        return idResultat;
    }

    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }
}
