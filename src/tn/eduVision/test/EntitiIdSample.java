/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.test;

import java.util.UUID;
import tn.eduVision.entités.Personne;

/**
 *
 * @author job_j
 */
public class EntitiIdSample {
    
    public static void main(String args[]){
        
        Personne p = new Personne();
        p.SetId(5);
        System.out.println(p.GetId());
        
    }
    
}
