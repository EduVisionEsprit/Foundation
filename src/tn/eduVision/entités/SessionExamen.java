/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.entités;

import java.time.LocalDate;

/**
 *
 * @author bhsan
 */
public class SessionExamen {
     private int idSessionExamen;
    private Matiere matiere;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public SessionExamen(int idSessionExamen, Matiere matiere, LocalDate dateDebut, LocalDate dateFin) {
        this.idSessionExamen = idSessionExamen;
        this.matiere = matiere;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public SessionExamen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdSessionExamen() {
        return idSessionExamen;
    }

    public void setIdSessionExamen(int idSessionExamen) {
        this.idSessionExamen = idSessionExamen;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}
