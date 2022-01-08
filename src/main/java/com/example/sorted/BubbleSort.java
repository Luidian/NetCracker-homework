package com.example.sorted;

import com.example.contracts.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class BubbleSort implements ISorted{
    @Override
    public void sort(Comparator<Contract> contractComparator, Contract[] contractArray) {
        bubbleSort(contractComparator, contractArray);
    }

    private void bubbleSort(Comparator<Contract> contractComparator, Contract[] contractArray) {
        int length = contractArray.length;
        Contract temp;

        for (int i = 0; i < length; i++){
            for (int j = 1; j < (length - i); j++){
                if (contractComparator.compare(contractArray[j - 1], contractArray[j]) > 0) {
                        temp = contractArray[j - 1];
                        contractArray[j - 1] = contractArray[j];
                        contractArray[j] = temp;
                }
            }
        }
    }
}
