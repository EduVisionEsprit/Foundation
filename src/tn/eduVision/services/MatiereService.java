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

            Module module = matiere.getModule();
            if (module != null) {
                statement.setInt(2, module.getIdModule());
            } else {
                statement.setNull(2, java.sql.Types.INTEGER);
            }

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been inserted!");
                throw new UnsupportedOperationException();
            }

            _logger.log(Level.INFO, "Insertion done");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public void update(Matiere matiere) {
        try {
            String updateMatiere = "UPDATE `matieres` SET `nom_matiere` = ?, `id_module` = ? WHERE `id_matiere` = ?;";
            statement = _connection.prepareStatement(updateMatiere);
            statement.setString(1, matiere.getNomMatiere());

            Module module = matiere.getModule();
            if (module != null) {
                statement.setInt(2, module.getIdModule());
            } else {
                 
                statement.setNull(2, java.sql.Types.INTEGER);
            }

            statement.setInt(3, matiere.getIdMatiere());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been updated!");
                throw new UnsupportedOperationException();
            }

            _logger.log(Level.INFO, "Update done");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            closeStatement(statement);
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
    public Matiere getById(int id) throws NoDataFoundException {
        String selectMatiereById = "SELECT * FROM `matieres` WHERE `id_matiere` = ?;";

        try (PreparedStatement stmt = _connection.prepareStatement(selectMatiereById)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int matiereId = resultSet.getInt("id_matiere");
                String matiereNom = resultSet.getString("nom_matiere");
                int moduleId = resultSet.getInt("id_module");

                Module module = getModuleById(moduleId);

                return new Matiere(matiereId, matiereNom, module);
            } else {
                throw new NoDataFoundException("No matiere found with id " + id);
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Matiere> getAll() {
        List<Matiere> matiereList = new ArrayList<>();

        try {
            String selectAllMatieres = "SELECT * FROM `matieres`;";
            statement = _connection.prepareStatement(selectAllMatieres);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int matiereId = resultSet.getInt("id_matiere");
                String matiereNom = resultSet.getString("nom_matiere");
                int moduleId = resultSet.getInt("id_module");

                Module module = getModuleById(moduleId);

                Matiere matiere = new Matiere(matiereId, matiereNom, module);
                matiereList.add(matiere);
            }

            if (matiereList.isEmpty()) {
                throw new NoDataFoundException("No data found in table: matieres");
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            closeStatement(statement);
        }

        return matiereList;
    }

    public List<String> getModuleNames() {
        List<String> moduleNames = new ArrayList<>();
        ModuleService moduleService = new ModuleService();  
        List<Module> modules = moduleService.getAll();
        for (Module module : modules) {
            moduleNames.add(module.getNomModule());
        }
        return moduleNames;
    }
public double getCoefMatiere(String nomMatiere) {
    String selectCoefQuery = "SELECT coef FROM matieres WHERE nom_matiere = ?;";
    double coefficient = 0.0;

    try (PreparedStatement stmt = _connection.prepareStatement(selectCoefQuery)) {
        stmt.setString(1, nomMatiere);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            coefficient = resultSet.getDouble("coef");
        }
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
    }

    return coefficient;
}

    private Module getModuleById(int moduleId) {
        String selectModuleById = "SELECT * FROM `modules` WHERE `id_module` = ?;";

        try (PreparedStatement stmt = _connection.prepareStatement(selectModuleById)) {
            stmt.setInt(1, moduleId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int moduleEtudeId = resultSet.getInt("id_module");
                String moduleEtudeDescription = resultSet.getString("nom_module");

                return new Module(moduleEtudeId, moduleEtudeDescription);
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
    public Module getModuleOfMatiere(String nomMatiere) {
    String selectModuleQuery = "SELECT modules.* FROM modules INNER JOIN matieres ON modules.id_module = matieres.id_module WHERE matieres.nom_matiere = ?;";
    Module module = null;

    try (PreparedStatement stmt = _connection.prepareStatement(selectModuleQuery)) {
        stmt.setString(1, nomMatiere);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            int moduleId = resultSet.getInt("id_module");
            String moduleNom = resultSet.getString("nom_module");

            module = new Module(moduleId, moduleNom);
        }
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
    }

    return module;
}

}