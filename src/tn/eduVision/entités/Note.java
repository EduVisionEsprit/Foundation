package tn.eduVision.entités;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Note {
    private int idNote;
    private Utilisateur utilisateur;
    private Matiere matiere;
    private float note;

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

    public StringProperty getNomMatiereProperty() {
        return new SimpleStringProperty(matiere.getNomMatiere());
    }

    public FloatProperty getNoteProperty() {
        return new SimpleFloatProperty(note);
    }

    public Etudiant getEtudiant() {
        return (Etudiant) utilisateur;
    }

    private StringProperty nomEtudiantProperty;

    public Note(int idNote, Utilisateur utilisateur, Matiere matiere, float note) {
        this.idNote = idNote;
        this.utilisateur = utilisateur;
        this.matiere = matiere;
        this.note = note;
        this.nomEtudiantProperty = new SimpleStringProperty(((Etudiant) utilisateur).getNom());
    }

    public StringProperty getNomEtudiantProperty() {
        return nomEtudiantProperty;
    }
}
