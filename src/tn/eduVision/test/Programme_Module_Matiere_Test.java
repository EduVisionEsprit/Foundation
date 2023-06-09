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

    MatiereService matiereService = new MatiereService();
    ModuleService moduleService = new ModuleService();

    // Create a module
    Module module = new Module("Module 1");
    moduleService.add(module);

    // Create a matiere with the valid module ID
    Matiere matiere = new Matiere(1,"Matiere 1", module);
    matiereService.add(matiere);

    // Get all matieres
    List<Matiere> allMatieres = matiereService.getAll();
    for (Matiere m : allMatieres) {
        System.out.println(m.getNomMatiere());
    }
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
    


    

