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

public class MatiereService  {

    private Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public MatiereService() {
        _connection = SqlConnectionManager.getInstance().getConnection();
    }
 public void add(Matiere matiere) {
    String insertMatiereQuery = "INSERT INTO `matieres` (`nom_matiere`, `id_module`, `coef`) VALUES (?, ?, ?);";
    try (PreparedStatement stmt = _connection.prepareStatement(insertMatiereQuery)) {
        stmt.setString(1, matiere.getNomMatiere());
        stmt.setInt(2, matiere.getModule().getIdModule());
        stmt.setFloat(3, matiere.getCoef());

        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

public void updateMatiere(Matiere matiere) {
    String updateMatiereQuery = "UPDATE `matieres` SET `nom_matiere` = ?, `id_module` = ?, `coef` = ? WHERE `id_matiere` = ?;";
    try (PreparedStatement stmt = _connection.prepareStatement(updateMatiereQuery)) {
        stmt.setString(1, matiere.getNomMatiere());
        stmt.setInt(2, matiere.getModule().getIdModule());
        stmt.setFloat(3, matiere.getCoef());
        stmt.setInt(4, matiere.getIdMatiere());

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected == 0) {
            System.out.println("No data has been updated!");
        } else {
            System.out.println("Update successful!");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
public float getCoefMatiere(String nomMatiere) {
    String query = "SELECT coef FROM matieres WHERE nom_matiere = ?";
    try (PreparedStatement stmt = _connection.prepareStatement(query)) {
        stmt.setString(1, nomMatiere);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getFloat("coef");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return 0.17f; // Default value if the matiere is not found or there is an error
}


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
