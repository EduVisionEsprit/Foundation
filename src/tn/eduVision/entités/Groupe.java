/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

import java.util.List;

/**
 *
 * @author Sayf
 */
public class Groupe {
    private int idGroupe;
    private Integer num;
    private String email;
    private String niveau;
    private String specialite;
    private Departement departement;

    private List<Etudiant> etudiants;
    private List<Module> modules;
    // Getters and setters

    public Groupe(int idGroupe, Integer num, String email, String niveau, String specialite, Departement departement, List<Etudiant> etudiants, List<Module> modules) {
        this.idGroupe = idGroupe;
        this.num = num;
        this.email = email;
        this.niveau = niveau;
        this.specialite = specialite;
        this.departement = departement;
        this.etudiants = etudiants;
        this.modules = modules;
    }

    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
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

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    
    
}
