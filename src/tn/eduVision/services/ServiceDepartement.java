/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.eduVision.entités.Departement;
import tn.eduVision.entités.Groupe;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author Sayf
 */
public class ServiceDepartement{

    private static  Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private static PreparedStatement statement = null;

    public ServiceDepartement() {
        _connection = SqlConnectionManager.getInstance().getConnection();
    }

    public void add(Departement item) {
        PreparedStatement depStatement = null;

        try {
            String insertGroupe = "INSERT INTO Departement (`id`,`titre`,`description`) VALUES (?,?,?)";
            depStatement = _connection.prepareStatement(insertGroupe, PreparedStatement.RETURN_GENERATED_KEYS);
            depStatement.setInt(1,item.getId());
            depStatement.setString(2, item.getTitre());
            depStatement.setString(3, item.getDescription());

            int rowsAffected = depStatement.executeUpdate();

            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been inserted!");
                throw new UnsupportedOperationException();
            }

            ResultSet generatedKeys = depStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int depId = generatedKeys.getInt(1);
                item.setId(depId);
            }

            _logger.log(Level.INFO, "Insertion done");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            closeStatement(depStatement);
        }
    }
    public void update(int id, Departement item) throws SQLException {
        PreparedStatement depStatement = null ;

        try {
            String insertDep = "Update departement SET titre=?,description=? where id="+id;
            depStatement = _connection.prepareStatement(insertDep, PreparedStatement.RETURN_GENERATED_KEYS);
            depStatement.setString(1, item.getTitre());
            depStatement.setString(2, item.getDescription());

            int rowsAffected = depStatement.executeUpdate();
            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been inserted!");
                throw new UnsupportedOperationException();
            }

            ResultSet generatedKeys = depStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int groupeId = generatedKeys.getInt(1);
                item.setId(groupeId);
            }

            _logger.log(Level.INFO, "update done");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    public void delete(int id) {
        try {
            String deleteGroupeStatement = "DELETE FROM `departement` WHERE `id` = ?;";
            statement = _connection.prepareStatement(deleteGroupeStatement);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been deleted!");
                throw new UnsupportedOperationException();
            }

            _logger.log(Level.INFO, "Deleted");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            closeStatement(statement);
        }
    }

    public Departement getById(int id) throws NoDataFoundException{
        Departement dep = this.getAll().stream().filter( m -> m.getId() == id).findFirst().get();
        if(dep == null){
            throw new NoDataFoundException("pas de departement avec id " + id);
        }
        return dep;
    }

    public ObservableList<Departement> getAll() throws NoDataFoundException {

        boolean HasData = false;
        ObservableList<Departement> depList = (FXCollections.observableArrayList());
        try{
            String selectById = "select id,titre,description from departement";
            statement = _connection.prepareStatement(selectById);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                HasData = true;
                Departement dep;
                dep = new Departement(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"));
                depList.add(dep);
            }
        }
        catch(SQLException ex ){
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        }
        return depList;
    }

    private void closeStatement(PreparedStatement statment){
        try{
            if(statment !=null){
                statment.close();
            }
        }
        catch(SQLException ex){
        }
    }
}
