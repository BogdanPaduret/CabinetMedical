package com.company.models;

import com.company.helpers.RepositoryLoad;

import java.util.*;

public class Agenda {
    //contine id doctor si id programare

    //instance variables
    private Map<Doctor, Set<Appointment>> appointmentMap;

    //constructor
    public Agenda(String usersPath,String appointmentsPath) {
        appointmentMap = new TreeMap<>();

        matchDoctorsAppointments(RepositoryLoad.userRepository.getAll(), RepositoryLoad.appointmentRepository.getAll());
    }
    public Agenda(Map<Doctor, Set<Appointment>> appointmentMap) {
        this.appointmentMap = appointmentMap;
    }

    //create
    public void addAppointment(Doctor doctor, Appointment appointment) {
        if (appointmentMap.containsKey(doctor)) {
            Set<Appointment> appointments = appointmentMap.get(doctor);
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
    public Collection<Appointment> getDoctorAppointments(Doctor doctor) {
        return appointmentMap.get(doctor);
    }

    //update
    public void updateAppointment(Doctor doctor, int appointmentId, Appointment appointment) {
        if (appointmentMap.containsKey(doctor)) {
            Set<Appointment> appointments = appointmentMap.get(doctor);
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
        if (appointmentMap.containsKey(doctor)) {
            Set<Appointment> appointments = appointmentMap.get(doctor);
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
    private void matchDoctorsAppointments(Set<User> users, Set<Appointment> appointments) {
        Iterator<User> iterator = users.iterator();
        Set<Appointment> doctorAppointments;
        while (iterator.hasNext()) {
            Doctor doctor = null;
            User user = iterator.next();
            if (user instanceof Doctor) {
                doctor = (Doctor) user;
            }
            if (doctor != null) {
                doctorAppointments = matchAppointments(doctor, appointments);
                appointmentMap.put(doctor, doctorAppointments);
            }
        }
    }

    private Set<Appointment> matchAppointments(Doctor doctor, Set<Appointment> allAppointments) {
        Set<Appointment> doctorAppointments = new TreeSet<>();

        Iterator<Appointment> iterator = allAppointments.iterator();

        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();
            if (appointment.getDoctorId() == doctor.getUserId()) {
                doctorAppointments.add(appointment);
            }
        }

        return doctorAppointments;
    }

    private void refreshMap(Doctor doctor, Set<Appointment> appointments) {
        appointmentMap.remove(doctor);
        appointmentMap.put(doctor, appointments);
    }

    private Doctor getDoctorById(int id) {
        User user = RepositoryLoad.userRepository.get(id);
        if (user instanceof Doctor doctor) {
            return doctor;
        }
        throw new NoSuchElementException("Doctor with given ID does not exist");
    }




}
