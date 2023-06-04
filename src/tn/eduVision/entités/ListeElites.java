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
public class ListeElites {
    private int idListe;
    private Etudiant etudiant;
    private SessionExamen sessionExamen;
    private Matiere matiere;

    public ListeElites(int idListe, Etudiant etudiant, SessionExamen sessionExamen, Matiere matiere) {
        this.idListe = idListe;
        this.etudiant = etudiant;
        this.sessionExamen = sessionExamen;
        this.matiere = matiere;
    }

    public int getIdListe() {
        return idListe;
    }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public SessionExamen getSessionExamen() {
        return sessionExamen;
    }

    public void setSessionExamen(SessionExamen sessionExamen) {
        this.sessionExamen = sessionExamen;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
}
