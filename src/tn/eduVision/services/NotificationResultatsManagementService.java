/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.net.Authenticator;
   import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import tn.eduVision.entités.Etudiant;
import tn.eduVision.entités.Note;
import tn.eduVision.entités.SessionExamen;

import java.util.Properties;
import java.util.logging.Logger;
import sun.rmi.transport.Transport;
import tn.eduVision.entités.Matiere;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author bhsan
 */
 

public class NotificationResultatsManagementService implements NotificationResultatsService {

 private Connection connection;
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private PreparedStatement statement = null;

    public NotificationResultatsManagementService() {
        connection = SqlConnectionManager.getInstance().getConnection();
    }


  @Override
    public void PublierResultats(Etudiant etudiant,Matiere m) {
        SessionExamen sessionExamen = obtenirDerniereSessionExamen(); 
        String resultat = determinerResultat(etudiant, m);
    String sujet = "Résultat de l'examen";
      List<Note> notes = obtenirNotesParEtudiantEtSession(etudiant, m);

        float moyenne = calculerMoyenne(notes);
    String contenu = "Votre moyenne est : " + moyenne ;

    String query = "INSERT INTO resultats (id_utilisateur, sujet, contenu,moyenne) VALUES (?, ?, ?,?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1,etudiant.getIdUtilisateur());
        statement.setString(2, sujet);
        statement.setString(3, contenu);
                statement.setFloat(4, moyenne);

        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
       ;
    }

    private String determinerResultat(Etudiant etudiant, Matiere m ) {
        List<Note> notes = obtenirNotesParEtudiantEtSession(etudiant, m);

        float moyenne = calculerMoyenne(notes);

        String resultat;
        if (moyenne >= 10.0f) {
            resultat = "Réussite";
        } else {
            resultat = "Échec";
        }

        return resultat;
    }

  

    private List<Note> obtenirNotesParEtudiantEtSession(Etudiant etudiant, Matiere matiere) {

        List<Note> notes = null;
        try {
            String query = "SELECT * FROM notes WHERE id_matiere = ? AND id_matiere = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, etudiant.getIdUtilisateur());
                statement.setInt(2, matiere.getIdMatiere());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Récupérer les informations de la note et ajouter à la liste des notes
                        Note note = new Note();
                        note.setIdNote(resultSet.getInt("id_note"));
                        note.setNote(resultSet.getFloat("note"));
                        notes.add(note);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    private float calculerMoyenne(List<Note> notes) {
      
        float somme = 0.0f;
        for (Note note : notes) {
            somme += note.getNote();
        }
        return somme / notes.size();
    }

    // Méthode utilitaire pour obtenir la dernière session d'examen
    private SessionExamen obtenirDerniereSessionExamen() {
        // Logique pour récupérer la dernière session d'examen depuis la base de données
        SessionExamen sessionExamen = null;
        try {
            String query = "SELECT * FROM session_examen ORDER BY date DESC LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupérer les informations de la dernière session d'examen
                    sessionExamen = new SessionExamen();
                    sessionExamen.setIdSessionExamen(resultSet.getInt("id_session_examen"));
               

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessionExamen;
    }

  




}
