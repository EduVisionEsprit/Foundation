package tn.eduVision.entités;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matiere {
    private int idMatiere;
    private String nomMatiere;
    private Module module;
    private float coef;

    public Matiere(int idMatiere, String nomMatiere, Module module, float coef) {
        this.idMatiere = idMatiere;
        this.nomMatiere = nomMatiere;
        this.module = module;
        this.coef = coef;
    }
     public String tooString() {
        if (nomMatiere != null) {
            return nomMatiere;
        } else {
            return "";
        }
    }

    
     public Matiere( String nomMatiere) {
        this.nomMatiere = nomMatiere;
    
    }
     public Matiere(int idMatiere, String nomMatiere, Module module) {
        this.idMatiere = idMatiere;
        this.nomMatiere = nomMatiere;
        this.module = module;
        this.coef = coef;
    }

    public Matiere(int idMatiere) {
        this.idMatiere = idMatiere;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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


    public double getMatiereCoef(String nomMatiere) {
        if (this.nomMatiere.equals(nomMatiere)) {
            return coef;
        }
        return 0.0;  
    }
    public float getCoef() {
        return coef;
    }

    public void setCoef(float coef) {
        this.coef = coef;
    }

    public StringProperty nomMatiereProperty() {
        return new SimpleStringProperty(nomMatiere);
    }

    public ObjectProperty<Module> moduleProperty() {
        return new SimpleObjectProperty<>(module);
    }

    public StringProperty getNomProperty() {
        return new SimpleStringProperty(nomMatiere);
    }
    
    public StringProperty nomProperty() {
        return new SimpleStringProperty(nomMatiere);
    }

    @Override
    public String toString() {
        return getNomMatiere();
    }
    
    public List<String> getModuleNames() {
        List<String> moduleNames = new ArrayList<>();
        if (module != null) {
            for (Matiere matiere : module.getMatieres()) {
                moduleNames.add(matiere.getNomMatiere());
            }
        }
        return moduleNames;
    }
    
    
    public List<String> getModuleNames2() {
        List<String> moduleNames = new ArrayList<>();
        if (module != null) {
            moduleNames.add(module.getNomModule());
        }
        return moduleNames;
    }
}
