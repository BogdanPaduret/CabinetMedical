package com.company.models;

import com.company.repositories.AppointmentRepository;
import com.company.repositories.UserRepository;

import java.util.*;

public class Agenda {
    //contine id doctor si id programare

    //instance variables
    private Map<Doctor, Set<Appointment>> appointmentMap;

    //constructor
    public Agenda() {
        this("test/com/company/repositories/users", "test/com/company/repositories/appointments");
    }
    public Agenda(String usersPath,String appointmentsPath) {
        appointmentMap = new TreeMap<>();

        UserRepository ur = new UserRepository(usersPath);
        AppointmentRepository ar = new AppointmentRepository(appointmentsPath);

        matchDoctorsAppointments(ur, ar);
    }
    public Agenda(Map<Doctor, Set<Appointment>> appointmentMap) {
        this.appointmentMap = appointmentMap;
    }

    //create
    public void addAppointment(Doctor doctor, Appointment appointment) {
        Set<Appointment> appointments = appointmentMap.get(doctor);

        if (appointments == null) {
            appointments = new TreeSet<>();
        }

        appointments.add(appointment);
        appointmentMap.remove(doctor);
        appointmentMap.put(doctor, appointments);

    }

    //read
    public Appointment[] getDoctorAppointments(Doctor doctor) {
        //pentru a returna ca vector, mai intai incarcam lista si o cast-uim intr-un arraylist
        ArrayList<Appointment> appointmentsList = new ArrayList<>();
        appointmentsList.addAll(appointmentMap.get(doctor));

        //cod convertire ArrayList in vector
        Appointment[] appointments = new Appointment[0];
        appointments = appointmentsList.toArray(appointments);

        return appointments;
    }

    //helper methods
    private void matchDoctorsAppointments(UserRepository ur, AppointmentRepository ar) {
        List<User> users = new ArrayList<>();
        users.addAll(ur.getUsers());
        Set<Appointment> appointments = null;
        for (int i = 0; i < users.size(); i++) {
            Doctor doctor = null;
            if (users.get(i) instanceof Doctor) {
                doctor = (Doctor) users.get(i);
            }
            if (doctor != null) {
                appointments = matchAppointments(doctor, ar);
                appointmentMap.put(doctor, appointments);
            }
        }
    }
    private Set<Appointment> matchAppointments(Doctor doctor, AppointmentRepository ar) {
        List<Appointment> allAppointments = ar.getAppointments();
        Set<Appointment> doctorAppointments = new TreeSet<>();

        for (int i = 0; i < allAppointments.size(); i++) {
            Appointment appointment = allAppointments.get(i);
            if (appointment.getDoctorId() == doctor.getUserId()) {
                doctorAppointments.add(appointment);
            }
        }

        return doctorAppointments;
    }




}
