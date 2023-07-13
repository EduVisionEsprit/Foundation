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
public class ReservedRessourceException extends Exception{
    public ReservedRessourceException() {
        super("A resource is reserved in this start time please pick another date.");
    }
    
    public ReservedRessourceException(String message) {
        super(message);
    }
    
    public ReservedRessourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
