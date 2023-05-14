/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.test;

import java.sql.Connection;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author job_j
 */
public class DbConnectionTest {
    public static void main(String args[]){
        SqlConnectionManager connectionManager = SqlConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection();
        //sould only log info once
        Connection connection2 = connectionManager.getConnection();
    }
}
