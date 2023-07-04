
package tn.eduVision.services;

/**
 *
 * @author Meher
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import java.util.Properties;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.mail.*;
import javax.mail.internet.*;
import javafx.stage.DirectoryChooser;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import tn.eduVision.entités.StageEtudiant;

public class DecisionButtonCell extends TableCell<List<String>, Void> {
    
    
    
    
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
    
    
    
    
    //update decesion column
    
    public void updatedecesionaccept(int stageId,int UserId) {
        
        
    try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
        // Update the decision column in the stages table
        String updateQuery = "UPDATE stages SET Decision = 'accepté' WHERE id_stage = ?";
        PreparedStatement updateStatement = dbManager.getConnection().prepareStatement(updateQuery);
        updateStatement.setInt(1, stageId);
        updateStatement.executeUpdate();
        
        // Insert the userId and stageId into the suiviestage table
        String insertQuery = "INSERT INTO suivistage (id_utilisateur, id_stage) VALUES (?, ?)";
        PreparedStatement insertStatement = dbManager.getConnection().prepareStatement(insertQuery);
        insertStatement.setInt(1, UserId);
        insertStatement.setInt(2, stageId);
        insertStatement.executeUpdate();
        
        updateStatement.close();
        insertStatement.close();
        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }
}
    
    
    public void updatedecesionreject(int stageId) {
    try {
        DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
        String query = "UPDATE `stages` SET `Decision` = 'refusé' WHERE id_stage = " + stageId;
        dbManager.executeUpdate(query);
        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }
}
    
    
    
    public void sendNotificationEmail(String userEmail, String message) {
        
        
    // Paramètres de configuration pour la connexion SMTP
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com"); // Remplacez par le serveur SMTP que vous utilisez
    properties.put("mail.smtp.port", "587"); // Remplacez par le port SMTP approprié
    properties.put("mail.smtp.auth", "true"); // Indique que l'authentification est requise
    properties.put("mail.smtp.starttls.enable", "true"); // Active le chiffrement TLS

    // Informations d'authentification pour l'envoi de l'e-mail
    String username = "ferjani.meher@esprit.tn"; // Remplacez par votre adresse e-mail
    String password = "nwzjabqqtkjcrcig"; // Remplacez par votre mot de passe

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
    
    
    
    public String getFilePathFromDatabase(int stageId) {
        String filepath = null;
        // Ajoutez le code ici pour insérer les données dans la base de données
      try {
    DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
    
    
     String query = " select path from stages where id_stage= "+stageId ;
    
    ResultSet resultSet = dbManager.executeQuery(query);
    
    if (resultSet.next()) {
            filepath = resultSet.getString("path");
        }
        
        System.out.println(filepath);
    
        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }

        return filepath;
}

    
    
    
    
    
public void downloadFile(String filePath) {
    if (filePath != null) {
        File file = new File(filePath);
        if (file.exists()) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choisir un dossier de destination");
            File selectedDirectory = directoryChooser.showDialog(null);
            if (selectedDirectory != null) {
                String destinationPathString = selectedDirectory.getAbsolutePath() + File.separator + file.getName();
                

                Path destinationPath = Paths.get(destinationPathString);
                try {
                    FileChannel sourceChannel = new FileInputStream(file).getChannel();
                    FileChannel destChannel = new FileOutputStream(destinationPath.toFile()).getChannel();
                    destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
                    sourceChannel.close();
                    destChannel.close();
                    System.out.println("Le fichier a été téléchargé avec succès.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Une erreur s'est produite lors du téléchargement du fichier.");
                }
            } else {
                System.out.println("Aucun dossier de destination sélectionné.");
            }
        } else {
            System.out.println("Le fichier n'existe pas.");
            
        }
    }
}






 public void deleteStageFromDatabase(int StageId){
        
      System.out.println(StageId);
    try {
    DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/pidevcs", "root", "");
    
    String query = "DELETE FROM stages WHERE id_stage = ?";
        PreparedStatement statement = dbManager.getConnection().prepareStatement(query);
        statement.setInt(1, StageId);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
             showNotification("Success", "Selected stage deleted successfully.");
        } else {
            showNotification("Error", "No stage found with the specified ID.");
        }

        statement.close();
        dbManager.closeConnection();
    } catch (SQLException e) {
        System.out.println(e);
    }


    
 }

   private void showNotification(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
    
    
    
    
}