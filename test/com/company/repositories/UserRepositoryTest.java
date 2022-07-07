package com.company.repositories;

import com.company.models.User;
import com.company.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class UserRepositoryTest {

    String path = "test/com/company/repositories/users";

    @Test
    public void loadTest() {
        List<User> doctors = new ArrayList<>();

        UserRepository users = new UserRepository(path);

        doctors = users.getUsers();

        for (int i = 0; i < doctors.size(); i++) {
            System.out.println(doctors.get(i));
        }
    }

}