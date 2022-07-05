package com.company.models;

import static com.company.helpers.Constants.SAVE_SEPARATOR;

public abstract class User implements Comparable<User> {

    //instance variables
    private String type;
    private int userId;
    private String name;

    //constructor
    public User(String type, int userId, String name) {
        this.type = type;
        this.userId = userId;
        this.name = name;
    }

    //read
    public String getType() {
        return type;
    }
    public int getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }

    //update
    public void setType(String type) {
        this.type = type;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setName(String name) {
        this.name = name;
    }

    //implemented methods
    @Override
    public boolean equals(Object o) {
        return (o instanceof User user) &&
                this.getType().equals(user.getType()) &&
                this.getUserId() == user.getUserId() &&
                this.getName().equals(user.getName());
    }
    @Override
    public int compareTo(User o){
        User user = (User) o;

        Integer thisId = this.getUserId();
        Integer userId = user.getUserId();

        return thisId.compareTo(userId);
    }
    @Override
    public String toString() {
        String string = "";

        string += getType() + SAVE_SEPARATOR + getUserId() + SAVE_SEPARATOR + getName();

        return string;
    }

}
