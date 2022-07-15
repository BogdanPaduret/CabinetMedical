package com.company.views;

import com.company.helpers.RepositoryLoad;
import com.company.helpers.Utils;
import com.company.models.*;
import com.company.repositories.Repository;

import java.util.*;

import static com.company.helpers.Constants.USER_DOCTOR;
import static com.company.helpers.Utils.getScanner;

public class PatientView implements View {

    private Patient patient;


    public PatientView(Patient patient) {
        this.patient = patient;
    }

    private void menu() {
        String string = "";

        string += "\n=== MOD PACIENT ===";
        string += "\nSunteti logat ca " + patient.getUserName().toUpperCase();
        string += "\nApasati 1 pentru a vedea propriile programari";
        string += "\nApasati 2 pentru a vedea toti doctorii";
        string += "\nApasati 0 pentru a iesi";

        System.out.println(string);
    }

    @Override
    public void play() {
        this.play("");
    }
    public void play(String inputStrings) {
        Scanner scanner = getScanner(inputStrings);

        boolean running = true;
        int choice = -1;

        while (running) {
            menu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Puteti introduce DOAR numere");
            }

            switch (choice) {
                default -> System.out.println("Optiunea nu exista.\nIncercati din nou.");
                case 0 -> running = !Utils.exitAskSave(scanner, changedRepositories.toArray(new Repository<?>[0]));
                case 1 -> showPatientAppointments();
                case 2 -> showAllDoctors();
            }
        }
    }

    //helpers
    private void showPatientAppointments() {
        Agenda agenda = new Agenda(RepositoryLoad.userRepository.getPath(), RepositoryLoad.appointmentRepository.getPath());
        try {
            List<Appointment> appointments = new ArrayList<>(agenda.getUserAppointments(patient));

            int size = appointments.size();
            if (size > 0) {
                System.out.println("Programarile pacientului " + patient.getUserName().toUpperCase() + " sunt:");
                for (Appointment appointment : appointments) {
                    System.out.println("\n" + patientAppointmentString(appointment));
                }
            } else {
                System.out.println("Pacientul " + patient.getUserName().toUpperCase() + " nu are nicio programare");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAllDoctors() {
        Utils.showAllUsersByType(USER_DOCTOR);
    }

    private String patientAppointmentString(Appointment appointment) {
        String string = "";
        string += "Appointment #" + appointment.getAppointmentId();
        string += "\n" + RepositoryLoad.userRepository.get(appointment.getDoctorId());
        string += Utils.toStringAppointmentDates(appointment);

        return string;
    }

}
