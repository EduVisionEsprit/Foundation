/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
we use this job to get rid of the outdated
CREATE EVENT IF NOT EXISTS `Refuse_every_outdated_reservation` ON SCHEDULE EVERY 1 DAY STARTS CURRENT_DATE + INTERVAL 1 DAY DO BEGIN UPDATE `reservations` SET `etat` = 'confirme' WHERE `date_reservation` < CURDATE(); END;
 */
package tn.eduVision.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.eduVision.entités.Etat;
import tn.eduVision.entités.Reservation;
import tn.eduVision.entités.Ressource;
import tn.eduVision.entités.Role;
import tn.eduVision.entités.TypeRessource;
import tn.eduVision.entités.Utilisateur;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.exceptions.ReservedRessourceException;
import tn.eduVision.tools.CustomLogger;
import tn.eduVision.tools.SqlConnectionManager;

/**
 *
 * @author job_j
 */
public class ResvationMaterielService{
    
    private final Connection _connection = SqlConnectionManager.getInstance().getConnection();
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    private static PreparedStatement statement = null;
    private GenralRessourcesDetails GenraaldetailsServiceInstance = new GenralRessourcesDetails();
    private SallesService salleServiceInstance = new SallesService();
    private MaterielService materielServiceInstance = new MaterielService();
    

