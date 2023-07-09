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
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.NoteTest;
import tn.eduVision.entités.Ressource;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author sebte
 */
public class NoteTestService {

    private final Connection _connection = SqlConnectionManager.getInstance().getConnection();
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private static PreparedStatement statement = null;

    public NoteTest getNoteById(int idEtudiant, int idTest) {
        NoteTest n = null;
        String query = "SELECT *FROM notetest WHERE id_test=" + idTest + " and id_etudiant = " + idEtudiant + "";
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                n = new NoteTest();
                n.setId(rs.getInt("id"));
                n.setIdTest(rs.getInt("id_test"));
                n.setIdEtudiant(rs.getInt("id_etudiant"));
                n.setNoteObtnue(rs.getInt("note_obtenue"));

            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());

        }

        if (n != null) {
            return n;
        }

        return null;
    }

    public boolean insertNote(NoteTest n) {
        NoteTest note = getNoteById(n.getIdEtudiant(), n.getIdTest());
        if (note == null) {
            String query = "INSERT INTO notetest (id_etudiant,id_test,note_obtenue) "
                    + "VALUES(" + n.getIdEtudiant() + "," + n.getIdTest() + "," + n.getNoteObtnue() + ")";
            try {

                statement = _connection.prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException ex) {
                _logger.log(Level.SEVERE, ex.getMessage());
            }
            return true;
        } else {
            if (note.getNoteObtnue() < n.getNoteObtnue()) {
                updateNote(n.getNoteObtnue(), note.getId());
            }
            return false;
        }
    }

    public boolean updateNote(int note, int id) {
        String query = "UPDATE note set note_obtenue = " + note + " where id = " + id + "";
        try {
            statement = _connection.prepareStatement(query);
            int updatedRow = statement.executeUpdate();
            if (updatedRow > 0) {
                return true;
            }

        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());

        }
        return false;
    }
}
