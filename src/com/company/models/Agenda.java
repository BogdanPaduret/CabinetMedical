package com.company.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Agenda {
    //contine id doctor si id programare

    //instance variables
    private Map<Doctor, List<Appointment>> appointmentMap;

    //constructor
    public Agenda() {
        appointmentMap = new TreeMap<>();
    }
    public Agenda(Map<Doctor, List<Appointment>> appointmentMap) {
        this.appointmentMap = appointmentMap;
    }

    //create
    public void addAppointment(Doctor doctor, Appointment appointment) {
        List<Appointment> appointments = appointmentMap.get(doctor);

        if (appointments != null) {
            appointments.add(appointment);
            appointmentMap.remove(doctor);
            appointmentMap.put(doctor, appointments);
        }
    }

    //read
//    public Appointment[] getDoctorAppointments(Doctor doctor) {
//        ArrayList<Appointment> appointments = new ArrayList<>();
//
////        appointments.add(appointmentMap.get(doctor));
//        Appointment[] appointment = new Appointment[0];
//        appointment = appointments.toArray(appointment);
//
//        return appointment;
//    }




}
