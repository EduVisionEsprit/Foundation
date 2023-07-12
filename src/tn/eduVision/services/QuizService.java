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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.eduVision.entités.Question;
import tn.eduVision.entités.Quiz;
import tn.eduVision.entités.Salle;
import tn.eduVision.entités.TypeSalle;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author sebte
 */
public class QuizService implements Iservices<Quiz> {

    private final Connection _connection = SqlConnectionManager.getInstance().getConnection();
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private static PreparedStatement statement = null;

    @Override
    public void add(Quiz q) {

        try {

            String insertQuiz = "INSERT INTO `quiz` "
                    + "(`id_utilisateur`, `sujet`) "
                    + "VALUES (?, ?);";
            statement = _connection.prepareStatement(insertQuiz);
            statement.setInt(1, q.getIdFormateur());
            statement.setString(2, q.getSujet());

            statement.executeUpdate();

        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());

        }

    }

    @Override
    public void update(Quiz q) {
        try {

            String updateQuiz = "UPDATE `quiz  ` SET `sujet` = ?"
                    + "WHERE id= ? ";
            PreparedStatement statement = _connection.prepareStatement(updateQuiz);
            statement.setString(1, q.getSujet());
            statement.setInt(2, q.getIdFormateur());
            statement.executeUpdate();
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            CloseStatment(statement);
        }
    }

    @Override
    public void delete(Quiz q) {
        try {

            String insertQuiz = "DELETE from test WHERE id = " + q.getId() + " ";;
            statement = _connection.prepareStatement(insertQuiz);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                _logger.log(Level.INFO, "Delete operation successful. Rows deleted: {0}", rowsDeleted);
            } else {
                _logger.log(Level.INFO, "No rows deleted Delete operation failed.");
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            CloseStatment(statement);
        }
    }

    @Override
    public Quiz getById(int id) throws NoDataFoundException {

        Quiz q = this.getAll().stream().filter(e -> e.getId() == id).findFirst().get();
        if (q == null) {
            throw new NoDataFoundException("pas de quiz avec id " + id);
        }
        return q;
    }

    @Override
    public List<Quiz> getAll() throws NoDataFoundException {
        //retrive the data to cunstruct the object

        return null;

    }

    private void CloseStatment(PreparedStatement statment) {
        try {
            if (statment != null) {
                statment.close();
            }
        } catch (SQLException ex) {
        }
    }

    public ArrayList<Question> displayAllQuestions(String type, int idParent) {
        String query = "";
        ArrayList<Question> questions = new ArrayList<>();

        if (type.equals("quiz")) {
            query += "SELECT * FROM `questionquiz` WHERE id_quiz = " + idParent + "";
        } else if (type.equals("test")) {
            query += "SELECT * FROM `questiontest` WHERE id_test = " + idParent + "";
        }

        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Question q = new Question(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getInt(8));
                q.setType(type);
                questions.add(q);
            }

        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        return questions;
    }

    public ObservableList<Quiz> displayQuizList() {
        String query = "SELECT * FROM quiz ";
        ObservableList<Quiz> listQuiz = FXCollections.observableArrayList();
        try {
            PreparedStatement st = _connection.prepareStatement(query);
            ResultSet rs = st.executeQuery(query);
            QuestionService qdao = new QuestionService();
            while (rs.next()) {
                ArrayList<Question> questions = qdao.displayAllQuestions("quiz", rs.getInt("id"));
                Quiz quiz = new Quiz(rs.getInt("id"), rs.getString("sujet"), questions);
                listQuiz.add(quiz);
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }

        return listQuiz;
    }

    public void insertQuiz(Quiz q, int idFormateur) {
        String query = "INSERT INTO quiz(id_utilisateur,sujet) VALUES(" + idFormateur + ",'" + q.getSujet() + "')";
        try {
            statement = _connection.prepareStatement(query);
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(QuizService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        QuestionService qdao = new QuestionService();
        Quiz qz = getQuizbySujet(q.getSujet(), idFormateur);
        for (Question question : q.getQuestions()) {
            
            qdao.insertQuestion(question, qz.getId(), "quiz");
        }

    }

    public Quiz getQuizbySujet(String sujet, int idFormateur) {
        Quiz q = new Quiz();
        String query = "SELECT *from quiz WHERE sujet='" + sujet + "' and id_utilisateur=" + idFormateur + "";
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                q.setId(rs.getInt("id"));
                q.setSujet(rs.getString("sujet"));
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        return q;
    }

    public ObservableList<Quiz> displayQuizList(int idFormateur) {
        String query = "SELECT *FROM quiz WHERE id_utilisateur = " + idFormateur + " ";
        ObservableList<Quiz> listQuiz = FXCollections.observableArrayList();
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            QuestionService qdao = new QuestionService();
            while (rs.next()) {
                ArrayList<Question> questions = qdao.displayAllQuestions("quiz", rs.getInt("id"));
                Quiz quiz = new Quiz(rs.getInt("id"), rs.getString("sujet"), questions);
                listQuiz.add(quiz);
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }

        return listQuiz;
    }

    public boolean updateQuiz(Quiz q, int idFromateur) {
        String query = "UPDATE quiz SET sujet = '" + q.getSujet() + "' WHERE id = " + q.getId() + "";
        try {
            statement = _connection.prepareStatement(query);
            statement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        return false;
    }

    public void deleteQuiz(Quiz q) {
        String query = "DELETE from quiz WHERE id = " + q.getId() + " ";
        Quiz quiz = this.getQuizById(q.getId());
        if (quiz != null) {
            try {
                QuestionService questDao = new QuestionService();
                for (Question question : q.getQuestions()) {
                    questDao.deleteQuestionByIdParent(question, "quiz", q.getId());
                }
                statement = _connection.prepareStatement(query);
                statement.executeUpdate();

            } catch (SQLException ex) {
                _logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }

    public Quiz getQuizById(int id) {
        String query = "SELECT *FROM quiz WHERE id = " + id + " ";
        Quiz quiz = new Quiz();
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            QuestionService qdao = new QuestionService();
            while (rs.next()) {
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("quiz", rs.getInt(1));
                quiz.setId(rs.getInt(1));
                quiz.setSujet(rs.getString(3));
                quiz.setQuestions(questions);
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        return quiz;
    }

    public List<Quiz> getAllQuiz(String value) {
        String query = "SELECT *FROM quiz where sujet LIKE '%" + value + "%' ";
        ArrayList<Quiz> listQuiz = new ArrayList<>();
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            QuestionService qdao = new QuestionService();
            while (rs.next()) {
                ArrayList<Question> questions = qdao.displayAllQuestions("quiz", rs.getInt(1));
                Quiz quiz = new Quiz(rs.getInt(1), rs.getString(3), questions);
                quiz.setIdFormateur(rs.getInt("id_utilisateur"));
                listQuiz.add(quiz);
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }

        return listQuiz;
    }
// hedhy a discuter fl integration
    public Utilisateur getUserById(int id) {
        Utilisateur u = new Utilisateur();
        String query = "select *from utilisateurs where id_utilisateur = " + id + "";
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                u.setNom(rs.getString("nom"));
                u.setIdUtilisateur(rs.getInt("id_utilisateur"));
                u.setPrenom(rs.getString("prenom"));
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        return u;
    }

}
