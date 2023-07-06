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
public class Admin extends Utilisateur{
    private float salaire;
    private String positionAdmin;

    public Admin(float salaire, String positionAdmin, int idUtilisateur, String nom, String prenom, String email, String motDePasse, Role role) {
        super(idUtilisateur, nom, prenom, email, motDePasse, role);
        this.salaire = salaire;
        this.positionAdmin = positionAdmin;
    }

    public Admin() {
                super();

    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public String getPositionAdmin() {
        return positionAdmin;
    }

    public void setPositionAdmin(String positionAdmin) {
        this.positionAdmin = positionAdmin;
    }

  
    
    
}
