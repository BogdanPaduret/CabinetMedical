package com.company.models;

import java.util.Map;
import java.util.TreeMap;

public class Agenda {
    //contine id doctor si id programare

    //instance variables
    private Map<Doctor, Appointment> appointmentMap;

    //constructor
    public Agenda() {
        appointmentMap = new TreeMap<>();
    }




}
