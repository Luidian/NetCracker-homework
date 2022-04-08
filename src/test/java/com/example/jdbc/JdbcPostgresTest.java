package com.example.jdbc;

import com.example.contracts.DTVContract;
import com.example.contracts.MCContract;
import com.example.contracts.WIContract;
import com.example.human.Human;
import com.example.repository.Repository;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JdbcPostgresTest {

    Human h1 = new Human(1, "fn1", "n1", "mn1","m", LocalDate.of(1234, 5, 6),  1234, 1234567890);
    Human h2 = new Human(2, "fn2", "n2", "mn2", "m", LocalDate.of(1235, 6, 7),  1234, 123456789);
    Human h3 = new Human(3, "fn3", "n3", "mn3","m", LocalDate.of(1234, 5, 6),  1234, 1234567890);
    Human h4 = new Human(4, "fn4", "n4", "mn4", "m", LocalDate.of(1235, 6, 7),  1234, 123456789);

    WIContract wi1 = new WIContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 4, h1,100);
    WIContract wi2 = new WIContract(2, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 3, h2,80);
    DTVContract dtv = new DTVContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 1, h3, "all");
    MCContract mc = new MCContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 6, h4, 200, 500, 5000);

    Repository repository = new Repository();

    @Test
    void connection() {
        try(Connection connection = new JdbcPostgres().connection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveRepository() {
        JdbcPostgres postgres = new JdbcPostgres();
        Repository repository = new Repository();
        repository.add(wi1); repository.add(wi2); repository.add(dtv); repository.add(mc);

        boolean save = postgres.saveRepository(repository.getContracts());

        assertTrue(true);
    }

    @Test
    void recoveryRepository() {
        JdbcPostgres postgres = new JdbcPostgres();
        postgres.clearDB();

        Repository newRepository = new Repository();
        newRepository.recoveryInDatabase();

        int repositorySise = repository.getContracts().length;
        int newRepositorySise = newRepository.getContracts().length;

        assertEquals(repositorySise, newRepositorySise);
    }
}