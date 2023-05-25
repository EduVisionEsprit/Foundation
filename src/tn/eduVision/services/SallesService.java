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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.Salle;
import tn.eduVision.entités.TypeSalle;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author job_j
 */
public class SallesService implements Iservices<Salle> {

    private final Connection _connection = SqlConnectionManager.getConnection();
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    @Override
    public void add(Salle salle) {
        try{
            
            String insertSalle = "INSERT INTO `ressources` "
                    + "(`type_ressource`, `type_salle`, `nom_salle`, `capacite`, `equipements`, `disponibilite`) "
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = _connection.prepareStatement(insertSalle);
            statement.setString(1, salle.getTypeRessource().name());
            statement.setString(2, salle.getTypeSalle().toString());
            statement.setString(3, salle.getNomSalle());
            statement.setInt(4, salle.getCapacite());
            statement.setString(5, salle.getEquipements());
            statement.setString(6, salle.getDisponibilite());
            
            statement.executeUpdate();
        }
        catch(SQLException ex){
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void update(Salle salle) {
        try{
            
            String updateSalle = "UPDATE `ressources` SET `nom_salle` = ? , `equipements` = ?, `type_salle` = ? , `capacite` = ?, `type_ressource` = ?, `disponibilite` = ?"
                    + "WHERE `ressources`.`id_ressource` = ?;";
            PreparedStatement statement = _connection.prepareStatement(updateSalle);
            statement.setString(1, salle.getNomSalle());
            statement.setString(2, salle.getEquipements());
            statement.setString(3, salle.getTypeSalle().name());
            statement.setInt(4, salle.getCapacite());
            statement.setString(5, salle.getTypeRessource().name());
            statement.setString(6, salle.getDisponibilite());
            statement.setInt(7, salle.getIdRessource());
            statement.executeUpdate();
        }
        catch(SQLException ex){
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void delete(Salle salle) {
        try{
            
            String insertSalle = "DELETE FROM ressources WHERE `ressources`.`id_ressource` =  ?";
            PreparedStatement statement = _connection.prepareStatement(insertSalle);
            statement.setInt(1, salle.getIdRessource());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                _logger.log(Level.INFO, "Delete operation successful. Rows deleted: {0}" , rowsDeleted);
            }
            else{
               _logger.log(Level.INFO, "No rows deleted Delete operation failed."); 
            }
        }
        catch(SQLException ex){
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public Salle getById(int id) throws UnsupportedOperationException {
        
        //retrive the data to cunstruct the object 
        Salle salle = null;
        try{
        String selectById = " select * from `ressources` where `ressources`.`id_ressource` = ?;";
        PreparedStatement statement = _connection.prepareStatement(selectById);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        
        //custuction of the object
        
        if(resultSet.next()){
            salle = new Salle(
                    resultSet.getString("nom_salle"),
                    resultSet.getInt("capacite"),
                    resultSet.getString("equipements"),
                    resultSet.getString("disponibilite"),
                    TypeSalle.valueOf(resultSet.getString("type_salle")),
                    resultSet.getInt("id_ressource")
            );
             _logger.log(Level.INFO, salle.toString());
            return salle;
        }
       
        throw new UnsupportedOperationException();
        }
        catch(SQLException ex){
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        }
        return null;
    }

    @Override
    public List<Salle> getAll() throws UnsupportedOperationException{
         //retrive the data to cunstruct the object
        boolean HasData = false;
        List<Salle> salleList = new ArrayList<>();
        try{
        String selectById = " select * from `ressources` where `ressources`.`type_ressource` = 'salle' ;";
        PreparedStatement statement = _connection.prepareStatement(selectById);
        ResultSet resultSet = statement.executeQuery();
        
        //custuction of the objects and adding to the list
        
        while(resultSet.next()){
            HasData = true;
            Salle salle;
            salle = new Salle(
                    resultSet.getString("nom_salle"),
                    resultSet.getInt("capacite"),
                    resultSet.getString("equipements"),
                    resultSet.getString("disponibilite"),
                    TypeSalle.valueOf(resultSet.getString("type_salle")),
                    resultSet.getInt("id_ressource")
            );
            
             salleList.add(salle);
        }
       
        if(!HasData){
            throw new UnsupportedOperationException();
        }
        }
        catch(SQLException ex ){
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        }
        catch(Exception ex ){
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        }
        
        return salleList;
    }
    
}
