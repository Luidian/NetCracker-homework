package com.example;

import com.example.contracts.DTVContract;
import com.example.contracts.MCContract;
import com.example.contracts.WIContract;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    Human h1 = new Human(0, "fn", "n", "mn", LocalDate.of(1234, 5, 6), "m");
    WIContract wi1 = new WIContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 101392, h1,100);
    WIContract wi2 = new WIContract(2, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 101392, h1,100);
    WIContract wi3 = new WIContract(3, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 101392, h1,100);
    DTVContract dtv = new DTVContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 101392, h1, "all");
    MCContract mc = new MCContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 101392, h1, 200, 500, 5000);

    @Test
    void add() {
        Repository r = new Repository();

        int expected = r.getSize() + 1;
        r.add(wi1);
        int actual = r.getSize();
        assertEquals(expected, actual);

        int expected1 = r.getSize() + 1;
        r.add(dtv);
        int actual1 = r.getSize();
        assertEquals(expected1, actual1);

        int expected2 = r.getSize() + 1;
        r.add(mc);
        int actual2 = r.getSize();
        assertEquals(expected2, actual2);
    }

    @Test
    void deleteById() {
        Repository r = new Repository();
        r.add(wi1); r.add(wi2); r.add(mc);

        boolean actual = r.deleteById(2, wi2.getClass());
        assertTrue(actual);

        boolean actual1 = r.deleteById(3, wi3.getClass());
        assertFalse(actual1);

        boolean actual2 = r.deleteById(1, mc.getClass());
        assertTrue(actual2);
    }

    @Test
    void getById() {
        Repository r = new Repository();
        r.add(wi1); r.add(wi2); r.add(mc);

        WIContract actual = (WIContract) r.getById(1, wi1.getClass());
        WIContract expected = wi1;
        assertEquals(expected, actual);

        MCContract actualMC = (MCContract) r.getById( 2, mc.getClass());
        assertNull(actualMC);
    }
}
