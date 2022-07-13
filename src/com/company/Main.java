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
        subListUser() are ceva avertisment "Raw user of parameterized type" pe care nu stiu sa-l rezolv ca sa nu crape codul :)
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
