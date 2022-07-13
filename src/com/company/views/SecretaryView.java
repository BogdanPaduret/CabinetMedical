package com.company.views;

import com.company.helpers.RepositoryLoad;
import com.company.helpers.Utils;
import com.company.models.Secretary;
import com.company.repositories.Observed;
import com.company.repositories.Repository;

import java.util.ArrayList;
import java.util.Scanner;

import static com.company.helpers.Utils.getScanner;
import static com.company.helpers.Utils.updateRepositoryListAfterChange;

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
                    running = !Utils.exitAskSave(scanner, changedRepositories.toArray(new Repository<?>[0]));
            }
        }

    }

}
