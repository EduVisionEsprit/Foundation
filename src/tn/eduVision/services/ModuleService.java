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
import tn.eduVision.entités.Groupe;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Module;
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

public class ModuleService implements Iservices<Module> {
    private Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;
   public boolean isAdmin(Utilisateur user) {
    return user instanceof Admin;
}
    public ModuleService() {
        _connection = SqlConnectionManager.getInstance().getConnection();
    }
public List<Module> getModulesByProgramme(ProgrammeEtude programme) {
    List<Module> moduleList = new ArrayList<>();

    try {
        String selectModulesByProgramme = "SELECT * FROM modules WHERE id_programme = ?;";
        PreparedStatement statement = _connection.prepareStatement(selectModulesByProgramme);
        statement.setInt(1, programme.getId());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int moduleId = resultSet.getInt("id_module");
            String moduleNom = resultSet.getString("nom_module");

            Module module = new Module(moduleId, moduleNom);
            moduleList.add(module);
        }
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
    }

    return moduleList;
}

public List<Module> getModulesByGroupe(Groupe groupe) {
    List<Module> moduleList = new ArrayList<>();

    try {
        String selectModulesByGroupe = "SELECT modules.* FROM modules JOIN matieres ON modules.id_module = matieres.id_module WHERE matieres.id_groupe = ?;";
        PreparedStatement statement = _connection.prepareStatement(selectModulesByGroupe);
        statement.setInt(1, groupe.getIdGroupe());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int moduleId = resultSet.getInt("id_module");
            String moduleNom = resultSet.getString("nom_module");

            Module module = new Module(moduleId, moduleNom);
            moduleList.add(module);
        }
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
    }

    return moduleList;
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
    public List<Matiere> getMatieresByModule(Module module) {
    List<Matiere> matiereList = new ArrayList<>();

    String selectMatieresByModule = "SELECT * FROM `matieres` WHERE `id_module` = ?;";

    try (PreparedStatement stmt = _connection.prepareStatement(selectMatieresByModule)) {
        stmt.setInt(1, module.getIdModule());
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            int matiereId = resultSet.getInt("id_matiere");
            String matiereNom = resultSet.getString("nom_matiere");
            float coef = resultSet.getFloat("coef");

            Matiere matiere = new Matiere(matiereId, matiereNom, module, coef);
            matiereList.add(matiere);
        }
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
    }

    return matiereList;
}
public List<Matiere> getAllMatieres() {
    List<Matiere> matiereList = new ArrayList<>();

     
    List<Module> moduleList = getAll();

     
    for (Module module : moduleList) {
        List<Matiere> matieresByModule = getMatieresByModule(module);
        matiereList.addAll(matieresByModule);
    }

    return matiereList;
}public List<Module> getAllModules() {
    List<Module> moduleList = new ArrayList<>();

     
    String selectAllModules = "SELECT * FROM modules";

    try (PreparedStatement stmt = _connection.prepareStatement(selectAllModules)) {
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            int moduleId = resultSet.getInt("id_module");
            String moduleNom = resultSet.getString("nom_module");
             

            Module module = new Module(moduleId, moduleNom);
            moduleList.add(module);
        }
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
    }

    return moduleList;
}
public List<String> getNomMatiere(Module module) {
    List<String> nomMatieres = new ArrayList<>();

    try {
        String selectNomMatieres = "SELECT nom_matiere FROM matieres WHERE id_module = ?;";
        PreparedStatement statement = _connection.prepareStatement(selectNomMatieres);
        statement.setInt(1, module.getIdModule());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String nomMatiere = resultSet.getString("nom_matiere");
            nomMatieres.add(nomMatiere);
        }
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
    }

    return nomMatieres;
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

}
