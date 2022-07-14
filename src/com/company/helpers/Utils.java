package com.company.helpers;

import com.company.exceptions.AppointmentDoesNotExistException;
import com.company.exceptions.AppointmentFailedException;
import com.company.models.*;
import com.company.repositories.Observed;
import com.company.repositories.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.company.helpers.Constants.*;

public final class Utils {
    //users
    public static boolean typeExists(String type) {
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

    //appointments
    public static Appointment createAppointment(int appointmentId, int doctorId, int patientId, LocalDateTime startDate, LocalDateTime endDate)
            throws AppointmentFailedException {

        if (RepositoryLoad.isAppointmentAvailable(doctorId, patientId, startDate, endDate)) {
            return new Appointment(appointmentId, doctorId, patientId, startDate, endDate);
        } else {
            throw new AppointmentFailedException();
        }

    }

    public static void updateAppointment(int id, Appointment appointment) {

    }

    //misc
    public static Scanner getScanner(String input) {
        if (input.equals("")) {
            return new Scanner(System.in);
        } else {
            return new Scanner(input);
        }
    }

    public static Hashtable<String, Set<User>> createEmptyUserSetsHashtable() {
        Hashtable<String,Set<User>> hashtable = new Hashtable<>();

        for (int i = 0; i < USERS_ARRAY.length; i++) {
            hashtable.put(USERS_ARRAY[i], new TreeSet<>());
        }

        return hashtable;
    }

    //manipulation methods
    public static boolean exitAskSave(Scanner scanner, Repository<?>[] repositories) {
        if (exit(scanner)) {
            if (repositories.length != 0) {
                toSaveOrNotToSave(scanner, repositories);
            }
            return true;
        }
        return false;
    }

    public static boolean exit(Scanner scanner) {
        System.out.println("Sigur iesiti din aplicatie?");
        char ans = scanner.nextLine().toLowerCase().charAt(0);
        if (ans == 'y') {
            return true;
        }
        if (ans == 'q') {
            System.exit(0);
        }
        return false;
    }

    public static boolean toSaveOrNotToSave(Scanner scanner, Repository<?>[] repositories) {
        System.out.println("Salvati?");
        char ans = scanner.nextLine().toLowerCase().charAt(0);
        String string = "Bazele de date";
        boolean truth;
        if (ans == 'y') {
            try {
                for (Repository<?> repository : repositories) {
                    repository.save();
                    string += "\n - " + repository.getClass().getSimpleName();
                }
                string += "\n";
                truth = true;
            } catch (Exception e) {
                e.printStackTrace();
                string += "\nnu ";
                truth = false;
            }
        } else {
            string += " nu ";
            truth = false;
        }
        string += "au fost salvate";
        System.out.println(string);
        return truth;
    }

    public static void addToRepositoryListAfterChange(List<Repository<?>> repositories, Observed observed) {
        try {
            if (!repositories.contains((Repository<?>) observed)) {
                repositories.add((Repository<?>) observed);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromRepositoryListAfterSave(List<Repository<?>> repositories, Observed observed) {
        try {
            repositories.remove((Repository<?>) observed);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
