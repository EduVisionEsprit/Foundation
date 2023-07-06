/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

import java.util.Date;

/**
 *
 * @author Sayf
 */
public class Absence {
    private int idAbsence;
    private Etudiant etudiant;
    private Seance seance;

    public Absence(int idAbsence, Etudiant etudiant, Seance seance) {
        this.idAbsence = idAbsence;
        this.etudiant = etudiant;
        this.seance = seance;
    }

    public int getIdAbsence() {
        return idAbsence;
    }

    public void setIdAbsence(int idAbsence) {
        this.idAbsence = idAbsence;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    
}