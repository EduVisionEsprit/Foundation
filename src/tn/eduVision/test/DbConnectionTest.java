/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.test;

import java.sql.Connection;
import java.util.List;
import tn.eduVision.entités.Materiel;
import tn.eduVision.entités.Salle;
import tn.eduVision.entités.TypeRessource;
import tn.eduVision.entités.TypeSalle;
import tn.eduVision.services.SallesService;
import tn.eduVision.tools.SqlConnectionManager;
import tn.eduVision.entités.TypeRessource;
import tn.eduVision.services.MaterielService;

/**
 *
 * @author job_j
 */
public class DbConnectionTest {
    public static void main(String args[]){
        //SqlConnectionManager connectionManager = SqlConnectionManager.getInstance();
        //Connection connection = connectionManager.getConnection();
        //sould only log info once
        //Connection connection2 = connectionManager.getConnection();
        MaterielService ms = new MaterielService();
        Materiel m = new Materiel("test", 8, TypeRessource.Salle, 7);
        //ms.add(m);
        
        Salle s = new Salle("aaa", 75, "test", "test", TypeSalle.Amphi, 7);
        SallesService ss = new SallesService();
        
        //ss.delete(s);
        
        
        System.out.println(ss.getAll());
        
        
        
    }
}
