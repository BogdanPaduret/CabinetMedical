package com.company.repositories;

import com.company.exceptions.AppointmentFailedException;
import com.company.helpers.RepositoryLoad;
import com.company.helpers.Utils;
import com.company.models.Appointment;
import com.company.views.Observer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class AppointmentRepository implements Repository<Appointment>{

    private TreeSet<Appointment> appointments;
    private String path;

    private ArrayList<Observer> observers;

    private void init(String path) {
        this.path = path;
        this.appointments = new TreeSet<>();
        this.observers = new ArrayList<>();
    }
    public AppointmentRepository(String path) {
        this.init(path);
    }
    public AppointmentRepository(String path, Collection<Appointment> appointments) {
        this.init(path);
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
        this.add(
                appointment.getDoctorId(),
                appointment.getPatientId(),
                appointment.getStartDate(),
                appointment.getEndDate()
        );
        notifyObservers();
    }
    public void add(
            int doctorId,
            int patientId,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        if (RepositoryLoad.isAppointmentAvailable(doctorId, patientId, startDate, endDate)) {
            try {
                appointments.add(Utils.createAppointment(generateNewId(), doctorId, patientId, startDate, endDate));
            } catch (AppointmentFailedException e) {
                e.printStackTrace();
            }
        }
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
        notifyObservers();
    }
    @Override
    public void addAll(Collection<Appointment> appointments) {
        this.appointments.addAll(appointments);
        notifyObservers();
    }
    public void setPath(String path) {
        this.path = path;
    }

    //delete
    @Override
    public void clear() {
        appointments.clear();
        notifyObservers();
    }
    @Override
    public void remove(Appointment appointment) {
        appointments.remove(appointment);
        notifyObservers();
    }

    //observer pattern
    @Override
    public void registerObserver(com.company.views.Observer observer) {
        observers.add(observer);
    }
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(this);
        }
    }

    //helpers
    private int generateNewId() {
        try {
            return appointments.last().getAppointmentId() + 1;
        } catch (NoSuchElementException e) {
            return 0;
        }
    }
}
