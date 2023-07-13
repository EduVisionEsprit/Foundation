package tn.eduVision.entités;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;
import tn.eduVision.services.NoteManagementService;

public class Etudiant extends Utilisateur {
    private int anneeInscrit;
    private int nombreAbsences;
    private List<Absence> absences;
    private List<Note> notes;

     
    public Etudiant() {
        notes = new ArrayList<>();  
    }
  
   public double getMoyenne() {
     
    double total = 0;
    int count = 0;
        NoteManagementService noteManagementService = new NoteManagementService();  

    List<Note> fetchedNotes = noteManagementService.obtenirNotesParEtudiant(this);
    
    for (Note note : fetchedNotes) {
        total += note.getNote();
        count++;
    }
    
    if (count != 0) {
        return total / count;
    } else {
        return 0.0;  
    }
}
public String getResultat() {
    double moyenne = getMoyenne();
    
    if (moyenne >= 10.0 && moyenne < 14.0) {
        return "Réussite";
    } else if (moyenne >= 14.0 && moyenne < 16.0) {
        return "Réussite avec mention Bien";
    } else if (moyenne >= 16.0) {
        return "Réussite avec mention Très Bien";
    } else {
        return "Redouble";
    }
}

    public StringProperty resultatProperty() {
    return new SimpleStringProperty(getResultat());
}


 public Etudiant(int idUtilisateur, String nom, String prenom) {
        super(idUtilisateur, nom, prenom);
    }

    public Etudiant(int idEtudiant) {
        super();
        this.setIdUtilisateur(idEtudiant);
    }

    public Etudiant(int anneeInscrit, int nombreAbsences, List<Absence> absences, List<Note> notes, int idUtilisateur, String nom, String prenom, String email, String motDePasse, Role role) {
        super(idUtilisateur, nom, prenom, email, motDePasse, role);
        this.anneeInscrit = anneeInscrit;
        this.nombreAbsences = nombreAbsences;
        this.absences = absences;
        this.notes = notes;
    }

    public int getAnneeInscrit() {
        return anneeInscrit;
    }

    public void setAnneeInscrit(int anneeInscrit) {
        this.anneeInscrit = anneeInscrit;
    }

    public int getNombreAbsences() {
        return nombreAbsences;
    }

    public void setNombreAbsences(int nombreAbsences) {
        this.nombreAbsences = nombreAbsences;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return getNom();
    }
}
