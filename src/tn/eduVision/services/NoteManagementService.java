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
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.services.SaisiNotesService;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

public class NoteManagementService implements SaisiNotesService {
    private Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public NoteManagementService() {
        _connection = SqlConnectionManager.getInstance().getConnection();
    }

    public boolean isNoteExists(Etudiant etudiant, Matiere matiere) {
        List<Note> notes = getAllNotes();

        for (Note note : notes) {
            if (note.getEtudiant().equals(etudiant) && note.getMatiere().equals(matiere)) {
                return true;
            }
        }

        return false; // Note does not exist for the given Etudiant and Matiere
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes";
        try {
            PreparedStatement statement = _connection.prepareStatement(query);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_note");
                    float noteValue = resultSet.getFloat("note");
                    int etudiantId = resultSet.getInt("id_utilisateur");
                    int matiereId = resultSet.getInt("id_matiere");

                    Etudiant etudiant = obtenirEtudiantParId(etudiantId);
                    Matiere matiere = obtenirMatiereParId(matiereId);

                    Note note = new Note(id, etudiant, matiere, noteValue);
                    notes.add(note);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public void saisirNote(Etudiant etudiant, Matiere matiere, float note) {
        String query = "INSERT INTO notes (id_utilisateur, id_matiere, note) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = _connection.prepareStatement(query);
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
        try {
            PreparedStatement statement = _connection.prepareStatement(query);
            statement.setInt(1, etudiant.getIdUtilisateur());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_note");
                    float noteValue = resultSet.getFloat("note");
                    Matiere matiere = obtenirMatiereParId(resultSet.getInt("id_matiere"));

                    Note note = new Note(id, etudiant, matiere, noteValue);
                    notes.add(note);
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
        try {
            PreparedStatement statement = _connection.prepareStatement(query);
            statement.setInt(1, matiere.getIdMatiere());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_note");
                    float noteValue = resultSet.getFloat("note");
                    Etudiant etudiant = obtenirEtudiantParId(resultSet.getInt("id_utilisateur"));

                    Note note = new Note(id, etudiant, matiere, noteValue);
                    notes.add(note);
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
        try {
            PreparedStatement statement = _connection.prepareStatement(query);
            statement.setInt(1, etudiant.getIdUtilisateur());
            statement.setInt(2, matiere.getIdMatiere());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_note");
                    float noteValue = resultSet.getFloat("note");

                    Note note = new Note(id, etudiant, matiere, noteValue);
                    notes.add(note);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public void modifierNote(int idNote, float nouvelleNote) {
        String query = "UPDATE notes SET note = ? WHERE id_note = ?";
        try {
            PreparedStatement statement = _connection.prepareStatement(query);
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
        try {
            PreparedStatement statement = _connection.prepareStatement(query);
            statement.setInt(1, idNote);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Matiere obtenirMatiereParId(int idMatiere) {
        Matiere matiere = null;
        String query = "SELECT * FROM matieres WHERE id_matiere = ?";
        try {
            PreparedStatement statement = _connection.prepareStatement(query);
            statement.setInt(1, idMatiere);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_matiere");
                    String nom = resultSet.getString("nom_matiere");

                    matiere = new Matiere(id, nom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matiere;
    }

    private Etudiant obtenirEtudiantParId(int idEtudiant) {
        Etudiant etudiant = null;
        String query = "SELECT * FROM utilisateurs WHERE id_utilisateur = ?";
        try {
            PreparedStatement statement = _connection.prepareStatement(query);
            statement.setInt(1, idEtudiant);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_utilisateur");
                    // Récupérer les autres informations de l'étudiant à partir de la base de données
                    // et les assigner à l'objet Etudiant
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    // ...

                    etudiant = new Etudiant(id,nom,prenom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiant;
    }

    // ...

    public void closeConnection() {
        try {
            if (_connection != null) {
                _connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
