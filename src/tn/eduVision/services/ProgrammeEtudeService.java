package tn.eduVision.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

public class ProgrammeEtudeService implements Iservices<ProgrammeEtude> {

    private Connection _connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public ProgrammeEtudeService() {
        _connection = SqlConnectionManager.getInstance().getConnection();
    }
public int getProgrammeIdByDescription(String description) {
    int programId = 0;

    try {
        String selectProgramId = "SELECT id_programme FROM programmes_etudes WHERE description = ?;";
        PreparedStatement statement = _connection.prepareStatement(selectProgramId);
        statement.setString(1, description);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            programId = resultSet.getInt("id_programme");
        }
    } catch (SQLException ex) {
        _logger.log(Level.SEVERE, ex.getMessage());
    }

    return programId;
}

    @Override
    public void add(ProgrammeEtude programmeEtude) {
        PreparedStatement programmeStatement = null;

        try {
            String insertProgrammeEtude = "INSERT INTO `programmes_etudes` (`description`) VALUES (?);";
            programmeStatement = _connection.prepareStatement(insertProgrammeEtude, PreparedStatement.RETURN_GENERATED_KEYS);
            programmeStatement.setString(1, programmeEtude.getDescription());

            int rowsAffected = programmeStatement.executeUpdate();

            if (rowsAffected == 0) {
                _logger.log(Level.WARNING, "No data has been inserted!");
                throw new UnsupportedOperationException();
            }

            ResultSet generatedKeys = programmeStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int programmeEtudeId = generatedKeys.getInt(1);
                programmeEtude.setId(programmeEtudeId);
            }

            _logger.log(Level.INFO, "Insertion done");
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            closeStatement(programmeStatement);
        }
    }

    @Override
    public void update(ProgrammeEtude programmeEtude) {
        try {
            String updateProgrammeEtude = "UPDATE `programmes_etudes` SET `description` = ? WHERE `id_programme` = ?;";
            statement = _connection.prepareStatement(updateProgrammeEtude);
            statement.setString(1, programmeEtude.getDescription());
            statement.setInt(2, programmeEtude.getId());

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
    public void delete(ProgrammeEtude programmeEtude) {
        try {
            String deleteProgrammeEtude = "DELETE FROM `programmes_etudes` WHERE `id_programme` = ?;";
            statement = _connection.prepareStatement(deleteProgrammeEtude);
            statement.setInt(1, programmeEtude.getId());

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
    public ProgrammeEtude getById(int id) throws NoDataFoundException {
        ProgrammeEtude programmeEtude = getAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (programmeEtude == null) {
            throw new NoDataFoundException("No programme etude found with id " + id);
        }

        return programmeEtude;
    }

    @Override
    public List<ProgrammeEtude> getAll() {
        boolean hasData = false;
        List<ProgrammeEtude> programmeEtudeList = new ArrayList<>();

        try {
            String selectAllProgrammeEtudes = "SELECT * FROM `programmes_etudes`;";
            statement = _connection.prepareStatement(selectAllProgrammeEtudes);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                hasData = true;
                ProgrammeEtude programmeEtude = new ProgrammeEtude(
                        resultSet.getInt("id_programme"),
                        resultSet.getString("description")
                );

                programmeEtudeList.add(programmeEtude);
            }

            if (!hasData) {
                throw new NoDataFoundException("No data found in table: programmes_etudes");
            }
        } catch (SQLException ex) {
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        } finally {
            closeStatement(statement);
        }

        return programmeEtudeList;
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
