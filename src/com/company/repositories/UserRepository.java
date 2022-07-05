package com.company.repositories;

import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.company.helpers.Constants.*;

public class UserRepository {


    private List<User> users;


    public UserRepository(String path) {
        this.users = new ArrayList<>();
        this.load(path);
    }

    private void load(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                User user = createUser(line);
                if (user != null) {
                    try {
                        users.add(user);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private User createUser(String line) {
        String[] input = line.split(SAVE_SEPARATOR);
        String userType = input[0];
        int userId = Integer.parseInt(input[1]);
        String userName = input[2];

        switch (userType) {
            default:
                return null;
            case USER_DOCTOR:
                return new Doctor(userId, userName);
            case USER_PATIENT:
                return new Patient(userId, userName);
        }

    }

    public List<User> getUsers() {
        return users;
    }


}
