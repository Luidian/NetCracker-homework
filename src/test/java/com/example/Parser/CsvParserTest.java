package com.example.Parser;

import com.example.Repository.Repository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvParserTest {
    @Test
    void parse() throws CsvParseException {
        String path = "src/test/resources/data.csv";
        Repository repository = new Repository();
        CsvParser parser = new CsvParser();

        int expected = 5;
        parser.parse(path, repository);
        int actual = repository.getSize();
        assertEquals(expected, actual);

        String path1 = "src/test/resources/emptyFile.csv";
        Repository repository1 = new Repository();
        CsvParser parser1 = new CsvParser();

        int expected1 = 0;
        parser.parse(path1, repository);
        int actual1 = repository1.getSize();
        assertEquals(expected1, actual1);
    }
}