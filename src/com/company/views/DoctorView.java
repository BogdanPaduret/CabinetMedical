package com.company.views;

import com.company.exceptions.AppointmentDoesNotExistException;
import com.company.exceptions.AppointmentFailedException;
import com.company.helpers.RepositoryLoad;
import com.company.helpers.Utils;
import com.company.models.*;
import com.company.repositories.Repository;

import java.util.*;

import static com.company.helpers.Utils.getScanner;

public class DoctorView implements View {

    //instance variables
    private Doctor doctor;

    //constructor
    public DoctorView(Doctor doctor) {
        this.doctor = doctor;
    }

    //menu
    private void menu() {
        String string = "";

        string += "\n=== MOD DOCTOR ===";
        string += "\nSunteti logat ca doctor " + doctor.getUserName().toUpperCase();
        string += "\nApasati 1 pentru a vedea programarile";
        string += "\nApasati 2 pentru a inchide o programare";
        string += "\nApasati 0 pentru a iesi";

        System.out.println(string);
    }

    //play
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
                e.printStackTrace();
            }

            switch (choice) {
                default -> {}
                case 0 -> running = !Utils.exitAskSave(scanner, changedRepositories.toArray(new Repository<?>[0]));
                case 1 -> showDoctorAppointments();
                case 2 -> {
                    try {
                        closeAppointment(scanner);
                        System.out.println("Programare inchisa cu succes.");
                    } catch (AppointmentFailedException e) {
                        e.printStackTrace();
                        System.out.println(
                                "Programarea nu a putut fi inchisa." +
                                        "\nVerificati daca datele introduse sunt corecte."
                        );
                    }

                }
            }
        }
    }

    //helper methods

    private void showDoctorAppointments() {
        Agenda agenda = new Agenda(RepositoryLoad.userRepository.getPath(), RepositoryLoad.appointmentRepository.getPath());
        try {
            List<Appointment> appointments = new ArrayList<>();
            appointments.addAll(agenda.getUserAppointments(doctor));
            int size = appointments.size();
            if (size > 0) {
                System.out.println("Programarile doctorului "+doctor.getUserName().toUpperCase()+" sunt:");
                for (Appointment appointment : appointments) {
                    System.out.println("\n" + doctorAppointmentString(appointment));
                }
            } else {
                System.out.println("Doctorul " + doctor.getUserName().toUpperCase() + " nu are nicio programare");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeAppointment(Scanner scanner) {
        System.out.println("Introduceti ID-ul programarii");
        int appointmentId = Utils.parseInteger(scanner.nextLine(), -1);
        if (appointmentId > -1) {
            RepositoryLoad.appointmentRepository.remove(RepositoryLoad.appointmentRepository.get(appointmentId));
        } else {
            throw new AppointmentDoesNotExistException();
        }
    }

    private String doctorAppointmentString(Appointment appointment) {
        String string = "";
        string += "Appointment #" + appointment.getAppointmentId();
        string += "\n" + RepositoryLoad.userRepository.get(appointment.getPatientId());
        string += Utils.toStringAppointmentDates(appointment);

        return string;
    }
}
