/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.util.List;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Note;

/**
 *
 * @author bhsan
 */
public interface SaisiNotesService {
    void saisirNote(Etudiant etudiant, Matiere matiere, float note);
        void supprimerNote(int id_note);
        void modifierNote(int id_note,float Note);

    List<Note> obtenirNotesParEtudiant(Etudiant etudiant);
    List<Note> obtenirNotesParMatiere(Matiere matiere);
    List<Note> obtenirNotesParEtudiantEtMatiere(Etudiant etudiant, Matiere matiere);
}

