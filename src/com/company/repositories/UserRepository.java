package com.company.repositories;

import com.company.models.Doctor;
import com.company.models.Patient;
import com.company.models.Secretary;
import com.company.models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import static com.company.helpers.Constants.*;
import static com.company.helpers.Utils.*;

public class UserRepository implements Repository<User>{

    //instance variables
    private Hashtable<String, Collection<User>> hashtable;
    private TreeSet<User> users;
    private String path;

    //constructor
    public UserRepository(String path) {
        this.users = new TreeSet<>();
        this.path = path;
        this.load();
    }
    public UserRepository(String path, Collection<User> users) {
        this.users = new TreeSet<>();
        this.users.addAll(users);
        this.path = path;
    }

    //create
    @Override
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
    @Override
    public void add(User user) {
        add(user.getType(), user.getUserName());
    }
    public void add(String type, String name) {
        if (checkUserType(type)) {
            users.add(createUser(type, generateNewId(), name));
        } else {
            throw new NoSuchElementException("Acest tip de utilizator nu exista.");
        }
    }

    //read
    @Override
    public void load() {
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

                    //todo de terminat codul pentru a umple hashtable-ul cu 3 intrari: 3 chei String doctor, patient, secretary si ca valori
                    if (user instanceof Doctor doctor) {
                        Set<User> doctors = new TreeSet<>();
                        if (hashtable.containsKey(USER_DOCTOR)) {
                            doctors.addAll(hashtable.get(USER_DOCTOR));
                        }
                        doctors.add(doctor);
                        hashtable.remove(USER_DOCTOR);
                        hashtable.put(USER_DOCTOR, doctors);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int size() {
        Iterator<User> iterator = users.iterator();
        int c = 0;
        while (iterator.hasNext()) {
            c++;
            iterator.next();
        }
        return c;
    }
    @Override
    public User get(int id) throws NoSuchElementException{
        for (User user : users) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        throw new NoSuchElementException();
    }
    @Override
    public Set<User> getAll() {
        return users;
    }
//    public Set<User> getAll(User user) {
//        Set<User> userSubset = new TreeSet<>();
//        Iterator<User> iterator = users.iterator();
//        while (iterator.hasNext()) {
//            User u = iterator.next();
//            if (user instanceof user.getClass()) {
//                userSubset.add(user);
//            }
//        }
//        return userSubset;
//    }
    public boolean exists(int id, String name) {
        for (User user : users) {
            if (user.getUserId() == id && user.getUserName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public String show() {
        String string = "";
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            string += user.toString() + "\n";
        }
        return string;
    }
    public String getPath() {
        return path;
    }

    //update
    public void setPath(String path) {
        this.path = path;
    }
    @Override
    public void set(int id, User user) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User u = iterator.next();
            if (u.getUserId() == id) {
                u.set(user);
            }
        }
    }
    @Override
    public void addAll(Collection<User> users) {
        this.users.addAll(users);
    }

    //delete
    @Override
    public void clear() {
        users.clear();
    }
    @Override
    public void remove(User user) {
        users.remove(user);
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
    public int generateNewId() {
        return users.last().getUserId() + 1;
    }

}
