package com.company.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.company.helpers.Constants.STRING_SEPARATOR;

public class Appointment {

    //instance variables
    private int appointmentId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    //constructor
    public Appointment(int appointmentId, String startDate,
                       String endDate) {

        String[] start = startDate.split(STRING_SEPARATOR);
        String[] end = endDate.split(STRING_SEPARATOR);

        int syy = Integer.parseInt(start[0]);
        int smm = Integer.parseInt(start[1]);
        int sdd = Integer.parseInt(start[2]);
        int shh = Integer.parseInt(start[3]);
        int smin = Integer.parseInt(start[4]);

        int eyy = Integer.parseInt(end[0]);
        int emm = Integer.parseInt(end[1]);
        int edd = Integer.parseInt(end[2]);
        int ehh = Integer.parseInt(end[3]);
        int emin = Integer.parseInt(end[4]);

        this.appointmentId = appointmentId;
        this.startDate = LocalDateTime.of(syy, smm, sdd, shh, smin);
        this.endDate = LocalDateTime.of(eyy, emm, edd, ehh, emin);
    }

    public Appointment(int appointmentId,
                       int startYear,
                       int startMonth,
                       int startDay,
                       int startHour,
                       int startMinute,
                       int endYear,
                       int endMonth,
                       int endDay,
                       int endHour,
                       int endMinute) {

        this.appointmentId = appointmentId;
        this.startDate = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute);
        this.endDate = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMinute);

    }
    //create


    //read
    public int getAppointmentId() {
        return appointmentId;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public Duration getDuration() {
        return Duration.between(startDate, endDate);
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm a");

        Duration duration = getDuration();
        int days = (int) duration.toDays();

        duration = duration.minusDays(days);
        int hours = (int) duration.toHours();

        duration = duration.minusHours(hours);
        int minutes = (int) duration.toMinutes();

        String string = "";
        string += "Appointment starts on " + startDate.format(formatter) + "\n";
        string += "Appointment ends on " + endDate.format(formatter) + "\n";
        string += "Duration:";
        if (days != 0) {
            string += " " + days + " days";
        }
        if (hours != 0) {
            string += " " + hours + " hours";
        }
        if (minutes != 0) {
            string += " " + minutes + " minutes";
        }

        return string;
    }
    @Override
    public boolean equals(Object o) {
        return (o instanceof Appointment appointment) &&
                this.appointmentId == appointment.appointmentId &&
                this.startDate.equals(appointment.startDate) &&
                this.endDate.equals(appointment.endDate);
    }

    //update
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

}
