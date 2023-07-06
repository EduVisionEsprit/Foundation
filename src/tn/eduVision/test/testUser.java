/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.test;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.services.UserServices;
import java.util.List;
import tn.eduVision.services.MatiereService;
import tn.eduVision.services.ModuleService;
/**
 *
 * @author bhsan
 */
public class testUser {
    

    public static void main(String[] args) {
        // Create an instance of UserServices
 UserServices UserServices = new UserServices();
        // Test getAllUsers() method
        List<Utilisateur> users = UserServices.getAllUsers();
        System.out.println("All Users:");
        for (Utilisateur user : users) {
            System.out.println(user);
        }

        // Test getUserById() method
        Utilisateur userById = UserServices.getUserById(1);
        if (userById != null) {
            System.out.println("User with ID 1: " + userById);
        } else {
            System.out.println("User with ID 1 not found.");
        }

        // Test getUsersByRole() method
        List<Utilisateur> usersByRole = UserServices.getUsersByRole(Role.etudiant);
        System.out.println("Students:");
        for (Utilisateur user : usersByRole) {
            System.out.println(user);
        }
    }
}

