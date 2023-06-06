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
public class AttestationReussite {
    private int idAttestation;
    private Etudiant etudiant;
    private SessionExamen sessionExamen;
    private float moyenne;
    private String mention;

    public AttestationReussite(int idAttestation, Etudiant etudiant, SessionExamen sessionExamen, float moyenne, String mention) {
        this.idAttestation = idAttestation;
        this.etudiant = etudiant;
        this.sessionExamen = sessionExamen;
        this.moyenne = moyenne;
        this.mention = mention;
    }

    public int getIdAttestation() {
        return idAttestation;
    }

    public void setIdAttestation(int idAttestation) {
        this.idAttestation = idAttestation;
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

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }
    
}
