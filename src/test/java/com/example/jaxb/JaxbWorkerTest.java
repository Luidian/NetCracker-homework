package com.example.jaxb;

import com.example.contracts.Contract;
import com.example.contracts.DTVContract;
import com.example.contracts.MCContract;
import com.example.contracts.WIContract;
import com.example.human.Human;
import com.example.repository.Repository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JaxbWorkerTest {

    Human h1 = new Human(1, "fn1", "n1", "mn1","m", LocalDate.of(1234, 5, 6),  1234, 1234567890);
    Human h2 = new Human(2, "fn2", "n2", "mn2", "m", LocalDate.of(1235, 6, 7),  1234, 123456789);
    Human h3 = new Human(3, "fn3", "n3", "mn3","m", LocalDate.of(1234, 5, 6),  1234, 1234567890);
    Human h4 = new Human(4, "fn4", "n4", "mn4", "m", LocalDate.of(1235, 6, 7),  1234, 123456789);

    WIContract wi1 = new WIContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 4, h1,100);
    WIContract wi2 = new WIContract(2, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 3, h2,80);
    DTVContract dtv = new DTVContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 1, h3, "all");
    MCContract mc = new MCContract(1, LocalDate.of(1234, 5, 6), LocalDate.of(1234, 6, 6), 6, h4, 200, 500, 5000);

    Repository repository = new Repository();
    Repository repository1 = new Repository();
    @Test
    void saveRepository() {
        repository.add(wi1); repository.add(wi2); repository.add(dtv); repository.add(mc);

        JaxbWorker jaxbWorker = new JaxbWorker();
        boolean save = jaxbWorker.saveRepository(repository);

        assertTrue(save);
    }

    @Test
    void recoveryRepository() {
        repository.add(wi1); repository.add(wi2); repository.add(dtv); repository.add(mc);

        JaxbWorker jaxbWorker = new JaxbWorker();
        repository1 = jaxbWorker.recoveryRepository();

        assertEquals(repository.getSize(), repository1.getSize());

        for (int i = 0; i < repository.getContracts().length; i++){
            assertEquals(repository.getContracts()[i], repository.getContracts()[i]);
        }
    }
}