package com.company.models;

import com.company.helpers.RepositoryLoad;

import java.util.*;

import static com.company.helpers.Constants.USER_DOCTOR;
import static com.company.helpers.Constants.USER_PATIENT;

public class Agenda {
    //contine id doctor si id programare

    //instance variables
    private Map<User, Set<Appointment>> userAppointmentsMap;

    //constructor
    public Agenda(String usersPath,String appointmentsPath) {
        userAppointmentsMap = new TreeMap<>();

        matchUserAppointments(RepositoryLoad.userRepository.getAll(), RepositoryLoad.appointmentRepository.getAll());
    }
    public Agenda(Map<User, Set<Appointment>> userAppointmentsMap) {
        this.userAppointmentsMap = userAppointmentsMap;
    }

    //create
    public void addAppointment(Doctor doctor, Appointment appointment) {
        if (userAppointmentsMap.containsKey(doctor)) {
            Set<Appointment> appointments = userAppointmentsMap.get(doctor);
            if (appointments == null) {
                appointments = new TreeSet<>();
            }

            appointments.add(appointment);
            refreshMap(doctor, appointments);

        } else {
            throw new NoSuchElementException("Doctor does not exist");
        }
    }

    //read
    public Collection<Appointment> getUserAppointments(User user) {
        return userAppointmentsMap.get(user);
    }

    //updateAdd
    public void updateAppointment(Doctor doctor, int appointmentId, Appointment appointment) {
        if (userAppointmentsMap.containsKey(doctor)) {
            Set<Appointment> appointments = userAppointmentsMap.get(doctor);
            Iterator<Appointment> iterator = appointments.iterator();
            while (iterator.hasNext()) {
                Appointment a = iterator.next();
                if (a.getAppointmentId() == appointmentId) {
                    if (doctor.getUserId() == appointment.getDoctorId()) {
                        a.set(appointment);
                        refreshMap(doctor, appointments);
                    } else {
                        appointments.remove(appointment);
                        refreshMap(doctor, appointments);
                        doctor = getDoctorById(appointment.getDoctorId());
                        addAppointment(doctor, appointment);
                    }
                }
            }
        } else {
            throw new NoSuchElementException("Doctor does not exist");
        }
    }

    //delete
    public void removeAppointment(Doctor doctor, Appointment appointment) {
        if (userAppointmentsMap.containsKey(doctor)) {
            Set<Appointment> appointments = userAppointmentsMap.get(doctor);
            if (appointments.contains(appointment)) {
                appointments.remove(appointment);
                refreshMap(doctor, appointments);
            } else {
                throw new NoSuchElementException("Appointment does not exist");
            }
        } else {
            throw new NoSuchElementException("Doctor does not exist");
        }
    }

    //helper methods
    private void matchUserAppointments(Set<User> users, Set<Appointment> appointments) {
        for (User user : users) {
            userAppointmentsMap.put(user, matchAppointments(user, appointments));
        }
    }

    private Set<Appointment> matchAppointments(User user, Set<Appointment> allAppointments) {
        Set<Appointment> userAppointments = new TreeSet<>();

        Iterator<Appointment> iterator = allAppointments.iterator();

        while (iterator.hasNext()) {

            Appointment appointment = iterator.next();
            if (appointment.getPatientId() == user.getUserId()) {
                userAppointments.add(appointment);
            }
            if (appointment.getDoctorId() == user.getUserId()) {
                userAppointments.add(appointment);
            }
        }

        return userAppointments;
    }

    private void refreshMap(Doctor doctor, Set<Appointment> appointments) {
        userAppointmentsMap.remove(doctor);
        userAppointmentsMap.put(doctor, appointments);
    }

    private Doctor getDoctorById(int id) {
        User user = RepositoryLoad.userRepository.get(id);
        if (user instanceof Doctor doctor) {
            return doctor;
        }
        throw new NoSuchElementException("Doctor with given ID does not exist");
    }




}
