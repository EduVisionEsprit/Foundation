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
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Module;
import tn.eduVision.entités.Note;
import tn.eduVision.entités.SessionExamen;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author bhsan
 */
public class NoteManagementService implements SaisiNotesService{

   private Connection connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public NoteManagementService() {
        connection = SqlConnectionManager.getInstance().getConnection();
    }

    @Override
    public void saisirNote(Etudiant etudiant, Matiere matiere, float note) {
        String query = "INSERT INTO notes (id_utilisateur, id_matiere, note) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, etudiant.getIdUtilisateur());
            statement.setInt(2, matiere.getIdMatiere());
            statement.setFloat(3, note);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Note> obtenirNotesParEtudiant(Etudiant etudiant) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes WHERE id_utilisateur = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, etudiant.getIdUtilisateur());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_note");
                    float note = resultSet.getFloat("note");
                    Matiere matiere = obtenirMatiereParId(resultSet.getInt("id_matiere"));
                                        Note noteObj = new Note(id, etudiant, matiere, note);
                    notes.add(noteObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public List<Note> obtenirNotesParMatiere(Matiere matiere) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes WHERE id_matiere = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matiere.getIdMatiere());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_note");
                    float note = resultSet.getFloat("note");
                    Etudiant etudiant = obtenirEtudiantParId(resultSet.getInt("id_utilisateur"));
                    
                    // Créer une instance de Note avec les données récupérées
                    Note noteObj = new Note(id, etudiant, matiere, note);
                    notes.add(noteObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public List<Note> obtenirNotesParEtudiantEtMatiere(Etudiant etudiant, Matiere matiere) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes WHERE id_utilisateur = ? AND id_matiere = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, etudiant.getIdUtilisateur());
            statement.setInt(2, matiere.getIdMatiere());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_note");
                    float note = resultSet.getFloat("note");
                    
                    Note noteObj = new Note(id, etudiant, matiere, note);
                    notes.add(noteObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }
   @Override
    public void modifierNote(int idNote, float nouvelleNote) {
        String query = "UPDATE note SET notes = ? WHERE id_note = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setFloat(1, nouvelleNote);
            statement.setInt(2, idNote);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerNote(int idNote) {
        String query = "DELETE FROM notes WHERE id_note = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idNote);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        
    }}
        
        
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
                    int id = resultSet.getInt("id_utilisateur");
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



