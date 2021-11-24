package com.example.sorted;

import com.example.contracts.Contract;

import java.util.Comparator;
/**
 * @author  Alexanrd Spaskin
 */
public interface ISorted {
    void sort(Comparator<Contract> contractComparator, Contract[] contractArray);

}
