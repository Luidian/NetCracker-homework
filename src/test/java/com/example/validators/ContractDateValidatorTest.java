package com.example.validators;

import com.example.contracts.WIContract;
import com.example.human.Human;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContractDateValidatorTest {

    @Test
    void validation() {
        Human human = new Human(0, "Fn", "N", "Mn","m",  LocalDate.of(2020, 5, 6), 1234, 123456780);
        WIContract wi1 = new WIContract(1, LocalDate.of(2015, 1, 6), LocalDate.of(2015, 2, 5), 987654321, human,100);
        WIContract wi2 = new WIContract(1, LocalDate.of(2015, 10, 6), LocalDate.of(2015, 2, 5), 987654321, human,100);

        HumanAgeValidator validator = new HumanAgeValidator();

        String actual = validator.validation(wi1).getStatus();
        String expected = "Ok";
        assertEquals(actual, expected);

        String actual1 = validator.validation(wi2).getStatus();
        String expected1 = "Error";
        assertEquals(actual1, expected1);
    }
}