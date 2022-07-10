package com.company.views;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewLogInTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void play() {

        String rootPath = "test/com/company/repositories";
        ViewLogIn logIn = new ViewLogIn(rootPath);

        logIn.play("0\ny\nn");
        logIn.play("1\n");

    }
}