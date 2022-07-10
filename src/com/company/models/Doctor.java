package com.company.models;

import static com.company.helpers.Constants.*;

public class Doctor extends User {

    //instance variables

    //constructor
    public Doctor(int userId, String userName) {
        super(USER_DOCTOR, userId, userName);
    }

    //implemented methods
    @Override
    public String toString() {
        String string = "";

        string += "Doctor ID: " + this.getUserId();
        string += "\nDoctor Name: " + this.getUserName();

        return string;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Doctor doctor) &&
                super.equals(o);
    }

}
