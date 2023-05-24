/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class Inscription {
    private int idInscription;
    private Etudiant etudiant;
    private Groupe Groupe;
    private int anneeInscription;

    public Inscription(int idInscription, Etudiant etudiant, Groupe Groupe, int anneeInscription) {
        this.idInscription = idInscription;
        this.etudiant = etudiant;
        this.Groupe = Groupe;
        this.anneeInscription = anneeInscription;
    }

    public int getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(int idInscription) {
        this.idInscription = idInscription;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Groupe getGroupe() {
        return Groupe;
    }

    public void setGroupe(Groupe Groupe) {
        this.Groupe = Groupe;
    }

    public int getAnneeInscription() {
        return anneeInscription;
    }

    public void setAnneeInscription(int anneeInscription) {
        this.anneeInscription = anneeInscription;
    }

  
}
