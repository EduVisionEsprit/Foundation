/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class Materiel extends Ressource{
      private String nomMateriel;
      private int quantite;
    // Constructeur, getters et setters

    public Materiel(String nomMateriel, int idRessource, TypeRessource typeRessource, int quantite) {
        super(idRessource, typeRessource);
        this.nomMateriel = nomMateriel;
        this.quantite = quantite;
        
    }

    @Override
    public String toString() {
        return "Materiel{" + "nomMateriel=" + nomMateriel + ", quantite=" + quantite + '}';
    }

    public String getNomMateriel() {
        return nomMateriel;
    }

    public void setNomMateriel(String nomMateriel) {
        this.nomMateriel = nomMateriel;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    
}