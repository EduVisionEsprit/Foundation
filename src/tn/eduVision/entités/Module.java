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
public class Module {
    private int idModule;
    private String nomModule;
    private ProgrammeEtude programme; 
    private List<Matiere> matieres;

    public Module(int idModule, String nomModule, ProgrammeEtude programme) {
        this.idModule = idModule;
        this.nomModule = nomModule;
        this.programme = programme;
    }

    public Module(int idModule, String nomModule, List<Matiere> matieres) {
        this.idModule = idModule;
        this.nomModule = nomModule;
        this.matieres = matieres;
    }

    public Module(int idModule, String nomModule) {
        this.idModule = idModule;
        this.nomModule = nomModule;
    }

    public Module(int idModule, String nomModule, ProgrammeEtude programme, List<Matiere> matieres) {
        this.idModule = idModule;
        this.nomModule = nomModule;
        this.programme = programme;
        this.matieres = matieres;
    }

    public Module(String module_3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Module() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdModule() {
        return idModule;
    }

    public void setIdModule(int idModule) {
        this.idModule = idModule;
    }

    public String getNomModule() {
        return nomModule;
    }

    public void setNomModule(String nomModule) {
        this.nomModule = nomModule;
    }

    public ProgrammeEtude getProgramme() {
        return programme;
    }

    public void setProgramme(ProgrammeEtude programme) {
        this.programme = programme;
    }

    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }

  

    
}