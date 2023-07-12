/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.entités;

/**
 *
 * @author sebte
 */
public class NoteTest {
     private int id ;
    private int idTest ;
    private int idEtudiant ;
    private int noteObtnue ;

    public NoteTest() {
    }
    public NoteTest(int idTest, int idEtudiant,int noteObtnue) {
        this.idTest = idTest;
        this.idEtudiant = idEtudiant;
        this.noteObtnue = noteObtnue ;
    }
    
    
    public NoteTest(int id, int idTest, int idEtudiant, int noteObtnue) {
        this.id = id;
        this.idTest = idTest;
        this.idEtudiant = idEtudiant;
        this.noteObtnue = noteObtnue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public int getNoteObtnue() {
        return noteObtnue;
    }

    public void setNoteObtnue(int noteObtnue) {
        this.noteObtnue = noteObtnue;
    }
    
}
