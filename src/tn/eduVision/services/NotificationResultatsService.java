/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.SessionExamen;

/**
 *
 * @author bhsan
 */
public interface NotificationResultatsService {
      void PublierResultats(Etudiant etudiant, Matiere m);
}
