package com.example.sorted;

import com.example.contracts.Contract;

import java.util.Comparator;

public class SelectionSort implements ISorted{
    @Override
    public void sort(Comparator<Contract> contractComparator, Contract[] contractArray) {
        selectionSort(contractComparator, contractArray);
    }

    private void selectionSort(Comparator<Contract> contractComparator, Contract[] contractArray) {
        int length = contractArray.length;

        for (int i = 0; i < length; i++){
            int min = i;
            for (int j = i + 1; j < length; j++)
                if (contractComparator.compare(contractArray[min], contractArray[j]) > 0)
                    min = j;

            Contract temp = contractArray[min];
            contractArray[min] = contractArray[i];
            contractArray[i] = temp;
        }
    }
}
