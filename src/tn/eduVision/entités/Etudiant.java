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
public class Etudiant extends Utilisateur {
    private int anneeInscrit;
    private int nombreAbsences;
   
    private List<Absence> absences;
    private List<Note> notes;

    public Etudiant(int anneeInscrit, int nombreAbsences, List<Absence> absences, List<Note> notes, int idUtilisateur, String nom, String prenom, String email, String motDePasse, Role role) {
        super(idUtilisateur, nom, prenom, email, motDePasse, role);
        this.anneeInscrit = anneeInscrit;
        this.nombreAbsences = nombreAbsences;
        this.absences = absences;
        this.notes = notes;
    }

    public Etudiant(int idUtilisateur, String nom, String prenom, String email, String motDePasse, Role role) {
        super(idUtilisateur, nom, prenom, email, motDePasse, role);
    }
    
     public Etudiant() {
    }

    public Etudiant(int etudiantId) {
        super();//To change body of generated methods, choose Tools | Templates.
    }
 
    
    
  

    public int getAnneeInscrit() {
        return anneeInscrit;
    }

    public void setAnneeInscrit(int anneeInscrit) {
        this.anneeInscrit = anneeInscrit;
    }

    public int getNombreAbsences() {
        return nombreAbsences;
    }

    public void setNombreAbsences(int nombreAbsences) {
        this.nombreAbsences = nombreAbsences;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
    
    
  }
