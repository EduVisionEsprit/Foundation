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
import tn.eduVision.entités.Salle;
import tn.eduVision.entités.TypeRessource;
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
            statement.setString(1, TypeRessource.Salle.toString());
            statement.setString(2, salle.getTypeSalle().toString());
            statement.setString(3, salle.getNomSalle());
            statement.setInt(4, salle.getCapacite());
            statement.setString(5, salle.getEquipements());
            statement.setString(6, salle.getDisponibilite());
            
            int rowsAffected = statement.executeUpdate();
        }
        catch(SQLException ex){
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void update(Salle item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Salle item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Salle getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Salle> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
