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
import tn.eduVision.entités.Test;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author sebte
 */
public class TestService implements Iservices<Test> {

    private final Connection _connection = SqlConnectionManager.getInstance().getConnection();
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private static PreparedStatement statement = null;

    @Override
    public void add(Test test) {

        try {
            String addquery = "INSERT INTO test(id_utilisateur, id_formation, sujet, duree) VALUES (?, ?, ?, ?)";
            statement = _connection.prepareStatement(addquery);

            statement.setInt(1, test.getUtilisateur().getIdUtilisateur());
            statement.setInt(2, test.getIdFormation());
            statement.setString(3, test.getSujet());
            statement.setInt(4, test.getDuree());
            statement.executeUpdate();
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void update(Test test) {

    }

    @Override
    public void delete(Test item) {
        try {
            String deletequery = "DELETE FROM test WHERE id_test = ?";
            statement = _connection.prepareStatement(deletequery);

            statement.setInt(1, item.getUtilisateur().getIdUtilisateur());
            statement.setInt(2, item.getIdFormation());
            statement.setString(3, item.getSujet());
            statement.setInt(4, item.getDuree());
            statement.executeUpdate();
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

 

    public ObservableList<Test> getListTest() {
        String query = "SELECT * FROM test";
        ObservableList<Test> listTest = FXCollections.observableArrayList();
        try {
            Statement st = _connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            QuestionService qdao = new QuestionService();
            while (rs.next()) {
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("test", 21);
                Test t = new Test(rs.getInt(1), rs.getString("sujet"), questions);
                t.setIdFormateur(rs.getInt("id_utilisateur"));
                t.setDuree(rs.getInt("duree"));
                t.setNbEtudiantsAdmis(rs.getInt("nb_etudiants_admis"));
                t.setNbEtudiantsPasses(rs.getInt("nb_etudiant_passes"));
                int duree = rs.getInt("duree");
                int hours = (duree / 3600);
                int min = ((duree % 3600) / 60);
                t.setTemps(hours + "h" + min + "min");
                listTest.add(t);
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }

        return listTest;
    }

    public void insertTest(Test t, int idFormateur) {
        String query = "INSERT INTO test(id_utilisateur,nb_etudiant_passes,nb_etudiants_admis,sujet,duree) "
                + "VALUES(" + idFormateur + "," + 0 + "," + 0 + ",'" + t.getSujet() + "'," + t.getDuree() + ")";
        try {
            statement = _connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }

        QuestionService qdao = new QuestionService();
        Test test = getTestbySujet(t.getSujet(), idFormateur);
        for (Question question : t.getQuestions()) {
            qdao.insertQuestion(question, test.getId(), "test");
        }

    }

    public Test getTestbySujet(String sujet, int idFormateur) {
        Test t = new Test();
        String query = "SELECT *from test WHERE sujet='" + sujet + "' and id_utilisateur=" + idFormateur + "";
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                t.setId(rs.getInt("id"));
                t.setSujet(rs.getString("sujet"));
                t.setIdFormation(rs.getInt("id_utilisateur"));
                t.setDuree(rs.getInt("duree"));
                int duree = rs.getInt("duree");
                int hours = (duree / 3600);
                int min = ((duree % 3600) / 60);
                t.setTemps(hours + "h" + min + "min");
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        return t;
    }

    public ObservableList<Test> displayTestList(int idFormateur) {
        String query = "SELECT * FROM test WHERE id_utilisateur= " + idFormateur + " ";
        ObservableList<Test> listTest = FXCollections.observableArrayList();
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            QuestionService qdao = new QuestionService();
            while (rs.next()) {
                ArrayList<Question> questions = qdao.displayAllQuestions("test", rs.getInt("id"));
                Test test = new Test(rs.getInt("id"), rs.getString("sujet"), questions);
                test.setIdFormation(rs.getInt("id_utilisateur"));
                test.setDuree(rs.getInt("duree"));
                test.setNbEtudiantsAdmis(rs.getInt("nb_etudiants_admis"));
                test.setNbEtudiantsPasses(rs.getInt("nb_etudiant_passes"));
                int duree = rs.getInt("duree");
                int hours = (duree / 3600);
                int min = ((duree % 3600) / 60);
                test.setTemps(hours + "h" + min + "min");
                listTest.add(test);
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }

        return listTest;
    }

    public boolean updateTest(Test t, int idFromateur) {
        String query = "UPDATE test SET sujet = '" + t.getSujet() + "',duree = " + t.getDuree() + " where id = " + t.getId() + "";
        try {
            statement = _connection.prepareStatement(query);
            statement.executeUpdate();
          
            return true;
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        return false;
    }

    public void deleteTest(Test t) {
        String query = "DELETE from test WHERE id = " + t.getId() + " ";
        Test test = this.getTestById(t.getId());
        if (test != null) {
            try {
                QuestionService questDao = new QuestionService();
                for (Question question : t.getQuestions()) {
                    questDao.deleteQuestionByIdParent(question, "test", t.getId());
                }
                statement = _connection.prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException ex) {
                _logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }

    public Test getTestById(int id) {
        String query = "SELECT *FROM test WHERE id = " + id + " ";
        Test test = new Test();
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            QuestionService qdao = new QuestionService();
            while (rs.next()) {
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("test", rs.getInt(1));
                test.setId(rs.getInt(1));
                test.setSujet(rs.getString("sujet"));
                test.setQuestions(questions);
                test.setDuree(rs.getInt("duree"));
                int duree = rs.getInt("duree");
                int hours = (duree / 3600);
                int min = ((duree % 3600) / 60);
                test.setTemps(hours + "h" + min + "min");
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        return test;
    }

    public boolean updateNbreEtudiantsPasses(int idTest) {
        String query = "UPDATE test set nb_etudiant_passes = nb_etudiant_passes + 1 where id=" + idTest + "";
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

    public boolean updateNbreEtudiantsAdmis(int idTest) {
        String query = "UPDATE test set nb_etudiants_admis = nb_etudiants_admis + 1 where id=" + idTest + "";
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

    public List<Test> getAllTest(String value) {
        String query = "SELECT *FROM test where sujet LIKE '%" + value + "%'";
        ArrayList<Test> listTest = new ArrayList<>();
        try {
            statement = _connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            QuestionService qdao = new QuestionService();
            while (rs.next()) {
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("test", rs.getInt(1));
                Test t = new Test(rs.getInt(1), rs.getString("sujet"), questions);
                t.setIdFormateur(rs.getInt("id_utilisateur"));
                t.setDuree(rs.getInt("duree"));
                int duree = rs.getInt("duree");
                int hours = (duree / 3600);
                int min = ((duree % 3600) / 60);
                t.setTemps(hours + "h" + min + "min");
                listTest.add(t);
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }

        return listTest;
    }
    
       @Override
    public Test getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Test> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
