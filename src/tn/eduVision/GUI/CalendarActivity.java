/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.GUI;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import tn.eduVision.entités.Etat;

/**
 *
 * @author job_j
 */
public class CalendarActivity {
    private ZonedDateTime date;
    private String clientName;
    private Integer serviceNo;
    private Etat etat;

    public CalendarActivity(ZonedDateTime date, String clientName, Integer serviceNo, Etat etat) {
        this.date = date;
        this.clientName = clientName;
        this.serviceNo = serviceNo;
        this.etat = etat;
    }

    public ZonedDateTime getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss z");
        String formattedDate = date.format(formatter);
        System.out.println(formattedDate);

    return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(Integer serviceNo) {
        this.serviceNo = serviceNo;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat stat) {
        this.etat = stat;
    }
    

    @Override
    public String toString() {
        return "CalenderActivity{" +
                "date=" + date +
                ", clientName='" + clientName + '\'' +
                ", serviceNo=" + serviceNo +
                '}';
    }
}
