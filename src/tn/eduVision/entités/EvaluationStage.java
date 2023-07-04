/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.eduVision.entités;

/**
 *
 * @author Sayf
 */
public class EvaluationStage {
    private int idEvaluation;
    private StageEtudiant stage;
    private Utilisateur utilisateur;
    private float note;

    public EvaluationStage(int idEvaluation, StageEtudiant stage, Utilisateur utilisateur, float note) {
        this.idEvaluation = idEvaluation;
        this.stage = stage;
        this.utilisateur = utilisateur;
        this.note = note;
    }

    public int getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(int idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public StageEtudiant getStage() {
        return stage;
    }

    public void setStage(StageEtudiant stage) {
        this.stage = stage;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

   
}