package com.company;

import com.company.views.ViewLogIn;

public class Main {

    public static void main(String[] args) {
        viewLogInTest();
    }

    public static void viewLogInTest() {
        String rootPath = "test/com/company/repositories";
        ViewLogIn logIn = new ViewLogIn(rootPath);
        logIn.play();
    }

    /*
    todo
     - ViewLogIn
        full test
     - UserRepository
        full test except load()
     - DoctorView
        toSaveOrNotToSave()
        closeAppointment()
        createAppointment()
        full test
     - PatientView
        (everything)
        should also have access to appointments path, not just users path!
     */

}
