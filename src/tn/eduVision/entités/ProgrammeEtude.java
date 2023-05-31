/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.entités;

import java.util.List;

/**
 *
 * @author bhsan
 */
public class ProgrammeEtude {
    private int id; 
    private String description ; 
        private List<Module> modules;


    public ProgrammeEtude(int id, String description, List<Module> modules) {
        this.id = id;
        this.description = description;
        this.modules = modules;
    }
        public ProgrammeEtude(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
        

}
