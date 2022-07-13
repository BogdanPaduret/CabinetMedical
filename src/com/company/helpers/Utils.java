package com.company.helpers;

import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.Secretary;
import com.company.models.User;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import static com.company.helpers.Constants.*;

public final class Utils {
    //users
    public static boolean checkUserType(String type) {
        for (int i = 0; i < USERS_ARRAY.length; i++) {
            if (USERS_ARRAY[i].equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static User createUser(String line) {
        String[] input = line.split(SAVE_SEPARATOR);
        String userType = input[0];
        int userId = Integer.parseInt(input[1]);
        String userName = input[2];

        return createUser(userType, userId, userName);
    }
    public static User createUser(String type, int id, String name) {
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
            RepositoryLoad.userRepository.save();
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

    public static Hashtable<String, Set> createEmptyUserSetsHashtable() {
        Hashtable<String,Set> hashtable = new Hashtable<>();

        Set<User> doctors = new TreeSet<>();
        Set<Patient> patients = new TreeSet<>();
        Set<Secretary> secretaries = new TreeSet<>();

        hashtable.put(USER_DOCTOR, doctors);
        hashtable.put(USER_PATIENT, patients);
        hashtable.put(USER_SECRETARY, secretaries);

        return hashtable;
    }

}
