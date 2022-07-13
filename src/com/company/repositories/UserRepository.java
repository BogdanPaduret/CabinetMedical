package com.company.repositories;

import com.company.helpers.Utils;
import com.company.models.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import static com.company.helpers.Constants.*;
import static com.company.helpers.Utils.*;

public class UserRepository implements Repository<User>{

    //instance variables
    private Hashtable<String, Set> usersHashtable;
    private TreeSet<User> users;
    private String path;

    //constructor
    private void init(String path) {
        this.users = new TreeSet<>();
        this.path = path;
        usersHashtable= createEmptyUserSetsHashtable();
    }
    public UserRepository(String path) {
        init(path);
        this.load();
    }
    public UserRepository(String path, Collection<User> users) {
        init(path);
        this.users.addAll(users);
        splitUsers();
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
        if (Utils.checkUserType(type)) {
            users.add(Utils.createUser(type, generateNewId(), name));
            splitUsers();
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
                User user = Utils.createUser(line);
                if (user != null) {
                    try {
                        this.users.add(user);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }

                    //code pentru a umple hashtable-ul cu utilizatori diferiti
                    sublistUser(user);

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
    public Set getAll(String type) {
        return usersHashtable.get(type);
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

    /*
    Momentan metoda constructorul va popula usersHashtable tot timpul.
    Asta scade efortul de a incarca o lista cu useri dar consuma mai multa memorie.
    Se poate sa introduc metoda splitUsers() in getAll(String type) pentru a elibera memoria
    DAR va avea nevoie de mai mult timp metoda pentru a returna lista.
     */

    private void splitUsers() {
        for (User user : users) {
            sublistUser(user);
        }
    }
    private void sublistUser(User user) {
        for (int i = 0; i < USERS_ARRAY.length; i++) {
            if (user.getClass().getSimpleName().toLowerCase().equals(USERS_ARRAY[i])) {
                if (usersHashtable.containsKey(USERS_ARRAY[i])) {
                    usersHashtable.get(USERS_ARRAY[i]).add(user);
                }
            }
        }

    }


}
