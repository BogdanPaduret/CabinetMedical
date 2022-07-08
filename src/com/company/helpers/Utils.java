package com.company.helpers;

import com.company.repositories.UserRepository;

import static com.company.helpers.Constants.USER_DOCTOR;
import static com.company.helpers.Constants.USER_PATIENT;

public abstract class Utils {


    public static final UserRepository userRepository = new UserRepository("");

    public static final boolean checkType(String type) {
        String[] types = {USER_DOCTOR, USER_PATIENT};
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(type)) {
                return true;
            }
        }
        return false;
    }
}
