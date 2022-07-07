package com.company.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addAppointment() {
    }

    @Test
    void getDoctorAppointments() {
    }

    @Test
    public void constructorTest() {
        String usersPath = "test/com/company/repositories/users";
        String appointmentsPath = "test/com/company/repositories/appointments";
        Agenda agenda = new Agenda(usersPath, appointmentsPath);
    }
}