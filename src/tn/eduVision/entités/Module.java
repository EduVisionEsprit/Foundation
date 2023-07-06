/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
 public List<Float> getCoefMatieres() {
  List<Float> matiereNames = new ArrayList<>();
    if (matieres != null) {
        for (Matiere matiere : matieres) {
            matiereNames.add(matiere.getCoef());
        } }     return matiereNames;
 }
public void setMatieres(List<String> nomMatieres) {
    if (nomMatieres != null) {
        List<Matiere> matieres = new ArrayList<>();
        for (String nomMatiere : nomMatieres) {
            Matiere matiere = new Matiere(nomMatiere);  
            matieres.add(matiere);
        }
        this.matieres = matieres;
    } else {
        this.matieres = null;
    }
}

           public StringProperty nomModuleProperty() {
        return new SimpleStringProperty(nomModule);
    }

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(idModule);
    }



private SimpleDoubleProperty coefTotal;

public SimpleDoubleProperty coefTotalProperty() {
    return coefTotal;
}

public double getCoefTotal() {
    return coefTotal.get();
}

public void setCoefTotal(double coefTotal) {
    this.coefTotal.set(coefTotal);
}

  
        public Object programmeProperty() {
    return this.getProgramme().descriptionProperty();


    }
        @Override
public String toString() {
    return nomModule;
}

public String MatieresAsString() {
    if (matieres == null || matieres.isEmpty()) {
        return "";
    }
    
    List<String> matiereNames = new ArrayList<>();
    for (Matiere matiere : matieres) {
        matiereNames.add(matiere.getNomMatiere());  
    }
    return String.join(", ", matiereNames);
}
public String getMatieresAsString() {
    if (matieres == null || matieres.isEmpty()) {
        return "";
    }

    List<String> matiereNames = new ArrayList<>();
    for (Matiere matiere : matieres) {
        matiereNames.add(matiere.getNomMatiere());  
    }
    return String.join(", ", matiereNames);
}

    public String getCoefMatieresString() {
    List<Float> matiereNames = new ArrayList<>();
    if (matieres != null) {
        for (Matiere matiere : matieres) {
            matiereNames.add(matiere.getCoef());
        }
    }
    return matiereNames.toString();
}

     public Module getModule() {
        return this;
    }
}