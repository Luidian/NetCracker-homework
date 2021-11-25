package com.example;

import com.example.contracts.Contract;
import com.example.contracts.DTVContract;
import com.example.contracts.MCContract;
import com.example.contracts.WIContract;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    Human h1 = new Human(0, "fn", "n", "mn", LocalDate.of(1234, 5, 6), "m");
    Human h2 = new Human(1, "fn1", "n1", "mn1", LocalDate.of(1235, 6, 7), "m");
    WIContract wi1 = new WIContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 5, h1,100);
    WIContract wi2 = new WIContract(2, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 55, h2,80);
    WIContract wi4 = new WIContract(2, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 555, h2,50);
    WIContract wi3 = new WIContract(3, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 5555, h1,20);
    DTVContract dtv = new DTVContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 111, h2, "all");
    MCContract mc = new MCContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 222, h1, 200, 500, 5000);

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
    @Test
    void search() {
        Repository r = new Repository();
        r.add(wi1); r.add(wi2); r.add(wi3); r.add(wi4); r.add(dtv);
        Optional<Contract> actual = r.search(i -> i.getContractOwner() == h2);
        WIContract expected = wi2;
        assertEquals(expected, actual.get());

        Optional<Contract> actual1 = r.search(i -> i.getContractNumber() == 111);
        DTVContract expected1 = dtv;
        assertEquals(expected1, actual1.get());

        Optional<Contract> actual3 = r.search(i -> i.getId() == 55);
        Optional<Contract> expected3 = Optional.empty();
        assertEquals(expected3, actual3);

    }
}
