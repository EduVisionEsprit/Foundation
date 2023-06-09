/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    
    

  

    public Module() {
    }

    public Module(String nom) {
this.nomModule=nom;    }

    public Module(String nomModule, ProgrammeEtude selectedProgramme) {
this.nomModule=nomModule;
this.programme=selectedProgramme;   }
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

           public StringProperty nomModuleProperty() {
        return new SimpleStringProperty(nomModule);
    }

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(idModule);
    }




  
        public Object programmeProperty() {
    return this.getProgramme().descriptionProperty();


    }
        @Override
public String toString() {
    return nomModule;
}


    
}