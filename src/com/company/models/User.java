package com.company.models;

import static com.company.helpers.Constants.SAVE_SEPARATOR;

public abstract class User implements Comparable<User> {

    //instance variables
    private String type;
    private int userId;
    private String userName;

    //constructor
    public User(String type, int userId, String userName) {
        this.type = type;
        this.userId = userId;
        this.userName = userName;
    }

    //read
    public String saveString() {
        String string = "";

        string += getType() + SAVE_SEPARATOR + getUserId() + SAVE_SEPARATOR + getUserName();

        return string;
    }
    public String getType() {
        return type;
    }
    public int getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }

    //update
    public void setType(String type) {
        this.type = type;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void set(User user) {
        this.type = user.type;
        this.userId = user.userId;
        this.userName = user.userName;
    }

    //implemented methods
    @Override
    public boolean equals(Object o) {
        return (o instanceof User user) &&
                this.getType().equals(user.getType()) &&
                this.getUserId() == user.getUserId() &&
                this.getUserName().equals(user.getUserName());
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

        string += "User type: " + getType();
        string += "\nUser ID: " + getUserId();
        string += "\nUser Name: " + getUserName();

        return string;
    }

}
