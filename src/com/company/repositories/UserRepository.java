package com.company.repositories;

import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import static com.company.helpers.Constants.*;
import static com.company.helpers.Utils.checkType;

public class UserRepository {

    //instance variables
    private TreeSet<User> users;
    private String path;

    //constructor
    public UserRepository(String path) {
        this.users = new TreeSet<>();
        this.path = path;
        this.load();
    }
    public UserRepository(String path, Collection<User> users) {
        users = new TreeSet<>();
        this.users.addAll(users);
        this.path = path;
    }

    //create
    public void save() {
        try {
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.print(toSave());
            printWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean addUser(String type, String name) {
        if (checkType(type)) {
            users.add(createUser(type, name));
            return true;
        } else {
            return false;
        }
    }

    //read
    private void load() {
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
    public Set<User> getUsers() {
        return users;
    }
    public boolean exists(int id, String name) {
        for (User user : users) {
            if (user.getUserId() == id && user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public User getUserById(int id) throws NoSuchElementException{
        for (User user : users) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        throw new NoSuchElementException();
    }

    //helpers
    private String toSave() {
        String string = "";

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            string += user.saveString() + "\n";
        }

        return string;
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
    private User createUser(String type, String name) {
        int id = generateNewId();
        return createUser(type, id, name);
    }
    private User createUser(String type, int id, String name) {
        switch (type) {
            default:
                return null;
            case USER_DOCTOR:
                return new Doctor(id, name);
            case USER_PATIENT:
                return new Patient(id, name);
        }
    }
    private int generateNewId() {
        return users.last().getUserId() + 1;
    }

}
