/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class Test {
    private int idTest;
    private Utilisateur utilisateur;
   private TypeTest typeTest;
    private float note;
    private StatutTest statut;
    
    public Test(int idTest, Utilisateur utilisateur, TypeTest typeTest, float note, StatutTest statut) {
        this.idTest = idTest;
        this.utilisateur = utilisateur;
        this.typeTest = typeTest;
        this.note = note;
        this.statut = statut;
    }
    
 
    // Ajoutez ici les getters et les setters

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public TypeTest getTypeTest() {
        return typeTest;
    }

    public void setTypeTest(TypeTest typeTest) {
        this.typeTest = typeTest;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public StatutTest getStatut() {
        return statut;
    }

    public void setStatut(StatutTest statut) {
        this.statut = statut;
    }
}
