package com.company.models;

public abstract class User implements Comparable<User> {

    //instance variables
    private int userId;
    private String name;

    //constructor
    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    //read
    public int getUserId() {
        return userId;
    }
    public void setName(String name) {
        this.name = name;
    }

    //update
    public String getName() {
        return name;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    //implemented methods
    @Override
    public boolean equals(Object o) {
        try {
            User user = (User) o;
            return this.getUserId() == user.getUserId() &&
                    this.getName().equals(user.getName());
        } catch (ClassCastException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public int compareTo(User o){
        User user = (User) o;

        Integer thisId = this.getUserId();
        Integer userId = user.getUserId();

        return thisId.compareTo(userId);
    }

    //abstract methods
    @Override
    public abstract String toString();

}
