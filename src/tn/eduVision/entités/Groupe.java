/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class Groupe {
    private int id;
    private int num;
    private String email;
    private int niveau;
    private String specialite;
    private int id_departement;

    public String getTitre_dep() {
        return titre_dep;
    }

    public void setTitre_dep(String titre_dep) {
        this.titre_dep = titre_dep;
    }

    private String titre_dep;

    public int getDepartement() {
        return id_departement;
    }

    public void setDepartement(int departement) {
        this.id_departement = departement;
    }

    // Getters and setters
    public Groupe() {

    }

    public Groupe(int id, int num, String email, int niveau, String specialite, int departement, String titre_dep) {
        this.id = id;
        this.num = num;
        this.email = email;
        this.niveau = niveau;
        this.specialite = specialite;
        this.id_departement = departement;
        this.titre_dep = titre_dep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    
}
