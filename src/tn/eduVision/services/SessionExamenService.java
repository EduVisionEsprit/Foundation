/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.time.LocalDate;
import java.util.List;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.SessionExamen;

/**
 *
 * @author bhsan
 */
public interface SessionExamenService {
    void creerSessionExamen(Matiere matiere, LocalDate dateDebut, LocalDate dateFin);
    void modifierSessionExamen(int idSessionExamen, LocalDate dateDebut, LocalDate dateFin);
    void supprimerSessionExamen(int idSessionExamen);
    List<SessionExamen> obtenirSessionsExamen();
    List<SessionExamen> obtenirSessionsExamenParMatiere(Matiere matiere);
    SessionExamen obtenirSessionExamen(int idSessionExamen);
}
