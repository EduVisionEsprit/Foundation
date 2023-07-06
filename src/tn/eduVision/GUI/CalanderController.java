/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI;


import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author job_j
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import tn.eduVision.entités.*;
import tn.eduVision.exceptions.NoDataFoundException;
import tn.eduVision.exceptions.ReservedRessourceException;
import tn.eduVision.services.*;
import tn.eduVision.tools.CustomLogger;

public class CalanderController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;
    private ResvationMaterielService _reservationInstance = new ResvationMaterielService();
    private GenralRessourcesDetails _generalDetailsInstance = new GenralRessourcesDetails();
    private static final Logger _logger = CustomLogger.getInstance().getLogger();
    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }
   private void showReservationPopup(List<Reservation> reservations) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Reservation Details");
    alert.setHeaderText(null);

    VBox container = new VBox();
    container.setSpacing(10);
    container.setPadding(new Insets(10));

    for (Reservation reservation : reservations) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.addRow(0, createLabel("Date Reservation"), createValue(reservation.getDateReservation().toString()));
        gridPane.addRow(1, createLabel("Heure Début"), createValue(reservation.getHeureDebut().toString()));
        gridPane.addRow(2, createLabel("Heure Fin"), createValue(reservation.getHeureFin().toString()));
        gridPane.addRow(3, createLabel("Etat"), createValue(reservation.getEtat().name()));
        container.getChildren().add(gridPane);
    }

    ScrollPane scrollPane = new ScrollPane(container);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setPrefWidth(600);  // Set preferred width
    scrollPane.setPrefHeight(400); // Set preferred height
    alert.getDialogPane().setContent(scrollPane);
    alert.getDialogPane().setPrefWidth(650);
    alert.getDialogPane().setPrefHeight(450);
    
    
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        
    }
}



   private void handleRectangleClick(ZonedDateTime clickedDate) {
    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Pop-up Title");
    dialog.setHeaderText("reserver");

    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(20, 20, 20, 20)); 

    DatePicker datePicker = new DatePicker(clickedDate.toLocalDate());
    gridPane.add(new Label("Date:"), 0, 0);
    gridPane.add(datePicker, 1, 0);
    datePicker.setEditable(false);
    
    ComboBox<Ressource> itemComboBox = new ComboBox<>();
    itemComboBox.getItems().addAll(_generalDetailsInstance.getAllResourcesPoly());
    gridPane.add(new Label("Ressources:"), 0, 3);
    gridPane.add(itemComboBox, 1, 3);
    itemComboBox.getStyleClass().add("custom-combo-box");
    itemComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue == null || oldValue == null) {
        if (!itemComboBox.getStyleClass().contains("invalid")) {
            itemComboBox.getStyleClass().add("invalid");
        }
    } else {
        itemComboBox.getStyleClass().remove("invalid");
    }
});
    Spinner<Integer> startHourSpinner = createHourSpinner(clickedDate.getHour());
    Spinner<Integer> startMinuteSpinner = createMinuteSpinner(clickedDate.getMinute());
    Spinner<Integer> endHourSpinner = createHourSpinner(clickedDate.getHour() + 1);
    Spinner<Integer> endMinuteSpinner = createMinuteSpinner(clickedDate.getMinute());
    gridPane.add(new Label("Start Time:"), 0, 1);
    gridPane.add(startHourSpinner, 1, 1);
    gridPane.add(new Label(":"), 2, 1);
    gridPane.add(startMinuteSpinner, 3, 1);
    gridPane.add(new Label("End Time:"), 0, 2);
    gridPane.add(endHourSpinner, 1, 2);
    gridPane.add(new Label(":"), 2, 2);
    gridPane.add(endMinuteSpinner, 3, 2);

    dialog.getDialogPane().setContent(gridPane);


    ButtonType okButton = new ButtonType("OK");
    dialog.getDialogPane().getButtonTypes().add(okButton);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == okButton) {
            LocalDate selectedDate = datePicker.getValue();
            int startHour = startHourSpinner.getValue();
            int startMinute = startMinuteSpinner.getValue();
            int endHour = endHourSpinner.getValue();
            int endMinute = endMinuteSpinner.getValue();
            Ressource selectedItem = itemComboBox.getSelectionModel().getSelectedItem();
          
            if(itemComboBox.getValue() == null || datePicker.getValue() == null){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Unsupported Operation");
                alert.setHeaderText("Alert");
                alert.setContentText("date and ressources are Mendetory");
                alert.showAndWait();
                return null;
            }

            ZonedDateTime selectedDateTime = ZonedDateTime.of(selectedDate, LocalTime.of(startHour, startMinute), clickedDate.getZone());
            //TODOOOOOOOO chnage once i can get the actual connected user :) 
            Utilisateur dummyUser =  new Utilisateur(1, "jobrane", "ben salah", "test@gmail.com", null, Role.ADMIN);
            Reservation reservation = new Reservation(-1, dummyUser, convertToDate(selectedDate), LocalTime.of(startHour, startHour),LocalTime.of(endHour, endMinute) , Etat.attente, selectedItem);
            try {
                _reservationInstance.Reserver(reservation);
                 Alert success = new Alert(AlertType.INFORMATION);
                success.setTitle("Reserved !");
                success.setHeaderText("Success");
                success.setContentText("you reserved " + _generalDetailsInstance.getNomRessource(selectedItem.getIdRessource()) + " at " +reservation.getHeureDebut() + " to " + reservation.getHeureFin() );
                success.showAndWait();
                _logger.log(Level.INFO, "reserved :{0}", reservation);
            } catch (ReservedRessourceException ex) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Unsupported Operation");
                alert.setHeaderText("Alert");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
        return null;
    });
    dialog.showAndWait();
}

