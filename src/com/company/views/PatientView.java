package com.company.views;

import com.company.models.Patient;

import java.util.Scanner;

import static com.company.helpers.Utils.getScanner;

public class PatientView implements View {



    public PatientView(Patient patient) {

    }

    @Override
    public void play() {
        this.play("");
    }
    public void play(String inputStrings) {
        Scanner scanner = getScanner(inputStrings);


    }
}
