package com.company.helpers;

import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.Secretary;
import com.company.models.User;
import com.company.repositories.AppointmentRepository;
import com.company.repositories.UserRepository;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

import static com.company.helpers.Constants.*;

public abstract class Utils<T> {

    private static String usersPath;
    private static String appointmentsPath;

    public static void pathInit(String usersPath, String appointmentsPath) {
        Utils.usersPath = usersPath;
        Utils.appointmentsPath = appointmentsPath;
    }

    public static final UserRepository userRepository = new UserRepository(Utils.usersPath);
    public static final AppointmentRepository appointmentRepository = new AppointmentRepository(Utils.appointmentsPath);

    //users
    public static final boolean checkUserType(String type) {
        for (int i = 0; i < USERS_ARRAY.length; i++) {
            if (USERS_ARRAY[i].equals(type)) {
                return true;
            }
        }
        return false;
    }
    public static final User createUser(String line) {
        String[] input = line.split(SAVE_SEPARATOR);
        String userType = input[0];
        int userId = Integer.parseInt(input[1]);
        String userName = input[2];

        return createUser(userType, userId, userName);
    }
    public static final User createUser(String type, int id, String name) {
        switch (type) {
            default:
                return null;
            case USER_DOCTOR:
                return new Doctor(id, name);
            case USER_PATIENT:
                return new Patient(id, name);
            case USER_SECRETARY:
                return new Secretary(id, name);
        }
    }

    public static void toSaveOrNotToSaveUsers(Scanner scanner) {
        System.out.println("Salvati?");
        char ans = scanner.nextLine().toLowerCase().charAt(0);
        if (ans == 'y') {
            userRepository.save();
            System.out.println("Baza de date cu utilizatori a fost SALVATA");
        } else {
            System.out.println("Baza de date cu utilizatori NU a fost SALVATA");
        }
    }



    //misc
    public static Scanner getScanner(String input) {
        if (input.equals("")) {
            return new Scanner(System.in);
        } else {
            return new Scanner(input);
        }
    }

}
