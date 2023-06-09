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
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.exceptions.NoDataFoundException;
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
        String insertModule = "INSERT INTO `modules` (`nom_module`) VALUES (?);";

        try (PreparedStatement stmt = _connection.prepareStatement(insertModule)) {
            stmt.setString(1, module.getNomModule());
            stmt.executeUpdate();
            _logger.log(Level.INFO, "Module added successfully!");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void update(Module module) {
        String updateModule = "UPDATE `modules` SET `nom_module` = ?, `id_programme` = ? WHERE `id_module` = ?;";

        try (PreparedStatement stmt = _connection.prepareStatement(updateModule)) {
            stmt.setString(1, module.getNomModule());

            // Ensure that the programme property is not null
            if (module.getProgramme() != null) {
                stmt.setInt(2, module.getProgramme().getId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            stmt.setInt(3, module.getIdModule());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been updated!");
                throw new UnsupportedOperationException();
            }

            _logger.log(Level.INFO, "Update done");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void delete(Module module) {
        String deleteModule = "DELETE FROM `modules` WHERE `id_module` = ?;";

        try (PreparedStatement stmt = _connection.prepareStatement(deleteModule)) {
            stmt.setInt(1, module.getIdModule());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been deleted!");
                throw new UnsupportedOperationException();
            }

            _logger.log(Level.INFO, "Deleted");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public Module getById(int id) throws NoDataFoundException {
        String selectModuleById = "SELECT * FROM `modules` WHERE `id_module` = ?;";

        try (PreparedStatement stmt = _connection.prepareStatement(selectModuleById)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int moduleId = resultSet.getInt("id_module");
                String moduleNom = resultSet.getString("nom_module");
                int programmeId = resultSet.getInt("id_programme");

                // Retrieve the associated ProgrammeEtude object
                ProgrammeEtude programme = getProgrammeById(programmeId);

                return new Module(moduleId, moduleNom, programme);
            } else {
                throw new NoDataFoundException("No module found with id " + id);
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Module> getAll() {
        List<Module> moduleList = new ArrayList<>();
        boolean hasData = false;

        try {
            String selectAllModules = "SELECT * FROM `modules`;";
            statement = _connection.prepareStatement(selectAllModules);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                hasData = true;
                int moduleId = resultSet.getInt("id_module");
                String moduleNom = resultSet.getString("nom_module");
                int programmeId = resultSet.getInt("id_programme");

                // Retrieve the associated ProgrammeEtude object
                ProgrammeEtude programme = getProgrammeById(programmeId);

                Module module = new Module(moduleId, moduleNom, programme);
                moduleList.add(module);
            }

            if (!hasData) {
                throw new NoDataFoundException("No data found in table: modules");
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            closeStatement(statement);
        }

        return moduleList;
    }

    private ProgrammeEtude getProgrammeById(int programmeId) {
    String selectProgrammeById = "SELECT * FROM `programmes_etudes` WHERE `id_programme` = ?;";

    try (PreparedStatement stmt = _connection.prepareStatement(selectProgrammeById)) {
        stmt.setInt(1, programmeId);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            int programmeEtudeId = resultSet.getInt("id_programme");
            String programmeEtudeDescription = resultSet.getString("description");

            return new ProgrammeEtude(programmeEtudeId, programmeEtudeDescription);
        }
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
    }

    return null;
}


    private void closeStatement(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        }
    }
    
}