    public void update(Reservation item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void delete(Reservation item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Reservation getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Optional<Reservation> getByStartDateTime(LocalTime startTime, Date startDate, LocalTime endTime, int id) {
        
        /*try {
            List<Reservation> listReservations = getAll(true);
           
            return listReservations.stream()
            .filter(reservation -> {
            LocalTime reservationStartTime = reservation.getHeureDebut();
            LocalTime reservationEndTime = reservation.getHeureFin();
            Date reservationDate = reservation.getDateReservation();
            
            return ((reservationStartTime.compareTo(startTime) >= 0 || reservationStartTime.compareTo(endTime) <= 0) && startDate.equals(reservationDate))
                    || ((reservationEndTime.compareTo(startTime) >= 0 || reservationEndTime.compareTo(startTime) <= 0) && startDate.equals(reservationDate));
        }).findFirst();

        } catch (NoDataFoundException e) {
            _logger.log(Level.WARNING, e.getMessage());
        }
        finally{
            CloseStatment(statement);
        }*/
        //i just want one single reservation with that date list should not contain more than one  
        
         try {
            String sql = "SELECT * FROM reservations "
                + "WHERE ((heure_debut >= TIME(?) AND heure_debut <= TIME(?) and DATE(`date_reservation`) = ?) "
                + "OR (heure_fin >= TIME(?) AND heure_fin <= TIME(?) and DATE(`date_reservation`) = ?) and id_ressource = ?) and etat = 'confirme';";
            
            statement = _connection.prepareStatement(sql);
            
            statement.setTime(1, convertToSqlTime(startTime));
            statement.setTime(2, convertToSqlTime(endTime));
            statement.setDate(3, sqlDateConverter(startDate));
            statement.setTime(4, convertToSqlTime(startTime));
            statement.setTime(5, convertToSqlTime(endTime));
            statement.setDate(6, sqlDateConverter(startDate));
            statement.setInt(7, id);
             System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                
               System.out.println(resultSet.getInt("id_reservation"));
                Reservation reservation =  new Reservation(resultSet.getInt("id_reservation"));
                //System.out.println(reservation); 
               return Optional.of(reservation);
            }
        } catch (SQLException e) {
        }
      return Optional.empty();
    }

    
    public List<Reservation> getAll(boolean getUnvalidated) throws NoDataFoundException{
        
        boolean HasData = false;
        Ressource salle = null;
        Ressource materiel = null;
        List<Reservation> reservationList = new ArrayList<>();
        try{
        String selectAll = "";
        if(getUnvalidated){
            selectAll = "select * from `reservations` where etat = '" + Etat.attente.name() + "' ;";
        }
        else {
            selectAll = "select * from `reservations` where etat = '" + Etat.confirme.name() + "' ;";
            System.out.println(selectAll);
        }
        
        statement = _connection.prepareStatement(selectAll);
        ResultSet resultSet = statement.executeQuery();
        
        //custuction of the objects and adding to the list
        
        while(resultSet.next()){
            HasData = true;
            Reservation reservation = null;
           
            if(GenraaldetailsServiceInstance.getRessourceType(resultSet.getInt("id_ressource")) == TypeRessource.Salle){
                
                salle = salleServiceInstance.getById(resultSet.getInt("id_ressource"));

            }
            else{
                materiel = materielServiceInstance.getById(resultSet.getInt("id_ressource"));
            }
            if(salle != null){
                System.out.println(resultSet.getTime("heure_debut"));
            reservation = new Reservation(
                    resultSet.getInt("id_reservation"),
                    new Utilisateur(resultSet.getInt("id_utilisateur"), "jobrane", "ben salah", "test@gmail.com", null, Role.admin),
                    resultSet.getDate("date_reservation"),
                    convertToLocaleTime(resultSet.getTime("heure_debut")),
                    convertToLocaleTime(resultSet.getTime("heure_fin")),
                    Etat.valueOf(resultSet.getString("etat")),
                    salle
            );
            
            }
            
            else if(materiel != null){
                reservation = new Reservation(
                    resultSet.getInt("id_reservation"),
                        //TODO change this when getUserById is done
                    new Utilisateur(resultSet.getInt("id_utilisateur"), "jobrane", "ben salah", "test@gmail.com", null, Role.admin),
                    resultSet.getDate("date_reservation"),
                    convertToLocaleTime(resultSet.getTime("heure_debut")),
                    convertToLocaleTime(resultSet.getTime("heure_fin")),
                    Etat.valueOf(resultSet.getString("etat")),
                    materiel
            );
                salle = null;
                materiel = null;
            }
            
             reservationList.add(reservation);
        }
       
        if(!HasData){
            throw new NoDataFoundException("no data found empty table : ressources type salle");
        }
        }
        catch(SQLException ex ){
            _logger.log(Level.SEVERE, ex.getMessage(), this.getClass());
        }
        finally{
            CloseStatment(statement);
        }
        return reservationList;
    }
    
    public void Reserver(Reservation reservation ) throws ReservedRessourceException{
        if(reservation.getDateReservation().compareTo(new Date()) <= 0){
            throw new ReservedRessourceException("date < a date system");
        }
        if(reservation.getHeureDebut().compareTo(reservation.getHeureFin()) > 0){
            throw new ReservedRessourceException("Can't have end time < to start time");
        }
        
        Optional<Reservation> CurrentReservation = getByStartDateTime(reservation.getHeureDebut(), reservation.getDateReservation(), reservation.getHeureFin(), reservation.getRessource().getIdRessource());
        
        
        if(CurrentReservation.isPresent()){
            _logger.log(Level.WARNING, "ressource reservée a {0}", reservation.getHeureDebut()+ " jusqu'à " + reservation.getHeureFin());
            throw new ReservedRessourceException("ressource reservée a , " + reservation.getHeureDebut() +  "jusqu à " + reservation.getHeureFin());
        }
        
        try {
            String insertReservation = "INSERT INTO `reservations` "
                    + "(`id_utilisateur`, `id_ressource`, `date_reservation`, `heure_debut`, `heure_fin`, `etat`) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?);";
            statement = _connection.prepareStatement(insertReservation);
            statement.setInt(1, reservation.getUtilisateur().getIdUtilisateur());
            statement.setInt(2, reservation.getRessource().getIdRessource());
            statement.setDate(3, sqlDateConverter(reservation.getDateReservation()));
            statement.setTime(4, Time.valueOf(reservation.getHeureDebut()));
            statement.setTime(5, Time.valueOf(reservation.getHeureFin()));
            statement.setString(6, Etat.attente.name());
            System.out.println(statement.toString());
            statement.executeUpdate();
            _logger.log(Level.INFO, "Reservation Inserted");
        } catch (SQLException e) {
            _logger.log(Level.SEVERE, e.getMessage());
        }
        finally{
        CloseStatment(statement);
           }
    }
    
    public void makeReservationVlidations(Etat etat, Reservation reservaation) throws Exception{
        try {
            String sql = "UPDATE `reservations` SET `etat` = ? WHERE `reservations`.`id_reservation` = ?;";
            
            statement = _connection.prepareStatement(sql);
            
            statement.setString(1, etat.name());
            statement.setInt(2, reservaation.getIdReservation());
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected == 0){
                throw new Exception("nothing got updated");
            }
            
            
        } catch (SQLException e) {
            _logger.log(Level.SEVERE, e.getMessage());
        }
    }
    
    private void CloseStatment(PreparedStatement statment){
        try{
            if(statment !=null){
                statment.close();
            }
        }
        catch(SQLException ex){
            _logger.log(Level.SEVERE, "error closing a statment", ex);
        }
    }
    
    private java.sql.Date sqlDateConverter(Date date){
        return new java.sql.Date(date.getTime());
    }
    
   private static LocalTime convertToLocaleTime(Time sqlTime) {
        return sqlTime.toLocalTime();
    }
   
   private static Time convertToSqlTime(LocalTime localTime) {
    
    LocalDate currentDate = LocalDate.now();
    LocalDateTime localDateTime = LocalDateTime.of(currentDate, localTime);

    Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    Time sqlTime = new Time(date.getTime());

    return sqlTime;
}

}
