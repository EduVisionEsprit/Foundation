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
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Matiere;
    import tn.eduVision.entités.Seance;

import tn.eduVision.tools.SqlConnectionManager;
/**
 *
 * @author bhsan
 */
public class AbscenceManagementService {


    private Connection connection;

    public AbscenceManagementService() {
        connection = SqlConnectionManager.getInstance().getConnection();
    }

    public void gererAbsences(Etudiant etudiant, Seance s, Matiere matiere) {
        int totalAbsences = compterAbsences(etudiant, s);
        if (totalAbsences > 3) {
            eliminerDeSessionPrincipale(etudiant, matiere);
        }
    }

    private int compterAbsences(Etudiant etudiant, Seance s) {
        int totalAbsences = 0;
        String query = "SELECT COUNT(*) AS total FROM absences WHERE id_utilisateur = ? AND id_seance = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, etudiant.getIdUtilisateur());
            statement.setInt(2,s.getIdSeance());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalAbsences = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalAbsences;
    }

  private void eliminerDeSessionPrincipale(Etudiant etudiant, Matiere matiere) {
    String query = "DELETE FROM session_examen WHERE id_utilisateur = ? AND id_matiere = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, etudiant.getIdUtilisateur());
        statement.setInt(2, matiere.getIdMatiere());
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



}
