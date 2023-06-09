package tn.eduVision.entités;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matiere {
    private int idMatiere;
    private String nomMatiere;
    private Module module;

    public Matiere(int idMatiere, String nomMatiere, Module module) {
        this.idMatiere = idMatiere;
        this.nomMatiere = nomMatiere;
        this.module = module;
    }
    
    public Matiere(int idMatiere) {
     
    }
       public Matiere(int idMatiere, String nomMatiere) {
        this.idMatiere = idMatiere;
        this.nomMatiere = nomMatiere;
    }

    public Matiere() {
    }

    public int getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(int idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public StringProperty nomMatiereProperty() {
        return new SimpleStringProperty(nomMatiere);
    }

    public ObjectProperty<Module> moduleProperty() {
        return new SimpleObjectProperty<>(module);
    }

    public void setModule(String nom) {
this.nomMatiere=nom;     }
}
