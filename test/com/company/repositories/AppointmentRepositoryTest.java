package com.company.repositories;

import com.company.models.Appointment;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentRepositoryTest {

    String path = "test/com/company/repositories/appointments";

    @Test
    public void saveLoadTest() {

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(0,1,2, "2022,8,5,20,30", "2022,8,5,21,0"));
        appointments.add(new Appointment(1,0,1, 2022, 8, 6, 20, 30, 2022, 8, 6, 21, 0));
        appointments.add(new Appointment(2,0,2, 2021, 9, 7, 21, 0, 2023, 9, 7, 22, 0));
        appointments.add(new Appointment(3,1,0, "2024,10,8,16,52", "2024,10,8,18,33"));

        AppointmentRepository appointmentRepository = new AppointmentRepository(path, appointments);

        assertEquals(4, appointmentRepository.getAll().size());
        System.out.println("\n\n\n=============== Elementele inainte de salvare/incarcare ===============");
        appointmentRepository.show();

        appointmentRepository.save();
        appointmentRepository.clear();
        appointmentRepository.load();
        assertEquals(4, appointmentRepository.getAll().size());
        System.out.println("\n\n\n=============== Elementele dupa salvare/incarcare ===============");
        appointmentRepository.show();

    }

    @Test
    public void addSizeGetGetAllTest() {
        AppointmentRepository ar = new AppointmentRepository(path);
        assertEquals(0, ar.size());
        Appointment appointment = new Appointment(0, 1, 2, 2022, 9, 11, 20, 30, 2022, 9, 11, 21, 0);
        ar.add(appointment);
        assertEquals(1, ar.size());
        assertEquals(appointment, ar.get(0));

        Set<Appointment> set = new TreeSet<>();
        set.add(appointment);
        assertEquals(set, ar.getAll());
    }

    @Test
    public void addAllTest() {
        Appointment[] appointments = {
                new Appointment(0, 1, 2, 2022, 9, 11, 20, 30, 2022, 9, 12, 21, 0),
                new Appointment(1, 1, 1, 2022, 9, 12, 20, 30, 2022, 9, 12, 21, 0),
        };

        for (int i = 0; i < appointments.length; i++) {

        }


        AppointmentRepository ar = new AppointmentRepository(path);
        ar.addAll(Arrays.stream(appointments).toList());
        assertEquals(2, ar.size());
        assertEquals(appointments[0], ar.get(0));
        assertEquals(appointments[1], ar.get(1));
    }

    @Test
    public void removeTest() {
        Appointment appointment = new Appointment(0, 1, 2, 2022, 9, 11, 20, 30, 2022, 9, 12, 21, 0);
        AppointmentRepository ar = new AppointmentRepository(path);
        ar.add(appointment);
        assertEquals(1, ar.size());
        ar.remove(appointment);
        assertEquals(0, ar.size());
    }

    @Test
    public void pathTest() {
        AppointmentRepository ar = new AppointmentRepository("");
        ar.setPath(path);
        assertEquals(path, ar.getPath());
        ar.load();
        assertEquals(4, ar.size());
    }

}