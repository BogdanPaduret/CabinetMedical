package com.company.repositories;

import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    String path = "test/com/company/repositories/users";

    @Test
    public void saveTest() {
        List<User> doctors = new ArrayList<>();

        doctors.add(new Doctor(0, "Petrescu Marian"));
        doctors.add(new Doctor(1, "Neculce Dumitru"));
        doctors.add(new Patient(2, "Dimitrie Cantemir"));
        doctors.add(new Doctor(3, "Cinevescu Ioan"));
        doctors.add(new Patient(4, "Cutarescu Vladimir"));
        doctors.add(new Patient(5, "Slovescu Adrian"));

        UserRepository ur = new UserRepository(path, doctors);

        System.out.println(ur.show());
        String show = "Doctor ID: 0\n" +
                "Doctor Name: Petrescu Marian\n" +
                "Doctor ID: 1\n" +
                "Doctor Name: Neculce Dumitru\n" +
                "Patient ID: 2\n" +
                "Patient name: Dimitrie Cantemir\n" +
                "Doctor ID: 3\n" +
                "Doctor Name: Cinevescu Ioan\n" +
                "Patient ID: 4\n" +
                "Patient name: Cutarescu Vladimir\n" +
                "Patient ID: 5\n" +
                "Patient name: Slovescu Adrian\n";
        assertEquals(6, ur.size());
        assertEquals(show, ur.show());

        ur.save();
        ur = new UserRepository(path);
        assertEquals(6, ur.size());

//        assertDoesNotThrow();
        ur.add("doctor", "Doctorescu Matei")

        assertEquals(7, ur.size());
        assertEquals(show + "Doctor ID: 6\n" +
                        "Doctor Name: Doctorescu Matei\n",
                ur.show());
    }

}