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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.Question;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author sebte
 */
public class QuestionService {

    private final Connection _connection = SqlConnectionManager.getInstance().getConnection();
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private static PreparedStatement statement = null;

    public void add(Question q, int idParent, String type) {
        String tableName = "";
        if (type.equals("quiz")) {
            tableName = "questionquiz";
        } else if (type.equals("test")) {
            tableName = "questiontest";
        }
        try {
            String addquery = "INSERT INTO `" + tableName + "` (id" + type + ", designation, reponse_correcte, reponse_fausse1, reponse_fausse2, reponse_fausse3, notetest) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            statement = _connection.prepareStatement(addquery);
            statement.setInt(1, idParent);
            statement.setString(2, q.getQuestionPosee());
            statement.setString(3, q.getReponseCorrecte());
            statement.setString(4, q.getReponseFausse1());
            statement.setString(5, q.getReponseFausse2());
            statement.setString(6, q.getReponseFausse3());
            statement.setInt(7, q.getNote());

            statement.executeUpdate();

        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());

        }
    }

    public void deleteQuestion(Question q, String type) {
        String tableName = "";
        if (type.equals("quiz")) {
            tableName = "questionquiz";
        } else if (type.equals("test")) {
            tableName = "questiontest";
        }

        try {
            String deleteQuery = "DELETE FROM `" + tableName + "` WHERE id = ?";
            statement = _connection.prepareStatement(deleteQuery);
            statement.setInt(1, q.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }

    }

    public void deleteQuestionByIdParent(Question q, String type, int idParent) {
        String tableName = "";
        String idColumnName = "";
        if (type.equals("quiz")) {
            tableName = "questionquiz";
            idColumnName = "id_quiz";
        } else if (type.equals("test")) {
            tableName = "questiontest";
            idColumnName = "id_test";
        }

        try {
            String query = "DELETE FROM `" + tableName + "` WHERE id = ? AND " + idColumnName + " = ?";
            statement = _connection.prepareStatement(query);
            statement.setInt(1, q.getId());
            statement.setInt(2, idParent);
            statement.executeUpdate();
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    public ArrayList<Question> displayAllQuestions(String type, int idParent) throws NoDataFoundException {
        String tableName = "";
        ArrayList<Question> questions = new ArrayList<>();

        if (type.equals("quiz")) {
            tableName = "questionquiz";
        } else if (type.equals("test")) {
            tableName = "questiontest";
        }

        String query = "SELECT * FROM `" + tableName + "` WHERE id_" + type + " = ?";

        try (PreparedStatement statement = _connection.prepareStatement(query)) {
            statement.setInt(1, idParent);
            ResultSet resultSet = statement.executeQuery();

            boolean hasData = true;
            while (resultSet.next()) {
                // System.out.println("are you here");
                hasData = true;
                int questionId = resultSet.getInt(1);
                String designation = resultSet.getString(3);
                String reponseCorrecte = resultSet.getString(4);
                String reponseFausse1 = resultSet.getString(5);
                String reponseFausse2 = resultSet.getString(6);
                String reponseFausse3 = resultSet.getString(7);
                int note = resultSet.getInt(8);

                Question question = new Question(questionId, designation, reponseCorrecte, reponseFausse1, reponseFausse2, reponseFausse3, note);
                question.setType(type);
                questions.add(question);
            }

            if (!hasData) {
                throw new NoDataFoundException("No questions found for the specified type and id");
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
            throw new NoDataFoundException("An error occurred while retrieving questions");
        }

        return questions;
    }

    public void insertQuestion(Question q, int idParent, String type) {
        System.out.println("helllo3");
        String query = "";
        if (type.equals("quiz")) {
            query += "INSERT INTO  `questionquiz` (id_quiz,designation,reponse_correcte,reponse_fausse1,reponse_fausse2,reponse_fausse3,notetest) "
                    + "VALUES(" + idParent + ",'" + q.getQuestionPosee() + "','" + q.getReponseCorrecte() + "','"
                    + q.getReponseFausse1() + "','" + q.getReponseFausse2() + "','" + q.getReponseFausse3() + "'," + q.getNote() + ")";
        } else if (type.equals("test")) {
            query += "INSERT INTO  `questiontest` (id_test,designation,reponse_correcte,reponse_fausse1,reponse_fausse2,reponse_fausse3,notetest) "
                    + "VALUES(" + idParent + ",'" + q.getQuestionPosee() + "','" + q.getReponseCorrecte() + "','"
                    + q.getReponseFausse1() + "','" + q.getReponseFausse2() + "','" + q.getReponseFausse3() + "'," + q.getNote() + ")";
        }

        try {
            statement = _connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

   

    public boolean updateQuestion(Question q, String type) {
        String query = "";
        if (type.equals("quiz")) {
            query += "UPDATE questionquiz SET  designation = '" + q.getQuestionPosee()
                    + "', reponse_correcte = '" + q.getReponseCorrecte() + "', reponse_fausse1 = '" + q.getReponseFausse1()
                    + "',reponse_fausse2 = '" + q.getReponseFausse2() + "', reponse_fausse3 = '" + q.getReponseFausse3()
                    + "', note = " + q.getNote() + " WHERE id = " + q.getId() + " ";
        } else if (type.equals("test")) {
            query += "UPDATE questiontest SET designation = '" + q.getQuestionPosee()
                    + "', reponse_correcte = '" + q.getReponseCorrecte() + "', reponse_fausse1 = '" + q.getReponseFausse1()
                    + "',reponse_fausse2 = '" + q.getReponseFausse2() + "', reponse_fausse3 = '" + q.getReponseFausse3()
                    + "', note = " + q.getNote() + " WHERE id = " + q.getId() + " ";
        }

        try {
            statement = _connection.prepareStatement(query);
            return true;

        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        return false;
    }
 private void CloseStatment(PreparedStatement statment) {
        try {
            if (statment != null) {
                statment.close();
            }
        } catch (SQLException ex) {
        }
    }
}
