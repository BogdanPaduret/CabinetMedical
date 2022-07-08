package com.company.views;

import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.User;
import com.company.repositories.UserRepository;

import java.util.*;

import static com.company.helpers.Constants.USER_DOCTOR;
import static com.company.helpers.Constants.USER_PATIENT;
import static com.company.helpers.Utils.checkType;

public class ViewLogIn implements View {

    //instance variables
    Set<User> users;
    User user;
    private String path;


    //constructors
    public ViewLogIn(String rootPath) {
        this.path = rootPath + "com/company/repositories/users";
        UserRepository userRepository = new UserRepository(path);
        users.addAll(userRepository.getUsers());

        user = null;
    }


    //menus
    private void menu() {
        String string = "";

        if (user != null) {
            string += "\nLogat ca: " + user.getName();
            string += "\nApasati 1 pentru a schimba utilizatorul";
        } else {
            string += "\nApasati 1 pentru a va loga";
        }

        string += "\nApasati 2 pentru a va inregistra";

        if (user != null) {
            string += "\nApasati 3 pentru a intra in aplicatie ca " + user.getName();
        }

        string += "\nApasati 0 pentru a iesi";

        System.out.println(string);
    }

    //play
    public void play() {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        int choice = -1;

        while (running) {
            menu();

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            switch (choice) {
                case 3:
                    enterApp();
                default:
                    break;
                case 0:
                    running = !exit(scanner);
                    break;
                case 1:
                    logIn(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
            }
        }
    }

    //helper methods
    private boolean exit(Scanner scanner) {
        System.out.println("Sigur iesiti din aplicatie?");
        char ans = scanner.nextLine().toLowerCase().charAt(0);
        if (ans == 'y') {
            toSaveOrNotToSave(scanner);
            return true;
        }
        return false;
    }
    private void toSaveOrNotToSave(Scanner scanner) {
        System.out.println("Salvati?");
        char ans = scanner.nextLine().toLowerCase().charAt(0);
        if (ans == 'y') {
            UserRepository ur = new UserRepository(path, users);
            ur.save();
            System.out.println("Baza de date cu utilizatori SALVATA");
        } else {
            System.out.println("Baza de date cu utilizatori NU a fost SALVATA");
        }
    }

    private void enterApp() {
        if (user != null) {
            View view = null;

            if (user instanceof Doctor doctor) {
                view = new DoctorView(path, doctor);
            }
            if (user instanceof Patient patient) {
                view = new PatientView(path, patient);
            }

            view.play();
        }
    }

    private void logIn(Scanner scanner) {

        System.out.println("Introduceti id-ul si numele utilizatorului, separate prin virgula");
        String[] input = scanner.nextLine().split(",");
        if (input.length == 2) {
            int id = -1;
            try {
                id = Integer.parseInt(input[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            String name = input[1];

            try {
                setUser(id, name);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Not enough arguments");
        }

    }

    private void register(Scanner scanner) {
        System.out.println("Introduceti tipul si numele utilizatorului separate prin virgula");
        String[] input = scanner.nextLine().split(",");
        String abort = "Date incorecte! Nu s-a creat nici un utilizator nou.";

        String type = input[0];
        if (input.length != 2 || !checkType(type)) {
            System.out.println(abort);
        } else {
            String name = input[1];
            UserRepository ur = new UserRepository(path);
            ur.addUser(type, name);
        }

    }

    private void setUser(int id, String name) throws NoSuchElementException {
        for (User proxy : users) {
            if (proxy.getUserId() == id && proxy.getName().equals(name)) {
                user = proxy;
            }
        }
        throw new NoSuchElementException("Invalid input");
    }

}
