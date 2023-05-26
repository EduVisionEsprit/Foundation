/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.Materiel;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author job_j
 */
public class MaterielService implements Iservices<Materiel>{

     private final Connection _connection = SqlConnectionManager.getConnection();
     private static final Logger _logger = CustomLogger.getInstance().getLogger();
    
    @Override
    public void add(Materiel materiel) throws UnsupportedOperationException{
         try{
            
            String insertMateriel = "INSERT INTO `ressources` "
                    + "(`type_ressource`, `nom_materiel`, `quantite_materiel`) "
                    + "VALUES ( ?, ?, ?);";
            PreparedStatement statement = _connection.prepareStatement(insertMateriel);
            statement.setString(1, materiel.getTypeRessource().Materiel.name());
            statement.setString(2, materiel.getNomMateriel());
            statement.setInt(3, materiel.getQuantite());
            
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
    }

    @Override
    public void update(Materiel materiel) {
        try{
            
            String updateMateriel = "UPDATE `ressources` "
                    + "SET `nom_materiel` = ?, `quantite_materiel` = ? "
                    + "WHERE `ressources`.`id_ressource` = ?;";
            PreparedStatement statement = _connection.prepareStatement(updateMateriel);
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
    }

    @Override
    public void delete(Materiel item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Materiel getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Materiel> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
