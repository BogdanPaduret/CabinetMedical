package com.company.views;

import com.company.helpers.Utils;
import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.Secretary;
import com.company.models.User;
import com.company.repositories.UserRepository;

import java.util.*;

import static com.company.helpers.Utils.*;

public class ViewLogIn implements View {

    //instance variables
    User user;


    //constructors
    public ViewLogIn(String repositoriesPath) {
        String usersPath = repositoriesPath + "/users";
        String appointmentsPath = repositoriesPath + "/appointments";

        Utils.pathInit(usersPath, appointmentsPath);

        user = null;
    }


    //menus
    private void menu() {
        String string = "";

        if (user != null) {
            string += "\nLogat ca: " + user.getUserName();
            string += "\nApasati 1 pentru a schimba utilizatorul";
        } else {
            string += "\nApasati 1 pentru a va loga";
        }

        string += "\nApasati 2 pentru a va inregistra";

        if (user != null) {
            string += "\nApasati 3 pentru a intra in aplicatie ca " + user.getUserName();
        }

        string += "\nApasati 0 pentru a iesi";

        System.out.println(string);
    }

    //play
    public void play() {
        this.play("");
    }
    public void play(String inputStrings) {
        Scanner scanner = getScanner(inputStrings);

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
                    user = null;
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
            toSaveOrNotToSaveUsers(scanner);
            return true;
        }
        return false;
    }

    private void enterApp() {
        if (user != null) {
            View view = null;

            if (user instanceof Doctor doctor) {
                view = new DoctorView(doctor);
            }
            if (user instanceof Patient patient) {
                view = new PatientView(patient);
            }
            if (user instanceof Secretary secretary) {
                view = new SecretaryView(secretary);
            }

            if (view != null) {
                view.play();
            } else {
                throw new NoSuchElementException("O eroare a aparut la crearea acestei ferestre. Verificati daca acest tip de utilizator are un View implementat");
            }
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
        if (input.length != 2 || !checkUserType(type)) {
            System.out.println(abort);
        } else {
            String name = input[1];
            userRepository.add(type,name);
        }

    }

    private void setUser(int id, String name) throws NoSuchElementException {
        for (User proxy : Utils.userRepository.getAll()) {
            if (proxy.getUserId() == id && proxy.getUserName().equals(name)) {
                user = proxy;
            }
        }
        if (user == null) {
            throw new NoSuchElementException("Invalid user input");
        }
    }

}
