package com.company.helpers;

import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.Secretary;
import com.company.models.User;
import com.company.repositories.AppointmentRepository;
import com.company.repositories.UserRepository;

import java.util.Scanner;

import static com.company.helpers.Constants.*;

public final class RepositoryLoad<T> {

    //instance variables
    private static String usersPath;
    private static String appointmentsPath;

    //constructor
    private RepositoryLoad() {}

    //initialize repositories
    public static void pathInit(String usersPath, String appointmentsPath) {
        RepositoryLoad.usersPath = usersPath;
        RepositoryLoad.appointmentsPath = appointmentsPath;
    }

    //repositories
    public static final UserRepository userRepository = new UserRepository(RepositoryLoad.usersPath);
    public static final AppointmentRepository appointmentRepository = new AppointmentRepository(RepositoryLoad.appointmentsPath);


}
