package tn.eduVision.entités;

public class Utilisateur {
    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String specialite_ens;
    private Role role;

    public Utilisateur(int idUtilisateur, String nom, String prenom, String email, String motDePasse, Role role,String specialite_ens) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.specialite_ens = specialite_ens;
    }

    

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
    public String getSpecialite_ens() {
        return specialite_ens;
    }

    public void setSpecialite_ens(String specialite_ens) {
        this.specialite_ens = specialite_ens;
    }
    
    @Override
public String toString() {
    return "Utilisateur [id=" + idUtilisateur + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", role=" + role + ", specialite_ens"+specialite_ens+"]";
}
}
