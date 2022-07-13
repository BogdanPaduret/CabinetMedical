package com.company.helpers;

import com.company.repositories.AppointmentRepository;
import com.company.repositories.UserRepository;

public final class RepositoryLoad<T> {

    //instance variables
    private static String usersPath="test/com/company/repositories/users";
    private static String appointmentsPath="test/com/company/repositories/appointments";

    //constructor
    private RepositoryLoad() {}

    //initialize repositories
    public static void init(String usersPath, String appointmentsPath) {
        RepositoryLoad.usersPath = usersPath;
        RepositoryLoad.appointmentsPath = appointmentsPath;
        userRepository.load();
        appointmentRepository.load();
    }

    //repositories
    public static final UserRepository userRepository = new UserRepository(RepositoryLoad.usersPath);
    public static final AppointmentRepository appointmentRepository = new AppointmentRepository(RepositoryLoad.appointmentsPath);

}
