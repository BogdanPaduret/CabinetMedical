package com.company.views;

import com.company.models.Secretary;

import java.util.Scanner;

import static com.company.helpers.Utils.getScanner;

public class SecretaryView implements View {

    private Secretary secretary;

    public SecretaryView(Secretary secretary) {
        this.secretary = secretary;
    }

    public void menu() {
        String string = "";

        string += "Sunteti logat ca " + secretary.getUserName();
        string += "\nApasati 1 pentru a crea o programare";
        string += "\nApasati 2 pentru a anula o programare";
        string += "\nApasati 3 pentru a vedea toate programarile unui doctor";
        string += "\nApasati 4 pentru a vedea toate programarile unui pacient";
        string += "\nApasati 0 pentru a iesi";

        System.out.println(string);
    }
    @Override
    public void play() {
        this.play("");
    }
    public void play(String input) {
        Scanner scanner = getScanner(input);

        boolean running = true;
        int choice = -1;

        while (running) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            switch (choice) {
                case 0:
                    running = !exit(scanner);
            }
        }

    }

    private boolean exit(Scanner scanner) {
        return true;
    }
}
