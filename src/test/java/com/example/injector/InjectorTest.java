package com.example.injector;

import com.example.repository.Repository;
import com.example.sorted.MergeSort;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InjectorTest {

    @Test
    void inject() {
        Repository repository = (new Injector()).inject(new Repository());

        String expected = null;
        try {
            expected = FieldUtils.readField(repository, "sorted", true).getClass().getName();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println(expected);
        String actual = (new MergeSort()).getClass().getName();

        assertEquals(expected, actual);

    }
}