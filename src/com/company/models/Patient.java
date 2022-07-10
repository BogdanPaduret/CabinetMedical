package com.company.models;

import static com.company.helpers.Constants.*;

public class Patient extends User {

    //instance variables

    //constructor
    public Patient(int userId, String userName) {
        super(USER_PATIENT, userId, userName);
    }

    //implemented methods
    @Override
    public String toString() {
        String string = "";

        string += "Patient ID: " + getUserId();
        string += "\nPatient name: " + getUserName();

        return string;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Patient patient) &&
                super.equals(o);
    }

}
