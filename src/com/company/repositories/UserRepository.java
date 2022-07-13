package com.company.repositories;

import com.company.exceptions.NoUserTypeException;
import com.company.helpers.Utils;
import com.company.models.*;
import com.company.views.Observer;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import static com.company.helpers.Constants.*;
import static com.company.helpers.Utils.*;

public class UserRepository implements Repository<User>{

    //instance variables
    private TreeSet<User> users;
    private String path;

    private ArrayList<Observer> observers;

    //constructor
    private void init(String path) {
        this.users = new TreeSet<>();
        this.path = path;
        observers = new ArrayList<>();
    }
    public UserRepository(String path) {
        init(path);
    }
    public UserRepository(String path, Collection<User> users) {
        init(path);
        this.users.addAll(users);
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
    public void add(User user)
            throws NoUserTypeException {
        add(user.getType(), user.getUserName());
    }
    public void add(String type, String name)
            throws NoUserTypeException {
        if (Utils.typeExists(type)) {
            users.add(Utils.createUser(type, generateNewId(), name));
            notifyObservers();
        } else {
            throw new NoUserTypeException();
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
                User user = Utils.createUser(line);
                if (user != null) {
                    try {
                        this.users.add(user);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
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
    public User get(int id)
            throws NoSuchElementException {
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
    public Set<User> getAll(String type)
            throws NoUserTypeException {
        if (typeExists(type)) {
            Hashtable<String, Set<User>> usersHashtable = createEmptyUserSetsHashtable();
            splitUsers(usersHashtable);
            return usersHashtable.get(type);
        } else {
            throw new NoUserTypeException();
        }
    }

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
    public void set(int id, User user)
            throws NoSuchElementException {
        Iterator<User> iterator = users.iterator();
        boolean updated = false;
        while (iterator.hasNext()) {
            User u = iterator.next();
            if (u.getUserId() == id) {
                u.set(user);
                updated = true;
            }
        }
        if (!updated) {
            throw new NoSuchElementException();
        } else {
            notifyObservers();
        }
    }
    @Override
    public void addAll(Collection<User> users) {
        this.users.addAll(users);
        notifyObservers();
    }

    //delete
    @Override
    public void clear() {
        users.clear();
        notifyObservers();
    }
    @Override
    public void remove(User user) {
        users.remove(user);
        notifyObservers();
    }

    //observer pattern
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(this);
        }
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
        try {
            return users.last().getUserId() + 1;
        } catch (NoSuchElementException e) {
            return 0;
        }
    }


    private void splitUsers(Hashtable<String, Set<User>> usersHashtable)
            throws NoUserTypeException {
        for (User user : users) {
            sublistUser(usersHashtable, user);
        }
    }

    private void sublistUser(Hashtable<String, Set<User>> usersHashtable, User user)
            throws NoUserTypeException {
        boolean hit = false;
        for (int i = 0; i < USERS_ARRAY.length; i++) {
            if (user.getClass().getSimpleName().toLowerCase().equals(USERS_ARRAY[i])) {
                if (usersHashtable.containsKey(USERS_ARRAY[i])) {
                    usersHashtable.get(USERS_ARRAY[i]).add(user);
                    hit = true;
                }
            }
        }
        if (!hit) {
            throw new NoUserTypeException();
        }

    }

}
