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
import tn.eduVision.entités.Materiel;
import tn.eduVision.entités.TypeRessource;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author job_j
 */
public class MaterielService implements Iservices<Materiel>{

     private final Connection _connection = SqlConnectionManager.getInstance().getConnection();
     private static final Logger _logger = CustomLogger.getInstance().getLogger();
     private static PreparedStatement statement = null;
    
    @Override
    public void add(Materiel materiel) throws UnsupportedOperationException{
         try{
            String insertMateriel = "INSERT INTO `ressources` "
                    + "(`type_ressource`, `nom_materiel`, `quantite_materiel`) "
                    + "VALUES ( ?, ?, ?);";
            statement = _connection.prepareStatement(insertMateriel);
            statement.setString(1, materiel.getTypeRessource().Materiel.name());
            statement.setString(2, materiel.getNomMateriel());
            statement.setInt(3, materiel.getQuantite());
            System.out.println("statement"+statement);
            int rowsAffected = statement.executeUpdate();
            
            if(rowsAffected == 0){
                _logger.log(Level.WARNING, "No data been inserted !");
                throw new UnsupportedOperationException();
            }
            _logger.log(Level.INFO, "Insertion done");
        }
        catch(SQLException ex){
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        finally{
            CloseStatment(statement);
        }
    }

    @Override
    public void update(Materiel materiel) {
        try{
            
            String updateMateriel = "UPDATE `ressources` "
                    + "SET `nom_materiel` = ?, `quantite_materiel` = ? "
                    + "WHERE `ressources`.`id_ressource` = ?;";
            statement = _connection.prepareStatement(updateMateriel);
            statement.setString(1, materiel.getNomMateriel());
            statement.setInt(2, materiel.getQuantite());
            statement.setInt(3, materiel.getIdRessource());
            
            int rowsAffected = statement.executeUpdate();
            
            if(rowsAffected == 0){
                _logger.log(Level.WARNING, "No data been updated !");
                throw new UnsupportedOperationException();
            }
            _logger.log(Level.INFO, "Update done");
        }
        catch(SQLException ex){
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        finally{
            CloseStatment(statement);
        }
    }

    @Override
    public void delete(Materiel materiel) {
        try{
            String deleteMateriel = "DELETE FROM `ressources`"
                    + " WHERE "
                    + "`ressources`.`id_ressource` = ? ; ";
            statement = _connection.prepareStatement(deleteMateriel);
            statement.setInt(1, materiel.getIdRessource());
            //statement.setString(2, materiel.getTypeRessource().name());
            System.out.println(Materiel.class.toString());
            int rowsAffected = statement.executeUpdate();
            
            if(rowsAffected == 0){
                _logger.log(Level.WARNING, "No data got deleted!");
                throw new UnsupportedOperationException();
            }
            _logger.log(Level.INFO, "deleted");
        }
        catch(SQLException ex){
            _logger.log(Level.SEVERE, ex.getMessage());
        }
        finally{
            CloseStatment(statement);
        }
    }

    @Override
    public Materiel getById(int id) throws NoDataFoundException{
         Materiel materiel = this.getAll().stream().filter( m -> m.getIdRessource() == id).findFirst().get();
         if(materiel == null){
             throw new NoDataFoundException("pas de materiel avec id " + id);
         }
         return materiel;
         }
    

    @Override
    public List<Materiel> getAll() {
        boolean HasData = false;
        List<Materiel> salleList = new ArrayList<>();
        try{
        String selectById = " select nom_materiel ,  id_ressource , type_ressource , quantite_materiel"
                + " from `ressources` "
                + "where `ressources`.`type_ressource` = 'Materiel' ;";
        statement = _connection.prepareStatement(selectById);
        ResultSet resultSet = statement.executeQuery();
        
        //custuction of the objects and adding to the list
        
        while(resultSet.next()){
            HasData = true;
            Materiel materiel;
            materiel = new Materiel(
                    resultSet.getString("nom_materiel"),
                    resultSet.getInt("id_ressource"),
                    TypeRessource.valueOf(resultSet.getString("type_ressource")),
                    resultSet.getInt("quantite_materiel")
            );
            
             salleList.add(materiel);
        }
       
        if(!HasData){
            throw new NoDataFoundException("no data found empty table : ressources type Materiel");
        }
        }
        catch(SQLException ex ){
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        }
        
        finally{    
            CloseStatment(statement);
        }
        
        return salleList;
    }
    
    private void CloseStatment(PreparedStatement statment){
        try{
            if(statment !=null){
                statment.close();
            }
        }
        catch(SQLException ex){
        }
    }
    
}
