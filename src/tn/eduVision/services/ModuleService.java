package tn.eduVision.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.Module;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.services.Iservices;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;
public class ModuleService implements Iservices<Module> {
    private Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public ModuleService() {
        _connection = SqlConnectionManager.getInstance().getConnection();
    }
   
    
    @Override
    public void add(Module module) {
      /*  try {
            String insertModule = "INSERT INTO `modules` (`nom_module`) VALUES (?);";
            statement = _connection.prepareStatement(insertModule);
            statement.setString(1, module.getNomModule());
            
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
        }*/
       String sql="insert into modules(nom_module) values"
                 + "(?)";
        try {
            PreparedStatement st = _connection.prepareStatement(sql);
            st.setString(1, module.getNomModule());
            st.executeUpdate();
            System.out.println("Module ajoutéé !!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }

    @Override
    public void update(Module module) {
        try {
            String updateModule = "UPDATE `modules` SET `nom_module` = ? WHERE `id_module` = ?;";
            statement = _connection.prepareStatement(updateModule);
            statement.setString(1, module.getNomModule());
            statement.setInt(2, module.getIdModule());
            
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
    public void delete(Module module) {
        try {
            String deleteModule = "DELETE FROM `modules` WHERE `id_module` = ?;";
            statement = _connection.prepareStatement(deleteModule);
            statement.setInt(1, module.getIdModule());
            
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
    public Module getById(int id) throws NoDataFoundException {
        Module module = this.getAll().stream()
                .filter(m -> m.getIdModule() == id)
                .findFirst()
                .orElse(null);
        
        if (module == null) {
            throw new NoDataFoundException("No module found with id " + id);
        }
        
        return module;
    }

    @Override
    public List<Module> getAll() {
        boolean hasData = false;
        List<Module> moduleList = new ArrayList<>();
        
        try {
            String selectAllModules = "SELECT * FROM `modules`;";
            statement = _connection.prepareStatement(selectAllModules);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                hasData = true;
                Module module = new Module(
                        resultSet.getInt("id_module"),
                        resultSet.getString("nom_module")
                );
                
                moduleList.add(module);
            }
            
            if (!hasData) {
                throw new NoDataFoundException("No data found in table: modules");
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        } finally {
            CloseStatement(statement);
        }
        
        return moduleList;
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