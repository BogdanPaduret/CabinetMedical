package com.company.models;

import static com.company.helpers.Constants.*;

public class Patient extends User {

    //instance variables

    //constructor
    public Patient(int userId, String name) {
        super(USER_PATIENT, userId, name);
    }

    //implemented methods
    @Override
    public String toString() {
        String string = "";

        string += "Patient ID: " + getUserId() + "\n";
        string += "Patient name: " + getName();

        return string;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Patient patient) &&
                super.equals(o);
    }

}
