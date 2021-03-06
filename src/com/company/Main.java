package com.company;

import com.company.views.ViewLogIn;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        viewLogInTest();
    }

    public static void viewLogInTest() {
        String rootPath = "test/com/company/repositories";

//        try {
//            File file = new File(rootPath + "/appointments");
//            PrintWriter printWriter = new PrintWriter(file);
//            printWriter.print("");
//            printWriter.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        ViewLogIn logIn = new ViewLogIn(rootPath);
        logIn.play();
    }

    /*
    todo
     - ViewLogIn
        full test in test package
     - UserRepository
        full test in test package except load()
     - DoctorView
        full test in test package
     - PatientView
        full test in test package
     */

}
