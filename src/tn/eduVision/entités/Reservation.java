/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author Sayf
 */
public class Reservation {
    private int idReservation;
    private Utilisateur utilisateur;
    private Date dateReservation;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Etat etat;
    private Ressource ressource;

    public Reservation(int idReservation, Utilisateur utilisateur, Date dateReservation, LocalTime heureDebut, LocalTime heureFin, Etat etat, Ressource ressource) {
        this.idReservation = idReservation;
        this.utilisateur = utilisateur;
        this.dateReservation = dateReservation;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.etat = etat;
        this.ressource = ressource;
    }

    @Override
    public String toString() {
        return "Reservation{" + "idReservation=" + idReservation + ", dateReservation=" + dateReservation + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + ", etat=" + etat + '}';
    }
    
    public Reservation(int idReservation){
        this.idReservation = idReservation;
    }
    

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Ressource getRessource() {
        return ressource;
    }

    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }
    
    
}

