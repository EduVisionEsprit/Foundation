/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class Salle extends Ressource{
    private String nomSalle;
    private int capacite;
    private String equipements;
    private String disponibilite;
    private TypeSalle typeSalle;

    // Constructeur, getters et setters

    public Salle(String nomSalle, int capacite, String equipements, String disponibilite, TypeSalle typeSalle, int idRessource, TypeRessource typeRessource) {
        super(idRessource, typeRessource);
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.equipements = equipements;
        this.disponibilite = disponibilite;
        this.typeSalle = typeSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getEquipements() {
        return equipements;
    }

    public void setEquipements(String equipements) {
        this.equipements = equipements;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public TypeSalle getTypeSalle() {
        return typeSalle;
    }

    public void setTypeSalle(TypeSalle typeSalle) {
        this.typeSalle = typeSalle;
    }
    
}
