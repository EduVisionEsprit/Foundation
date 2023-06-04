/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

/**
 *
 * @author bhsan
 */
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.SessionExamen;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

public class SessionExamenManagementService implements SessionExamenService {

    private Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public SessionExamenManagementService() {
        _connection = SqlConnectionManager.getInstance().getConnection();
    }
   

     @Override
    public void creerSessionExamen(Matiere matiere, LocalDate dateDebut, LocalDate dateFin) {
    String query = "INSERT INTO session_examen (id_matiere, date_debut, date_fin) VALUES (?, ?, ?)";
        try (PreparedStatement statement = _connection.prepareStatement(query)) {
            statement.setInt(1, matiere.getIdMatiere());
            statement.setDate(2, Date.valueOf(dateDebut));
            statement.setDate(3, Date.valueOf(dateFin));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }    }

    @Override
    public void modifierSessionExamen(int idSessionExamen, LocalDate dateDebut, LocalDate dateFin) {
        String query = "UPDATE session_examen SET date_debut = ?, date_fin = ? WHERE id_session_examen = ?";
        try (PreparedStatement statement = _connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(dateDebut));
            statement.setDate(2, Date.valueOf(dateFin));
            statement.setInt(3, idSessionExamen);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerSessionExamen(int idSessionExamen) {
        String query = "DELETE FROM session_examen WHERE id_session_examen = ?";
        try (PreparedStatement statement = _connection.prepareStatement(query)) {
            statement.setInt(1, idSessionExamen);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SessionExamen> obtenirSessionsExamen() {
        List<SessionExamen> sessionsExamen = new ArrayList<>();
        String query = "SELECT * FROM session_examen";
        try (Statement statement = _connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int idSessionExamen = resultSet.getInt("id_session_examen");
                int idMatiere = resultSet.getInt("id_matiere");
                LocalDate dateDebut = resultSet.getDate("date_debut").toLocalDate();
                LocalDate dateFin = resultSet.getDate("date_fin").toLocalDate();

                Matiere matiere = obtenirMatiereParId(idMatiere); 

                SessionExamen sessionExamen = new SessionExamen(idSessionExamen, matiere, dateDebut, dateFin);
                sessionsExamen.add(sessionExamen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessionsExamen;
    }



    @Override
    public SessionExamen obtenirSessionExamen(int idSessionExamen) {
        String query = "SELECT * FROM session_examen WHERE id_session_examen = ?";
        try (PreparedStatement statement = _connection.prepareStatement(query)) {
            statement.setInt(1, idSessionExamen);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idMatiere = resultSet.getInt("id_matiere");
                    LocalDate dateDebut = resultSet.getDate("date_debut").toLocalDate();
                    LocalDate dateFin = resultSet.getDate("date_fin").toLocalDate();

                    Matiere matiere = obtenirMatiereParId(idMatiere); // Méthode à implémenter pour obtenir une matière par son ID

                    return new SessionExamen(idSessionExamen, matiere, dateDebut, dateFin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode à implémenter pour obtenir une matière par son ID
    private Matiere obtenirMatiereParId(int idMatiere) {

    Matiere matiere = null;
    String query = "SELECT * FROM matieres WHERE id_matiere = ?";
    try (PreparedStatement statement = _connection.prepareStatement(query)) {
        statement.setInt(1, idMatiere);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id_matiere");
                String nom = resultSet.getString("nom_matiere");
                // Autres colonnes de la table matiere...

                matiere = new Matiere(id, nom);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return matiere;
}

    
    @Override
    public List<SessionExamen> obtenirSessionsExamenParMatiere(Matiere matiere) {
  List<SessionExamen> sessionsExamen = new ArrayList<>();
        String query = "SELECT * FROM session_examen WHERE id_matiere = ?";
        try (PreparedStatement statement = _connection.prepareStatement(query)) {
            statement.setInt(1, matiere.getIdMatiere());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idSessionExamen = resultSet.getInt("id_session_examen");
                    LocalDate dateDebut = resultSet.getDate("date_debut").toLocalDate();
                    LocalDate dateFin = resultSet.getDate("date_fin").toLocalDate();

                    SessionExamen sessionExamen = new SessionExamen(idSessionExamen, matiere, dateDebut, dateFin);
                    sessionsExamen.add(sessionExamen);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessionsExamen;
    }
}

