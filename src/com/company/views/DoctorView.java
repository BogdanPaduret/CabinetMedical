package com.company.views;

import com.company.helpers.RepositoryLoad;
import com.company.helpers.Utils;
import com.company.models.*;
import com.company.repositories.Observed;
import com.company.repositories.Repository;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.company.helpers.Utils.getScanner;

public class DoctorView implements View {

    //instance variables
    private Doctor doctor;
    private Set<Appointment> appointments;
    private Set<Patient> patients;

    //constructor
    public DoctorView(Doctor doctor) {
        this.doctor = doctor;
        Agenda agenda = new Agenda(RepositoryLoad.userRepository.getPath(), RepositoryLoad.appointmentRepository.getPath());
        appointments.addAll(agenda.getDoctorAppointments(doctor));
    }

    //menu
    private void menu() {
        String string = "";

        string += "Logat ca doctor " + doctor.getUserName();
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
                case 2 -> closeAppointment();
            }
        }
    }

    //helper methods

    private void showDoctorAppointments() {
        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();
            System.out.println(doctorAppointmentString(appointment));
        }
    }

    private void closeAppointment() {

    }

    private Patient getPatient(int id) {
        Iterator<Patient> iterator = patients.iterator();
        while (iterator.hasNext()) {
            Patient patient = iterator.next();
            if (patient.getUserId() == id) {
                return patient;
            }
        }
        throw new NoSuchElementException("Patient with ID: " + id + " does not exist");
    }
    private String doctorAppointmentString(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm a");

        Duration duration = appointment.getDuration();
        int days = (int) duration.toDays();

        duration = duration.minusDays(days);
        int hours = (int) duration.toHours();

        duration = duration.minusHours(hours);
        int minutes = (int) duration.toMinutes();

        String string = "";
        string += "\nAppointment with patient " + getPatient(appointment.getPatientId()).getUserName() + ":";
        string += "\nStarts on " + appointment.getStartDate().format(formatter);
        string += "\nEnds on " + appointment.getEndDate().format(formatter);
        string += "\nDuration:";
        if (days != 0) {
            string += " " + days + " days";
        }
        if (hours != 0) {
            string += " " + hours + " hours";
        }
        if (minutes != 0) {
            string += " " + minutes + " minutes";
        }

        return string;
    }
}
