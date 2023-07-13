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
import tn.eduVision.entités.Admin;
import tn.eduVision.entités.Departement;
import tn.eduVision.entités.Groupe;
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author Sayf
 */
public class ServiceGroupe implements Iservices<Groupe>{
    
      private final Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public ServiceGroupe() {
        _connection = SqlConnectionManager.getConnection();
    }

    @Override
    public void add(Groupe item) {
        PreparedStatement groupeStatement = null;

        try {
            String insertGroupe = "INSERT INTO `groupes` (`num`,`email`,'niveau',specialite','id_departement') VALUES (?,?,?,?,?);";
            groupeStatement = _connection.prepareStatement(insertGroupe, PreparedStatement.RETURN_GENERATED_KEYS);
            groupeStatement.setInt(1, item.getNum());
             groupeStatement.setString(2, item.getSpecialite());
             groupeStatement.setInt(3, item.getNiveau());
            groupeStatement.setInt(5, item.getDepartement().getId());

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
        } finally {
            closeStatement(groupeStatement);
        } 
    }

    @Override
    public void update(Groupe item) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

      @Override
   public void delete(Groupe Groupe) {
        try {
            String deleteGroupeStatement = "DELETE FROM `groupes` WHERE `id` = ?;";
            statement = _connection.prepareStatement(deleteGroupeStatement);
            statement.setInt(1, Groupe.getId());

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
   
      @Override
    public Groupe getById(int id) throws NoDataFoundException{
         Groupe groupe = this.getAll().stream().filter( m -> m.getId() == id).findFirst().get();
         if(groupe == null){
             throw new NoDataFoundException("pas de groupe avec id " + id);
         }
         return groupe;
         }

    @Override
    public List<Groupe> getAll() throws NoDataFoundException {

        boolean HasData = false;
        List<Groupe> groupeList = new ArrayList<>();
        try{
        String selectById = "select groupes.id,num,email,niveau,specialite,id_departement,departement.titre as deptitre,departement.description as depdesc from `groupes` JOIN departement ON groupes.id_departement = departement.id";
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
                    new Departement(
                            resultSet.getInt("id_departement")
                            ,resultSet.getString("deptitre")
                            ,resultSet.getString("depdesc"))
                    ,null
                    ,null
            );
            
             groupeList.add(groupe);
        }
       
        if(!HasData){
            throw new NoDataFoundException("no data found empty table : Groupes");
        }
        }
        catch(SQLException ex ){
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        }
        
        finally{    
            closeStatement(statement);
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
