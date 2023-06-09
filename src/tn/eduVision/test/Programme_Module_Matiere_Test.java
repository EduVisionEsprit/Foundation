/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import tn.eduVision.entités.Matiere;
import tn.eduVision.entités.Module;
import tn.eduVision.entités.ProgrammeEtude;
import tn.eduVision.services.MatiereService;
import tn.eduVision.services.ModuleService;
import tn.eduVision.services.ProgrammeEtudeService;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author bhsan
 */
public class Programme_Module_Matiere_Test {
 
    public static void main(String[] args) {
      
        //Testing ModuleService
     ModuleService moduleService = new ModuleService();
     ProgrammeEtude e =new ProgrammeEtude(1, "cc");

        // Create a new module
   Module module = new Module(2, "Module added");
        System.out.println(module.getIdModule());
        System.out.println(module.getNomModule());
           Module modulea = new Module(2, "Module 3",e);
        System.out.println(modulea.getIdModule());
        System.out.println(modulea.getNomModule());
                System.out.println(modulea.getProgramme().getDescription());
                
                       moduleService.add(module);
                       module.setNomModule("cc");
                       moduleService.update(module);
                       
    }}        
        // Add the module
     //   moduleService.add(module);

//        Update the module
      //  module.setNomModule("Updated Module 2");
        //moduleService.update(module);}}

        // Get all modules

    
   // Add the module

// Delete the module
        //     moduleService.delete(module);

    /*

     
  // Testing MatiereService
        MatiereService matiereService = new MatiereService();

        // Create a new matiere
        Module module2 = new Module(2, "Module 2");
        Matiere matiere = new Matiere(1, "Matiere 1", module2);
        matiereService.add(matiere);

        // Update the matiere
        matiere.setNomMatiere("Updated Matiere 1");
        matiereService.update(matiere);
/*
/*
        // Get all matieres
        List<Matiere> matieres = matiereService.getAll();
        System.out.println("Matieres:");
        for (Matiere m : matieres) {
            System.out.println("ID: " + m.getIdMatiere() + ", Name: " + m.getNomMatiere() + ", Module: " + m.getModule().getNomModule());
        }
*/
        // Delete the matiere
        //matiereService.delete(matiere);
    
    /*  // Testing ProgrammeEtudeService
        ProgrammeEtudeService programmeEtudeService = new ProgrammeEtudeService();

        // Create a new programme etude
        ProgrammeEtude programmeEtude = new ProgrammeEtude(1, "Programme Etude 1");

        // Create modules for the programme etude
        List<Module> programmeModules = new ArrayList<>();
        programmeModules.add(new Module(3, "Module 3"));
        programmeModules.add(new Module(4, "Module 4"));

        programmeEtude.setModules(programmeModules);

        // Add the programme etude
      programmeEtudeService.add(programmeEtude);

        // Update the programme etude
        //programmeEtude.setDescription("Updated Programme Etude 1");
      //  programmeEtudeService.update(programmeEtude);

        // Get all programme etudes
       List<ProgrammeEtude> programmeEtudes = programmeEtudeService.getAll();
        System.out.println("Programme Etudes:");
             // Delete the programme etude
        programmeEtudeService.delete(programmeEtude);*/
    


    

