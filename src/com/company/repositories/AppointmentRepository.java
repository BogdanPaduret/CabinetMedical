package com.company.repositories;

import com.company.models.Appointment;

import java.io.*;
import java.util.*;

public class AppointmentRepository implements Repository<Appointment>{

    private Set<Appointment> appointments;
    private String path;

    public AppointmentRepository(String path) {
        this.path = path;
        this.appointments = new TreeSet<>();
        this.load();
    }
    public AppointmentRepository(String path, Collection<Appointment> appointments) {
        this.path = path;
        this.appointments = new TreeSet<>();
        this.appointments.addAll(appointments);
    }

    //create
    @Override
    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(appointments);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void add(Appointment appointment) {
        appointments.add(appointment);
    }

    //read
    @Override
    public void load() {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            appointments = (TreeSet<Appointment>) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        Iterator<Appointment> iterator = appointments.iterator();
        int c = 0;
        while (iterator.hasNext()) {
            c++;
            iterator.next();
        }
        return c;
    }
    @Override
    public Appointment get(int id) throws NoSuchElementException {
        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();
            if (appointment.getAppointmentId() == id) {
                return appointment;
            }
        }
        throw new NoSuchElementException("Appointment with entered ID does not exist");
    }
    @Override
    public Set<Appointment> getAll() {
        return appointments;
    }
    public String getPath() {
        return path;
    }

    public void show() {
        Iterator<Appointment> iterator = appointments.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString() + "\n");
        }
    }

    //update
    @Override
    public void set(int id, Appointment appointment) {
        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            Appointment a = iterator.next();
            if (a.getAppointmentId() == id) {
                a.set(appointment);
            }
        }
    }
    @Override
    public void addAll(Collection<Appointment> appointments) {
        this.appointments.addAll(appointments);
    }
    public void setPath(String path) {
        this.path = path;
    }

    //delete
    @Override
    public void clear() {
        appointments.clear();
    }
    @Override
    public void remove(Appointment appointment) {
        appointments.remove(appointment);
    }
}
