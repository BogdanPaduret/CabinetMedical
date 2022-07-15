package com.company.models;

import com.company.helpers.RepositoryLoad;

import java.util.*;

import static com.company.helpers.Constants.USER_DOCTOR;
import static com.company.helpers.Constants.USER_PATIENT;

public class Agenda {
    //contine id doctor si id programare

    //instance variables
    private Map<Doctor, Set<Appointment>> doctorAppointmentsMap;
    private Map<Doctor, Set<Patient>> doctorPatientsMap;
    private Map<Patient, Set<Doctor>> patientDoctorsMap;
    private Map<Patient, Set<Appointment>> patientAppointmentsMap;

    //constructor
    public Agenda(String usersPath,String appointmentsPath) {
        doctorAppointmentsMap = new TreeMap<>();
        patientAppointmentsMap = new TreeMap<>();

        matchUserAppointments(RepositoryLoad.userRepository.getAll(), RepositoryLoad.appointmentRepository.getAll());
    }
    public Agenda(Map<Doctor, Set<Appointment>> doctorAppointmentsMap) {
        this.doctorAppointmentsMap = doctorAppointmentsMap;
    }

    //create
    public void addAppointment(Doctor doctor, Appointment appointment) {
        if (doctorAppointmentsMap.containsKey(doctor)) {
            Set<Appointment> appointments = doctorAppointmentsMap.get(doctor);
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
        return doctorAppointmentsMap.get(doctor);
    }
    public Collection<Appointment> getPatientAppointments(Patient patient) {
        return patientAppointmentsMap.get(patient);
    }

    //updateAdd
    public void updateAppointment(Doctor doctor, int appointmentId, Appointment appointment) {
        if (doctorAppointmentsMap.containsKey(doctor)) {
            Set<Appointment> appointments = doctorAppointmentsMap.get(doctor);
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
        if (doctorAppointmentsMap.containsKey(doctor)) {
            Set<Appointment> appointments = doctorAppointmentsMap.get(doctor);
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
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user instanceof Doctor doctor) {
                doctorAppointmentsMap.put(doctor, matchAppointments(doctor, appointments));
            }
            if (user instanceof Patient patient) {
                patientAppointmentsMap.put(patient, matchAppointments(patient, appointments));
            }
        }
    }

    private Set<Appointment> matchAppointments(User user, Set<Appointment> allAppointments) {
        Set<Appointment> userAppointments = new TreeSet<>();

        Iterator<Appointment> iterator = allAppointments.iterator();

        String userType = user.getClass().getSimpleName().toLowerCase();

        switch (userType) {
            case USER_DOCTOR -> {
                Doctor doctor = (Doctor) user;
                while (iterator.hasNext()) {
                    Appointment appointment = iterator.next();
                    if (appointment.getDoctorId() == doctor.getUserId()) {
                        userAppointments.add(appointment);
                    }
                }
            }
            case USER_PATIENT -> {
                Patient patient = (Patient) user;
                while (iterator.hasNext()) {
                    Appointment appointment = iterator.next();
                    if (appointment.getPatientId() == patient.getUserId()) {
                        userAppointments.add(appointment);
                    }
                }
            }
        }

        return userAppointments;
    }

    private void refreshMap(Doctor doctor, Set<Appointment> appointments) {
        doctorAppointmentsMap.remove(doctor);
        doctorAppointmentsMap.put(doctor, appointments);
    }

    private Doctor getDoctorById(int id) {
        User user = RepositoryLoad.userRepository.get(id);
        if (user instanceof Doctor doctor) {
            return doctor;
        }
        throw new NoSuchElementException("Doctor with given ID does not exist");
    }




}
