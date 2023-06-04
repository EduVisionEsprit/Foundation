/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Note;
import tn.eduVision.entités.SessionExamen;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author bhsan
 */
public class ReleveNotesManagementService implements ReleveNotesService{

    private Connection connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public ReleveNotesManagementService() {
        connection = SqlConnectionManager.getInstance().getConnection();
    }

    @Override
    public void creerReleveNotes(List<Etudiant> etudiants, SessionExamen sessionExamen,Matiere m) {
        for (Etudiant etudiant : etudiants) {
            List<Note> notes = obtenirNotesParEtudiantEtSession(etudiant, sessionExamen, m);
            float moyenne = calculerMoyenne(notes);
            String mention = determinerMention(moyenne);

            // Logique pour créer le relevé de notes dans la base de données
            String query = "INSERT INTO releve_notes (id_utilisateur, id_session_examen, moyenne, mention) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, etudiant.getIdUtilisateur());
                statement.setInt(2, sessionExamen.getIdSessionExamen());
                statement.setFloat(3, moyenne);
                statement.setString(4, mention);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void genererAttestationReussite(Etudiant etudiant, SessionExamen sessionExamen,Matiere m) {
        float moyenne = obtenirMoyenneParEtudiantEtSession(etudiant, sessionExamen,m);
        String mention = determinerMention(moyenne);

        // Logique pour générer l'attestation de réussite dans la base de données
        String query = "INSERT INTO attestation_reussite (id_etudiant, id_session_examen, moyenne, mention) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, etudiant.getIdUtilisateur());
            statement.setInt(2, sessionExamen.getIdSessionExamen());
            statement.setFloat(3, moyenne);
            statement.setString(4, mention);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void genererListeElites(Matiere matiere, SessionExamen sessionExamen,Matiere m) {
        List<Note> notes = obtenirNotesParMatiereEtSession(matiere, sessionExamen);
        List<Etudiant> elites = determinerListeElites(notes);

        // Logique pour générer la liste des élites dans la base de données
        for (Etudiant etudiant : elites) {
            String query = "INSERT INTO liste_elites (id_etudiant, id_session_examen, id_matiere) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, etudiant.getIdUtilisateur());
                statement.setInt(2, sessionExamen.getIdSessionExamen());
                statement.setInt(3, matiere.getIdMatiere());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }   

   private List<Note> obtenirNotesParEtudiantEtSession(Etudiant etudiant, SessionExamen sessionExamen, Matiere m) {
    List<Note> notes = new ArrayList<>();
    String query = "SELECT * FROM notes WHERE id_etudiant = ? AND id_matiere = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, etudiant.getIdUtilisateur());
        statement.setInt(2, m.getIdMatiere());
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idNote = resultSet.getInt("id_note");
                float note = resultSet.getFloat("note");
                // Récupérer l'objet Matiere associé à la note
                Matiere matiere = obtenirMatiereParId(resultSet.getInt("id_matiere"));

                Note noteObj = new Note(idNote, etudiant, matiere, note);
                notes.add(noteObj);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return notes;
}

// Méthode utilitaire pour calculer la moyenne à partir d'une liste de notes
private float calculerMoyenne(List<Note> notes) {
    float somme = 0;
    for (Note note : notes) {
        somme += note.getNote();
    }
    return somme / notes.size();
}

// Méthode utilitaire pour déterminer la mention en fonction de la moyenne
private String determinerMention(float moyenne) {
    if (moyenne >= 16) {
        return "Très bien";
    } else if (moyenne >= 14) {
        return "Bien";
    } else if (moyenne >= 12) {
        return "Assez bien";
    } else if (moyenne >= 10) {
        return "Passable";
    } else {
        return "Insuffisant";
    }
}

// Méthode utilitaire pour obtenir la moyenne d'un étudiant pour une session d'examen donnée
private float obtenirMoyenneParEtudiantEtSession(Etudiant etudiant, SessionExamen sessionExamen,Matiere m) {
    List<Note> notes = obtenirNotesParEtudiantEtSession(etudiant,sessionExamen, m);
    return calculerMoyenne(notes);
}

// Méthode utilitaire pour obtenir les notes d'une matière pour une session d'examen donnée
private List<Note> obtenirNotesParMatiereEtSession(Matiere matiere, SessionExamen sessionExamen) {
    List<Note> notes = new ArrayList<>();
    String query = "SELECT * FROM note WHERE id_matiere = ? AND id_session_examen = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, matiere.getIdMatiere());
        statement.setInt(2, sessionExamen.getIdSessionExamen());
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idNote = resultSet.getInt("id_note");
                float note = resultSet.getFloat("note");
                // Récupérer l'objet Etudiant associé à la note
                Etudiant etudiant = obtenirEtudiantParId(resultSet.getInt("id_etudiant"));

                Note noteObj = new Note(idNote, etudiant, matiere, note);
                notes.add(noteObj);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return notes;
}

private List<Etudiant> determinerListeElites(List<Note> notes) {
    List<Etudiant> elites = new ArrayList<>();
    float seuilElites = 16; 

    for (Note note : notes) {
        if (note.getNote() >= seuilElites) {
            elites.add((Etudiant) note.getUtilisateur());
        }
    }
    return elites;
}

   public Matiere obtenirMatiereParId(int idMatiere) {
        String query = "SELECT * FROM matieres WHERE id_matiere = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idMatiere);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_matiere");
                    String nom = resultSet.getString("nom");

                    Matiere matiere = new Matiere(id, nom);
                    return matiere;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Etudiant obtenirEtudiantParId(int idEtudiant) {
        String query = "SELECT * FROM utilisateurs WHERE id_utilisateur = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEtudiant);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_etudiant");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                
                    Etudiant etudiant = new Etudiant();

                    return etudiant;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

 
    
    
 
    
    
}
