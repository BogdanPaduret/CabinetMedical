package com.company.views;

import com.company.helpers.RepositoryLoad;
import com.company.helpers.Utils;
import com.company.models.Secretary;
import com.company.repositories.Observed;
import com.company.repositories.Repository;

import java.util.ArrayList;
import java.util.Scanner;

import static com.company.helpers.Constants.*;
import static com.company.helpers.Utils.*;

public class SecretaryView implements View {

    private Secretary secretary;

    public SecretaryView(Secretary secretary) {
        this.secretary = secretary;
    }

    public void menu() {
        String string = "";

        string += "Sunteti logat ca " + secretary.getUserName();
        string += "\nApasati 1 pentru a crea o programare";
        string += "\nApasati 2 pentru a anula o programare";
        string += "\nApasati 3 pentru a modifica o programare";
        string += "\nApasati 4 pentru a vedea toate programarile unui doctor";
        string += "\nApasati 0 pentru a iesi";

        System.out.println(string);
    }
    @Override
    public void play() {
        this.play("");
    }
    public void play(String input) {
        Scanner scanner = getScanner(input);

        boolean running = true;
        int choice = -1;

        while (running) {

            menu();

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            switch (choice) {
                default -> {}
                case 0 -> running = !Utils.exitAskSave(scanner, changedRepositories.toArray(new Repository<?>[0]));
                case 1 -> createAppointment(scanner);
                case 2 -> cancelAppointment(scanner);
                case 3 -> modifyAppointment(scanner);
                case 4 -> showAllDoctorAppointments(scanner);
            }

        }

    }

    //helpers
    private void createAppointment(Scanner scanner) {
        System.out.println("Introduceti numele pacientului si numele doctorului separate prin virgula");
        String[] input = scanner.nextLine().split(STRING_SEPARATOR);
        String patientName = input[0];
        String doctorName = input[1];

    }

    private void cancelAppointment(Scanner scanner) {

    }

    private void modifyAppointment(Scanner scanner) {

    }

    private void showAllDoctorAppointments(Scanner scanner) {

    }

}
