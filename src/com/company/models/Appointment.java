package com.company.models;

import com.company.helpers.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.company.helpers.Constants.STRING_SEPARATOR;

public class Appointment implements Serializable, Comparable<Appointment> {

    //instance variables
    private int appointmentId;
    private int doctorId;
    private int patientId;
    private transient LocalDateTime startDate;
    private transient LocalDateTime endDate;

    //constructor
    private void init(int appointmentId,
                      int doctorId,
                      int patientId) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }
    public Appointment(int appointmentId,
                       int doctorId,
                       int patientId,
                       String startDate,
                       String endDate) {

        init(appointmentId, doctorId, patientId);

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

        this.startDate = LocalDateTime.of(syy, smm, sdd, shh, smin);
        this.endDate = LocalDateTime.of(eyy, emm, edd, ehh, emin);
    }

    public Appointment(int appointmentId,
                       int doctorId,
                       int patientId,
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

        init(appointmentId, doctorId, patientId);

        this.startDate = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute);
        this.endDate = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMinute);
    }

    public Appointment(int appointmentId,
                       int doctorId,
                       int patientId,
                       LocalDateTime startDate,
                       LocalDateTime endDate) {

        init(appointmentId, doctorId, patientId);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    //create
    private void writeObject(ObjectOutputStream oos)
            throws IOException {
        oos.defaultWriteObject();
        //write ints of LocalDateTime for easy retrieval and localDateTime build
        LocalDateTime[] dates = {startDate, endDate};
        for (int i = 0; i < dates.length; i++) {
            LocalDateTime date = dates[i];
            oos.writeObject(date.getYear());
            oos.writeObject(date.getMonthValue());
            oos.writeObject(date.getDayOfMonth());
            oos.writeObject(date.getHour());
            oos.writeObject(date.getMinute());
        }
    }

    //read
    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        LocalDateTime[] dates = {startDate, endDate};
//        for (int i = 0; i < dates.length; i++) {
        int sYear = (int) ois.readObject();
        int sMonth = (int) ois.readObject();
        int sDay = (int) ois.readObject();
        int sHour = (int) ois.readObject();
        int sMinute = (int) ois.readObject();
        int eYear = (int) ois.readObject();
        int eMonth = (int) ois.readObject();
        int eDay = (int) ois.readObject();
        int eHour = (int) ois.readObject();
        int eMinute = (int) ois.readObject();
        startDate = LocalDateTime.of(sYear, sMonth, sDay, sHour, sMinute);
        endDate = LocalDateTime.of(eYear, eMonth, eDay, eHour, eMinute);
//        }
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
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
        String string = "Appointment #"+appointmentId;
        string += "\nAppointment of patient with ID " + patientId + " with doctor with ID " + doctorId + ":";
        string += Utils.toStringAppointmentDates(this);

        return string;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Appointment appointment) &&
                this.appointmentId == appointment.appointmentId &&
                this.doctorId == appointment.doctorId &&
                this.patientId == appointment.patientId &&
                this.startDate.equals(appointment.startDate) &&
                this.endDate.equals(appointment.endDate);
    }

    @Override
    public int compareTo(Appointment o) {
        return this.startDate.compareTo(o.startDate);
    }

    //updateAdd
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void set(Appointment appointment) {
        this.appointmentId = appointment.appointmentId;
        this.doctorId = appointment.doctorId;
        this.patientId = appointment.patientId;
        this.startDate = appointment.startDate;
        this.endDate = appointment.endDate;
    }

}
