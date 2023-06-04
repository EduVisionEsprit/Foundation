/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.util.List;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.SessionExamen;

/**
 *
 * @author bhsan
 */
public interface ReleveNotesService {
    void creerReleveNotes(List<Etudiant> etudiants, SessionExamen sessionExamen,Matiere m);
    void genererAttestationReussite(Etudiant etudiant, SessionExamen sessionExamen,Matiere m);
    void genererListeElites(Matiere matiere, SessionExamen sessionExamen,Matiere m);
}

   

