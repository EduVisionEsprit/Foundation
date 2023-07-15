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
public class ServiceGroupe{
    
      private static  Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private static PreparedStatement statement = null;
    private ServiceDepartement s;

    public ServiceGroupe() {
        _connection = SqlConnectionManager.getInstance().getConnection();
    }

    public void add(Groupe item) throws Exception {
        PreparedStatement groupeStatement = null;

        try {
            String insertGroupe = "INSERT INTO groupes (id,num,`email`,niveau,`specialite`,id_departement) VALUES (?,?,?,?,?,?)";
            groupeStatement = _connection.prepareStatement(insertGroupe, PreparedStatement.RETURN_GENERATED_KEYS);
            groupeStatement.setInt(1,item.getId());
            groupeStatement.setInt(2, item.getNum());
             groupeStatement.setString(3, item.getEmail());
             groupeStatement.setInt(4, item.getNiveau());
            groupeStatement.setString(5, item.getSpecialite());
            groupeStatement.setInt(6, item.getDepartement());

            int rowsAffected = groupeStatement.executeUpdate();

            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been inserted!");
                throw new UnsupportedOperationException();
            }

            ResultSet generatedKeys = groupeStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int groupeId = generatedKeys.getInt(1);
                item.setId(groupeId);
            }

            _logger.log(Level.INFO, "Insertion done");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
            throw new Exception(ex);

        } finally {
            closeStatement(groupeStatement);
        } 
    }
    public void update(int id, Groupe item) throws Exception {

        PreparedStatement groupeStatement = null ;
        try {
            String insertGroupe = "Update groupes SET num=?, email=?, niveau=?, specialite=?,id_departement=? where id="+id;
            groupeStatement = _connection.prepareStatement(insertGroupe, PreparedStatement.RETURN_GENERATED_KEYS);
            groupeStatement.setInt(1, item.getNum());
            groupeStatement.setString(2, item.getEmail());
            groupeStatement.setInt(3, item.getNiveau());
            groupeStatement.setString(4, item.getSpecialite());
            groupeStatement.setInt(5, item.getDepartement());

            int rowsAffected = groupeStatement.executeUpdate();
            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been inserted!");
                throw new UnsupportedOperationException();
            }

            ResultSet generatedKeys = groupeStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int groupeId = generatedKeys.getInt(1);
                item.setId(groupeId);
            }

            _logger.log(Level.INFO, "update done");
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
        throw new Exception(ex);
    } finally {
        closeStatement(groupeStatement);
    }
}

   public void delete(int id) {
        try {
            String deleteGroupeStatement = "DELETE FROM `groupes` WHERE `id` = ?;";
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
   
    public Groupe getById(int id) throws NoDataFoundException{
         Groupe groupe = this.getAll().stream().filter( m -> m.getId() == id).findFirst().get();
         if(groupe == null){
             throw new NoDataFoundException("pas de groupe avec id " + id);
         }
         return groupe;
         }

    public ObservableList<Groupe> getAll() throws NoDataFoundException {

        boolean HasData = false;
        ObservableList<Groupe> groupeList = (FXCollections.observableArrayList());
        try{
        String selectById = "select * from groupes INNER JOIN departement ON groupes.id_departement=departement.id;";
        statement = _connection.prepareStatement(selectById);
        ResultSet resultSet = statement.executeQuery();
        
        //custuction of the objects and adding to the list
        
        while(resultSet.next()){
            HasData = true;
            Groupe groupe;
            groupe = new Groupe(
                    resultSet.getInt("id"),
                    resultSet.getInt("num"),
                    resultSet.getString("email"),
                    resultSet.getInt("niveau"),
                    resultSet.getString("specialite"),
                    resultSet.getInt("id_departement"),
                    resultSet.getString("titre"));
                    groupeList.add(groupe);
        }
        }
        catch(SQLException ex ){
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        }
        return groupeList;
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
