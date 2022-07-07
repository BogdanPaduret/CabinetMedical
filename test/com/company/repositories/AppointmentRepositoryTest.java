package com.company.repositories;

import com.company.models.Appointment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentRepositoryTest {

    @Test
    public void saveLoadTest() {

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(0,1,2, "2022,8,5,20,30", "2022,8,5,21,0"));
        appointments.add(new Appointment(1,0,1, 2022, 8, 6, 20, 30, 2022, 8, 6, 21, 0));
        appointments.add(new Appointment(2,0,2, 2021, 9, 7, 21, 0, 2023, 9, 7, 22, 0));
        appointments.add(new Appointment(3,1,0, "2024,10,8,16,52", "2024,10,8,18,33"));

        String path = "test/com/company/repositories/appointments";
        AppointmentRepository appointmentRepository = new AppointmentRepository(path, appointments);

        assertEquals(4, appointmentRepository.getAppointments().size());
        System.out.println("\n\n\n=============== Elementele inainte de salvare/incarcare ===============");
        appointmentRepository.show();

        appointmentRepository.save();
        appointmentRepository = new AppointmentRepository(path);
        assertEquals(4, appointmentRepository.getAppointments().size());
        System.out.println("\n\n\n=============== Elementele dupa salvare/incarcare ===============");
        appointmentRepository.show();

    }

}