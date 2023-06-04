package tn.eduVision.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Module;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

public class MatiereService implements Iservices<Matiere> {

      private Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public MatiereService() {
        _connection = SqlConnectionManager.getInstance().getConnection();
    }
    @Override
    public void add(Matiere matiere) {
        try {
            String insertMatiere = "INSERT INTO `matieres` (`nom_matiere`, `id_module`) VALUES (?, ?);";
            statement = _connection.prepareStatement(insertMatiere);
            statement.setString(1, matiere.getNomMatiere());
            statement.setInt(2, matiere.getModule().getIdModule());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data been inserted !");
                throw new UnsupportedOperationException();
            }
            
            _logger.log(Level.INFO, "Insertion done");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            CloseStatement(statement);
        }
    }

    @Override
    public void update(Matiere matiere) {
        try {
            String updateMatiere = "UPDATE `matieres` SET `nom_matiere` = ?, `id_module` = ? WHERE `id_matiere` = ?;";
            statement = _connection.prepareStatement(updateMatiere);
            statement.setString(1, matiere.getNomMatiere());
            statement.setInt(2, matiere.getModule().getIdModule());
            statement.setInt(3, matiere.getIdMatiere());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data been updated !");
                throw new UnsupportedOperationException();
            }
            
            _logger.log(Level.INFO, "Update done");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            CloseStatement(statement);
        }
    }

    @Override
    public void delete(Matiere matiere) {
        try {
            String deleteMatiere = "DELETE FROM `matieres` WHERE `id_matiere` = ?;";
            statement = _connection.prepareStatement(deleteMatiere);
            statement.setInt(1, matiere.getIdMatiere());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data got deleted!");
                throw new UnsupportedOperationException();
            }
            
            _logger.log(Level.INFO, "Deleted");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            CloseStatement(statement);
        }
    }

    @Override
    public Matiere getById(int id) throws NoDataFoundException {
        Matiere matiere = this.getAll().stream()
                .filter(m -> m.getIdMatiere() == id)
                .findFirst()
                .orElse(null);
        
        if (matiere == null) {
            throw new NoDataFoundException("No matiere found with id " + id);
        }
        
        return matiere;
    }

    @Override
    public List<Matiere> getAll() {
        boolean hasData = false;
        List<Matiere> matiereList = new ArrayList<>();
        
        try {
            String selectAllMatieres = "SELECT * FROM `matieres`;";
            statement = _connection.prepareStatement(selectAllMatieres);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                hasData = true;
                Matiere matiere = new Matiere(
                        resultSet.getInt("id_matiere"),
                        resultSet.getString("nom_matiere")
                );
                
                Module module = new ModuleService().getById(resultSet.getInt("id_module"));
                matiere.setModule(module);
                
                matiereList.add(matiere);
            }
            
            if (!hasData) {
                throw new NoDataFoundException("No data found in table: matiere");
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        } finally {
            CloseStatement(statement);
        }
        
        return matiereList;
    }
    
    private void CloseStatement(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
        }
    }
}