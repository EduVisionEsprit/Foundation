/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

import java.math.BigDecimal;

/**
 *
 * @author Sayf
 */
public class Note {
    private int idNote;
    private Utilisateur utilisateur;
    private Matiere matiere;
    private float note;

    public Note(int idNote, Utilisateur utilisateur, Matiere matiere, float note) {
        this.idNote = idNote;
        this.utilisateur = utilisateur;
        this.matiere = matiere;
        this.note = note;
    }


    public Note() {
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    
}