private Spinner<Integer> createHourSpinner(int initialValue) {
    Spinner<Integer> spinner = new Spinner<>(0, 23, initialValue);
    spinner.setEditable(true);
    spinner.getEditor().setTextFormatter(new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d{0,2}") && Integer.parseInt(newText) < 60) {
            return change;
        }
        
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Unsupported value");
        alert.setHeaderText("Alert");
        alert.setContentText("can't pick more than 60 mins in the hours picker");

        alert.showAndWait();
        return null;
    }));

    return spinner;
}

private Spinner<Integer> createMinuteSpinner(int initialValue) {
    Spinner<Integer> spinner = new Spinner<>(0, 59, initialValue);
    spinner.setEditable(true);


    spinner.getEditor().setTextFormatter(new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d{0,2}") && Integer.parseInt(newText) < 60) {
            return change;
        }
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Unsupported value");
        alert.setHeaderText("Alert");
        alert.setContentText("can't pick more than 60 mins in the minuts picker");

        alert.showAndWait();
        return null;
    }));

    return spinner;
}


    
    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.RED);
                rectangle.setStrokeWidth(strokeWidth);
                int calculatedDate = (j+1)+(7*i);
                rectangle.setOnMouseClicked(mouseEvent -> {
                int dayOfMonth = calculatedDate - dateOffset;
                ZonedDateTime clickedDate = dateFocus.withDayOfMonth(dayOfMonth);
                ZoneId systemTimeZone = ZoneId.systemDefault();
                LocalDate currentDate = ZonedDateTime.now().toLocalDate();
                if(clickedDate.toLocalDate().isAfter(currentDate)){
                    handleRectangleClick(clickedDate);
                }
            });
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if(calendarActivities != null){
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            final int index = k;
            if(k >= 3) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    System.out.println(calendarActivities);
                });
                break;
            }
            Etat etat = calendarActivities.get(k).getEtat();
            CalendarActivity activity = calendarActivities.get(k);
            Text text = new Text(calendarActivities.get(k).getClientName() + ", " + calendarActivities.get(k).getDate().toLocalTime());
            if(null != etat)switch (etat) {
                case refuse:
                    text.setFill(Color.RED);
                    break;
                case attente:
                    text.setFill(Color.ORANGE);
                    break;
                case confirme:
                    text.setFill(Color.GREEN);
                    break;
                default:
                    break;
            }
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
            handleActivityClick(activity);
        });
           calendarActivityBox.setOnMouseClicked(event -> {
           List<Reservation> selectedReservation = _reservationInstance.getResrvationByDate(calendarActivities.get(index).getDate().plusMonths(1));

          showReservationPopup(selectedReservation);
});
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:WHITE");
        stackPane.getChildren().add(calendarActivityBox);
    }
    
    private void handleActivityClick(CalendarActivity activity) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Reservation Details");
        dialog.setHeaderText("Reservation Information");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        gridPane.add(new Label("Etat:"), 0, 0);
        gridPane.add(new Label(String.valueOf(activity.getEtat())), 1, 0);

        gridPane.add(new Label("Client Name:"), 0, 1);
        gridPane.add(new Label(activity.getClientName()), 1, 1);

        gridPane.add(new Label("Reservation Date:"), 0, 2);
        gridPane.add(new Label(activity.getDate().toLocalDate().toString()), 1, 2);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType okButton = new ButtonType("OK");
        dialog.getDialogPane().getButtonTypes().add(okButton);

        dialog.showAndWait();
}
    
    

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
    Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

    for (CalendarActivity activity: calendarActivities) {
        int activityDate = activity.getDate().getDayOfMonth();
        if (!calendarActivityMap.containsKey(activityDate)) {
            List<CalendarActivity> activityList = new ArrayList<>();
            activityList.add(activity);
            calendarActivityMap.put(activityDate, activityList);
        } else {
            List<CalendarActivity> activityList = calendarActivityMap.get(activityDate);
            activityList.add(activity);
        }
    }

    return  calendarActivityMap;
}

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<Reservation> listReservations = new ArrayList<>();
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        try{
        listReservations.addAll(_reservationInstance.getAll(false));
        listReservations.addAll(_reservationInstance.getResrvationByUserId(1)); //TODO one i get the id returned from the logged in user it should be updated so it detects the user concerned
        }
        catch(NoDataFoundException ex){
            return createCalendarMap(calendarActivities);
        }
        
        listReservations.forEach((reservation) -> {
            calendar.setTime(reservation.getDateReservation());
            if(reservation.getDateReservation().getMonth() + 1  == dateFocus.getMonthValue()){
                String nomMat = _generalDetailsInstance.getNomRessource(reservation.getRessource().getIdRessource());
                int year = calendar.get(Calendar.YEAR);
                int month = reservation.getDateReservation().getMonth();
                java.sql.Date sqlDate = (java.sql.Date) reservation.getDateReservation();
                LocalDate localDate = sqlDate.toLocalDate();
                int day = localDate.getDayOfMonth();
                int hour = reservation.getHeureDebut().getHour();
                int minute = reservation.getHeureDebut().getMinute(); 
                ZonedDateTime time = ZonedDateTime.of(year, month, day,hour, minute, 0, 0, dateFocus.getZone());
                calendarActivities.add(new CalendarActivity(time, nomMat, reservation.getIdReservation(),reservation.getEtat()));
            }
        });
        
        return createCalendarMap(calendarActivities);
    }
    
    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    private javafx.scene.control.Label createValue(String text) {
        return new javafx.scene.control.Label(text);
    }
    
    private javafx.scene.control.Label createLabel(String text) {
        javafx.scene.control.Label label = new javafx.scene.control.Label(text);
        label.setStyle("-fx-font-weight: bold");
        if(null != text)switch (text) {
                case "refuse":
                    label.setStyle("-fx-text-fill: red;");
                    break;
                case "attente":
                    label.setStyle("-fx-text-fill: orange;");
                    break;
                case "confirme":
                    label.setStyle("-fx-text-fill: green;");
                    break;
                default:
                    break;
            }
        return label;
    }


}