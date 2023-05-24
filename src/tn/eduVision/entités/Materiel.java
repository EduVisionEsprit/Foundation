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

    // Constructeur, getters et setters

    public Materiel(String nomMateriel, int idRessource, TypeRessource typeRessource) {
        super(idRessource, typeRessource);
        this.nomMateriel = nomMateriel;
    }

    public String getNomMateriel() {
        return nomMateriel;
    }

    public void setNomMateriel(String nomMateriel) {
        this.nomMateriel = nomMateriel;
    }
    
}