/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.exceptions;

/**
 *
 * @author job_j
 */
public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException() {
        super("No data found.");
    }
    
    public NoDataFoundException(String message) {
        super(message);
    }
    
    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
