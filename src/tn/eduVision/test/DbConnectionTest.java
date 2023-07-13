/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.test;

import tn.eduVision.entités.Module;
import tn.eduVision.services.ServiceExample;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author job_j
 */
public class DbConnectionTest {
    public static void main(String args[]){
        SqlConnectionManager c = SqlConnectionManager.getInstance();
       ServiceExample a;
        a = new ServiceExample();
        Module m = new Module(1, "Matiere");
        a.add(m);
       
    }
}
