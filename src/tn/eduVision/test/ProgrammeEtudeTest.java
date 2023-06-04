/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.test;

import java.util.ArrayList;
import java.util.List;
import tn.eduVision.entités.Module;
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.services.ProgrammeEtudeService;

/**
 *
 * @author bhsan
 */
public class ProgrammeEtudeTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
   
        // Create an instance of the ProgrammeEtudeService
        ProgrammeEtudeService programmeEtudeService = new ProgrammeEtudeService();

        try {
            // Create a new programme etude
            ProgrammeEtude programmeEtude = new ProgrammeEtude(1,"Programme Etude 1");

            // Add the programme etude
            programmeEtudeService.add(programmeEtude);

            // Create modules for the programme etude
            List<Module> programmeModules = new ArrayList<>();
            programmeModules.add(new Module("Module 3"));
            programmeModules.add(new Module("Module 4"));

            // Set the modules for the programme etude
            programmeEtude.setModules(programmeModules);

            // Update the programme etude
            programmeEtudeService.update(programmeEtude);

            // Get all programme etudes
            List<ProgrammeEtude> programmeEtudes = programmeEtudeService.getAll();
            System.out.println("Programme Etudes:");
            for (ProgrammeEtude pe : programmeEtudes) {
                System.out.println("ID: " + pe.getId() + ", Description: " + pe.getDescription());
                System.out.println("Modules:");
                for (Module m : pe.getModules()) {
                    System.out.println("  ID: " + m.getIdModule() + ", Name: " + m.getNomModule());
                }
            }
        } catch (NoDataFoundException e) {
            System.out.println(e.getMessage());
        }
    
    }
    
}
