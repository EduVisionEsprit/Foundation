
package tn.eduVision.entités;

/**
 *
 * @author Meher
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import tn.eduVision.entités.AjoutStage;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class ButtonCell extends TableCell<List<String>, Void> {
    
    
    
   
    
   
    private final Button acceptButton;
    private final Button rejectButton;

    public ButtonCell() {
        acceptButton = new Button("Accepter");
        rejectButton = new Button("Refuser");
        
        
        

        acceptButton.setOnAction(event -> {
            List<String> rowData = getTableView().getItems().get(getIndex());
    // Récupérer l'index de la colonne "utilisateur"
    
    // Récupérer la valeur de la colonne "utilisateur"
    int utilisateurValue = Integer.parseInt(rowData.get(1));
    // Utiliser la valeur de l'utilisateur comme nécessaire
    
    String userEmail = EmailFromUserTable(utilisateurValue); 

    // Envoyer un email de notification à l'utilisateur
    sendNotificationEmail(userEmail, "Votre stage a été accepté"); // Implémentez cette méthode pour envoyer l'email
        });
        
rejectButton.setOnAction(event -> {
           List<String> rowData = getTableView().getItems().get(getIndex());
            // Récupérer l'ID utilisateur depuis la colonne correspondante
    int userId = Integer.parseInt(rowData.get(1)); // Assurez-vous que l'index correspond à la colonne utilisateur

    // Récupérer l'email de l'utilisateur depuis la table utilisateur
    String userEmail = EmailFromUserTable(userId); // Implémentez cette méthode pour récupérer l'email de l'utilisateur

    // Envoyer un email de notification à l'utilisateur
    sendNotificationEmail(userEmail, "Votre stage est refusé"); // Implémentez cette méthode pour envoyer l'email
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(new HBox(acceptButton, rejectButton));
        } else {
            setGraphic(null);
        }
    }
    
    
    
    
    
    public String EmailFromUserTable(int userId) {
       
    String email = null;
        // Ajoutez le code ici pour insérer les données dans la base de données
      try {
    DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
    
    
     String query = " select email from utilisateurs where id_utilisateur= "+userId ;
    
    ResultSet resultSet = dbManager.executeQuery(query);
    
    if (resultSet.next()) {
            email = resultSet.getString("email");
        }
        
        System.out.println(email);
    System.out.println(email);
        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }

        return email;
    }
    
    
    
    
    public void sendNotificationEmail(String userEmail, String message) {
    // Paramètres de configuration pour la connexion SMTP
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.office365.com"); // Remplacez par le serveur SMTP que vous utilisez
    properties.put("mail.smtp.port", "587"); // Remplacez par le port SMTP approprié
    properties.put("mail.smtp.auth", "true"); // Indique que l'authentification est requise
    properties.put("mail.smtp.starttls.enable", "true"); // Active le chiffrement TLS

    // Informations d'authentification pour l'envoi de l'e-mail
    String username = "meher_fe@live.fr"; // Remplacez par votre adresse e-mail
    String password = "Masterofthegame8."; // Remplacez par votre mot de passe

    // Créer une session avec les paramètres de configuration et les informations d'authentification
    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username,password);
        }
    });
    
    try {
        // Créer un objet MimeMessage
        Message mimeMessage = new MimeMessage(session);

        // Définir l'expéditeur de l'e-mail
        mimeMessage.setFrom(new InternetAddress(username));

        // Définir le destinataire de l'e-mail
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));

        // Définir le sujet de l'e-mail
        mimeMessage.setSubject("Notification de stage");

        // Définir le contenu de l'e-mail
        mimeMessage.setText(message);

        // Envoyer l'e-mail
        Transport.send(mimeMessage);

        System.out.println("L'e-mail de notification a été envoyé avec succès à " + userEmail);
    } catch (MessagingException e) {
        System.out.println("Une erreur s'est produite lors de l'envoi de l'e-mail de notification : " + e.getMessage());
    }
    
    }
}