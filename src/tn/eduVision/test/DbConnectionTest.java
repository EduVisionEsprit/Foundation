/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.test;

import java.sql.Connection;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import tn.eduVision.entités.Etat;
import java.util.List;
import tn.eduVision.entités.Materiel;
import tn.eduVision.entités.Reservation;
import tn.eduVision.entités.Salle;
import tn.eduVision.entités.TypeRessource;
import tn.eduVision.entités.TypeSalle;
import tn.eduVision.tools.SqlConnectionManager;
import tn.eduVision.entités.TypeRessource;
import tn.eduVision.services.*;

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
        Materiel m = new Materiel("test", 10, TypeRessource.Salle, 99);
        //ms.add(m);
        ms.update(m);
        Salle s = new Salle("aaa", 75, "test", "test", TypeSalle.Amphi, 7);
        SallesService ss = new SallesService();
        
        //ss.delete(s);
        
        
        //ResvationMaterielService x = new ResvationMaterielService();
        //List<Reservation> l = x.getAll(true);
        //System.out.println(l.get(1));
        //x.getReservationsToReject(l.get(1));
        //System.out.println(x.getAll(true));
        
        // Assuming you have a ZonedDateTime object and a Date object
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Date date = new Date();

        // Convert Date to ZonedDateTime
        ZonedDateTime dateAsZonedDateTime = date.toInstant().atZone(ZoneId.systemDefault());

        // Compare the two ZonedDateTime objects
        int comparisonResult = zonedDateTime.compareTo(dateAsZonedDateTime);

        // Perform actions based on the comparison result
        if (comparisonResult < 0) {
            System.out.println("The ZonedDateTime is before the Date.");
        } else if (comparisonResult > 0) {
            System.out.println("The ZonedDateTime is after the Date.");
        } else {
            System.out.println("The ZonedDateTime is the same as the Date.");
        }
        
    }
}
