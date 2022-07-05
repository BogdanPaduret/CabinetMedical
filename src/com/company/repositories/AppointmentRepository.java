package com.company.repositories;

import com.company.models.Appointment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    private List<Appointment> appointments;
    private String path;

    public AppointmentRepository(String path) {
        this.path = path;
        this.appointments = new ArrayList<>();
        this.load();
    }

    public AppointmentRepository(String path, List<Appointment> appointments) {
        this.path = path;
        this.appointments = appointments;
    }

    //create
    public boolean save() {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(appointments);
            oos.flush();
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //read
    public void load() {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            appointments = (ArrayList<Appointment>) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public String getPath() {
        return path;
    }

    public void show() {
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println(appointments.get(i).toString() + "\n");
        }
    }

    //update
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
