package com.company.models;

import static com.company.helpers.Constants.USER_SECRETARY;

public class Secretary extends User {

    public Secretary(int userId, String userName) {
        super(USER_SECRETARY, userId, userName);
    }

    @Override
    public String toString() {
        String string = "";

        string += "Secretay ID: " + this.getUserId();
        string += "\nSecretary name: " + this.getUserName();

        return string;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Secretary secretary) &&
                super.equals(o);
    }

}
