package com.company.models;

public class Doctor extends User {

    //instance variables

    //constructor
    public Doctor(int userId, String name) {
        super(userId, name);
    }

    //implemented methods
    @Override
    public String toString() {
        String string = "";

        string += "Doctor ID: " + this.getUserId() + "\n";
        string += "Doctor Name: " + this.getName();

        return string;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
                o instanceof Doctor;
    }

}
