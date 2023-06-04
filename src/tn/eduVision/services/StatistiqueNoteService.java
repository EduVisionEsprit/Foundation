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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import tn.eduVision.entités.Matiere;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

public class StatistiqueNoteService {
    private Connection connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();

    public StatistiqueNoteService() {
        connection = SqlConnectionManager.getInstance().getConnection();
    }

    public float calculerMoyenneParMatiere(Matiere matiere) {
        float moyenne = 0;
        int count = 0;
        String query = "SELECT note FROM notes WHERE id_matiere = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matiere.getIdMatiere());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    moyenne += resultSet.getFloat("note");
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count > 0) {
            moyenne /= count;
        }
        return moyenne;
    }

    public float calculerEcartTypeParMatiere(Matiere matiere) {
        float moyenne = calculerMoyenneParMatiere(matiere);
        float ecartType = 0;
        int count = 0;
        String query = "SELECT note FROM notes WHERE id_matiere = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matiere.getIdMatiere());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    float note = resultSet.getFloat("note");
                    ecartType += Math.pow(note - moyenne, 2);
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count > 0) {
            ecartType = (float) Math.sqrt(ecartType / count);
        }
        return ecartType;
    }

    public float calculerTauxReussiteParMatiere(Matiere matiere) {
        int totalEtudiants = 0;
        int totalReussites = 0;
        String query = "SELECT COUNT(*) AS total FROM notes WHERE id_matiere = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matiere.getIdMatiere());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalEtudiants = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "SELECT COUNT(*) AS total FROM notes WHERE id_matiere = ? AND note >= 10";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matiere.getIdMatiere());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalReussites = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (totalEtudiants > 0) {
            return (float) totalReussites / totalEtudiants * 100;
        } else {
            return 0;
        }
    }
}

