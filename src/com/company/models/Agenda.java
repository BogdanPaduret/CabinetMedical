package com.company.models;

import com.company.repositories.AppointmentRepository;
import com.company.repositories.UserRepository;

import java.util.*;

public class Agenda {
    //contine id doctor si id programare

    //instance variables
    private Map<Doctor, Set<Appointment>> appointmentMap;
    private Set<User> users;
    private Set<Appointment> appointments;

    //constructor
    public Agenda() {
        this("test/com/company/repositories/users", "test/com/company/repositories/appointments");
    }
    public Agenda(String usersPath,String appointmentsPath) {
        appointmentMap = new TreeMap<>();

        UserRepository ur = new UserRepository(usersPath);
        AppointmentRepository ar = new AppointmentRepository(appointmentsPath);

//        cod comentat in cazul in care in repositories nu voi avea load-ul in constructor
//        ur.load();
//        ar.load();

        users = ur.getAll();
        appointments = ar.getAll();

        matchDoctorsAppointments(users, appointments);
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

    public Set<Patient> getAllPatients() {
        Set<Patient> patients = new TreeSet<>();

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user instanceof Patient patient) {
                patients.add(patient);
            }
        }
        return patients;
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
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user instanceof Doctor doctor) {
                if (doctor.getUserId() == id) {
                    return doctor;
                }
            }
        }
        throw new NoSuchElementException("Doctor does not exist");
    }




}
