/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import tn.eduVision.entités.Matiere;
import tn.eduVision.tools.SqlConnectionManager;
import tn.eduVision.entités.Module;

/**
 *
 * @author Sayf
 */
public class ServiceExample {
    Connection _connection;
    PreparedStatement statement;
    public ServiceExample(){
        _connection = SqlConnectionManager.getConnection();
    }
    
    public void add(Module module) {
      /*  try {
            String insertModule = "INSERT INTO modules (nom_module) VALUES (?);";
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
      module = new Module(1, "Matiere");
       String sql="insert into modules(nom_module) values"
                 + "(?)";
        try {
            PreparedStatement st = _connection.prepareStatement(sql);
            st.setString(1, module.getNomModule());
            st.executeUpdate();
            System.out.println("personne ajoutéé !!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
}
