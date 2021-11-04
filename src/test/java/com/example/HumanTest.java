package com.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest {

    @Test
    void calculateAge() {
        Human human = new Human(0, "Fn", "N", "Mn", LocalDate.of(1234, 5, 6), "m");
        Human human2 = new Human(0, "Fn", "N", "Mn", LocalDate.of(2015, 1, 25), "m");
        Human human3 = new Human(0, "Fn", "N", "Mn", LocalDate.of(2021, 10, 26), "m");

        int actual   = human.calculateAge();
        int actual2 = human2.calculateAge();
        int actual3 = human3.calculateAge();

        int expected = 787;
        int expected2 = 6;
        int expected3 = 0;

        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
    }
}
