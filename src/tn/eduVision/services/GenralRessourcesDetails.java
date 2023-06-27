/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import tn.eduVision.entités.TypeRessource;
import tn.eduVision.entités.Salle;
import tn.eduVision.entités.Materiel;
import tn.eduVision.entités.Ressource;
import tn.eduVision.exceptions.NoDataFoundException;

/**
 *
 * @author job_j
 */
public class GenralRessourcesDetails {
   
     private final MaterielService _materialServiceInstance = new MaterielService();
     private final SallesService _salleServiceInstance = new SallesService();
     
     
     public List<Ressource> getAllResourcesPoly(){
         List<Ressource> ressourcesList = new ArrayList<>();
         try{
         ressourcesList.addAll(_salleServiceInstance.getAll());
         ressourcesList.addAll(_materialServiceInstance.getAll());
         }
         catch(NoDataFoundException ex){
             return null;
         }
         return ressourcesList;
     }
     public TypeRessource getRessourceType(int id){
         
         List<Ressource> ressList = getAllResourcesPoly();
         if(ressList == null){
             return null;
         }
         Optional<TypeRessource> typeRessourceOptional = ressList.stream()
        .filter(ressource -> ressource.getIdRessource() == id)
        .map(Ressource::getTypeRessource)
        .findFirst();
         
         if(typeRessourceOptional.isPresent()){
             return typeRessourceOptional.get();
         }
         return null;
}
     
     public String getNomRessource(int id) {
    List<Ressource> ressList = getAllResourcesPoly();
    if (ressList == null) {
        return null;
    }
    
    Optional<String> nomSalle = ressList.stream()
            .filter(ressource -> ressource.getIdRessource() == id && ressource.getTypeRessource() == TypeRessource.Salle)
            .map(ressource -> ((Salle) ressource).getNomSalle())
            .findFirst();

    

    if (nomSalle.isPresent()) {
        return nomSalle.get();
    } 
    
    Optional<String> nomMateriel = ressList.stream()
            .filter(ressource -> ressource.getIdRessource() == id && ressource.getTypeRessource() == TypeRessource.Materiel)
            .map(ressource -> ((Materiel) ressource).getNomMateriel())
            .findFirst();
    
     if (nomMateriel.isPresent()) {
        return nomMateriel.get();
    }
    
    return null;
}

     
     
}
