package com.company.views;

import com.company.helpers.RepositoryLoad;
import com.company.helpers.Utils;
import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.Secretary;
import com.company.models.User;
import com.company.repositories.Observed;
import com.company.repositories.Repository;

import java.util.*;

public class ViewLogIn implements View {

    //instance variables
    private User user;

    //constructors
    public ViewLogIn(String repositoriesPath) {
        String usersPath = repositoriesPath + "/users";
        String appointmentsPath = repositoriesPath + "/appointments";

        RepositoryLoad.init(usersPath, appointmentsPath);

        user = null;

        RepositoryLoad.userRepository.registerObserver(this);
        RepositoryLoad.appointmentRepository.registerObserver(this);

    }


    //menus
    private void menu() {
        String string = "";

        if (user != null) {
            string += "\nSunteti logat ca: " + user.getUserName().toUpperCase();
            string += "\nApasati 1 pentru a schimba utilizatorul";
        } else {
            string += "\nApasati 1 pentru a va loga";
        }

        string += "\nApasati 2 pentru a va inregistra";

        if (user != null) {
            string += "\nApasati 3 pentru a intra in aplicatie ca " + user.getUserName();
        }
        if (!changedRepositories.isEmpty()) {
            string += "\nApasati 9 pentru a salva modificarile";
        }

        string += "\nApasati 0 pentru a iesi";

        System.out.println(string);
    }

    //play
    public void play() {
        this.play("");
    }
    public void play(String inputStrings) {
        Scanner scanner = Utils.getScanner(inputStrings);

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
                default -> System.out.println("Optiunea nu exista.\nIncercati din nou.");
                case 0 -> running = !Utils.exitAskSave(scanner, changedRepositories.toArray(new Repository<?>[0]));
                case 1 -> logIn(scanner);
                case 2 -> register(scanner);
                case 3 -> {
                    enterApp();
                    user = null;
                }
                case 9 -> Utils.toSaveOrNotToSave(scanner, changedRepositories.toArray(new Repository<?>[0]));
            }
        }
    }


    //helper methods

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
        if (input.length != 2 || !Utils.typeExists(type)) {
            System.out.println(abort);
        } else {
            String name = input[1];
            RepositoryLoad.userRepository.add(type,name);
        }

    }

    private void setUser(int id, String name) throws NoSuchElementException {
        for (User proxy : RepositoryLoad.userRepository.getAll()) {
            if (proxy.getUserId() == id && proxy.getUserName().equals(name)) {
                user = proxy;
            }
        }
        if (user == null) {
            throw new NoSuchElementException("Invalid user input");
        }
    }

}
