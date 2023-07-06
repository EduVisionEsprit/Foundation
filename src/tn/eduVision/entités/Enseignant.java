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
public class Enseignant extends Utilisateur {
      private String specialiteEns;
      private float salaire;
      private String bureauEns;

    public Enseignant(String specialiteEns, float salaire, String bureauEns, int idUtilisateur, String nom, String prenom, String email, String motDePasse, Role role) {
        super(idUtilisateur, nom, prenom, email, motDePasse, role);
        this.specialiteEns = specialiteEns;
        this.salaire = salaire;
        this.bureauEns = bureauEns;
    }

    public Enseignant() {
super();    }

    public String getSpecialiteEns() {
        return specialiteEns;
    }

    public void setSpecialiteEns(String specialiteEns) {
        this.specialiteEns = specialiteEns;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public String getBureauEns() {
        return bureauEns;
    }

    public void setBureauEns(String bureauEns) {
        this.bureauEns = bureauEns;
    }
      
}
