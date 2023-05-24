/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class Communication {
    private int idCommunication;
    private Utilisateur utilisateur1;
    private Utilisateur utilisateur2;
    private String sujet;
    private String message;

    public Communication(int idCommunication, Utilisateur utilisateur1, Utilisateur utilisateur2, String sujet, String message) {
        this.idCommunication = idCommunication;
        this.utilisateur1 = utilisateur1;
        this.utilisateur2 = utilisateur2;
        this.sujet = sujet;
        this.message = message;
    }

    public int getIdCommunication() {
        return idCommunication;
    }

    public void setIdCommunication(int idCommunication) {
        this.idCommunication = idCommunication;
    }

    public Utilisateur getUtilisateur1() {
        return utilisateur1;
    }

    public void setUtilisateur1(Utilisateur utilisateur1) {
        this.utilisateur1 = utilisateur1;
    }

    public Utilisateur getUtilisateur2() {
        return utilisateur2;
    }

    public void setUtilisateur2(Utilisateur utilisateur2) {
        this.utilisateur2 = utilisateur2;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
   
}
