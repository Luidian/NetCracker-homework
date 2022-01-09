package com.example.sorted;

import com.example.annotations.Default;
import com.example.contracts.Contract;

import java.util.Comparator;
/**
 * @author  Alexanrd Spaskin
 */
@Default
public class MergeSort implements ISorted {
    @Override
    public void sort(Comparator<Contract> contractComparator, Contract[] contractArray){
        mergeSort(contractComparator, contractArray, contractArray.length);
    }

    /**
     * method of splitting an array
     * @param contractComparator - Comparator for comparison
     * @param contracts - input array
     * @param length - length contracts
     */
    private void mergeSort(Comparator<Contract> contractComparator, Contract[] contracts, int length){
        if (length < 2){ return; }
        int  middle = length / 2;

        Contract[] l = new Contract[middle];
        Contract[] r = new Contract[length-middle];

        System.arraycopy(contracts, 0, l, 0, middle);

        if (length - middle >= 0) System.arraycopy(contracts, middle, r, middle - middle, length - middle);

        mergeSort(contractComparator, l, middle);
        mergeSort(contractComparator, r, length - middle);

        merge(contractComparator, contracts, l, r, middle, length - middle);
    }

    /**
     * array merge method
     * @param contractComparator - Comparator for comparison
     * @param contracts - input array
     * @param l - left array
     * @param r - right array
     * @param left -  end of left array
     * @param right - start of right array
     */
    private void merge(Comparator<Contract> contractComparator, Contract[] contracts, Contract[] l, Contract[] r, int left, int right){
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left && j < right) {
            if (contractComparator.compare(l[i], r[j]) < 0) {
                contracts[k++] = l[i++];
            } else {
                contracts[k++] = r[j++];
            }
        }
        while(i < left){
            contracts[k++] = l[i++];
        }
        while(j < right){
            contracts[k++] = r[j++];
        }
    }
}
