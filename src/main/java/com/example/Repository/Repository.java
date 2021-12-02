package com.example.Repository;

import com.example.contracts.Contract;
import com.example.contracts.DTVContract;
import com.example.contracts.MCContract;
import com.example.contracts.WIContract;
import com.example.sorted.MergeSort;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * The class describes the repository where contracts are stored
 * @author  Alexanrd Spaskin
 */
public class Repository {
    private int size = 0;
    private Contract[] contracts = new Contract[size];

    public int getSize() {
        return size;
    }

    public Contract[] getContracts() {
        Contract[] c = new Contract[size];
        System.arraycopy(contracts, 0, c, 0, contracts.length);
        return c;
    }

    /**
     * method of adding a contract by identifier
     */
    public void add(MCContract mc){
        expansion();
        this.contracts[contracts.length - 1] = mc;
    }

    public void add(DTVContract dt){
        expansion();
        this.contracts[contracts.length - 1] = dt;
    }

    public void add(WIContract wi){
        expansion();
        this.contracts[contracts.length - 1] = wi;
    }

    /**
     * Removes an item from the list, if present. If there is no item in the list, it remains unchanged.
     * @param id - contract id
     * @param expectedClacc - the type of the class in which the element is searched
     * @return true if this list contained the specified element
     */
    public boolean deleteById(int id, Class expectedClacc){
        final int length = contracts.length;
        int pos = -1;
        for (int i = 0; (i < length) && (pos == -1); i++)
            if (contracts[i].getId() == id && contracts[i].getClass().equals(expectedClacc)){
                pos = i;
            }
        if (pos == -1) {
            return false;
        }
        removeElement(pos);
        return true;
    }

    /**
     * Private remove method that skips bounds checking
     */
    private void removeElement(int pos){
        Contract[] contracts = this.contracts;
        Contract[] temp = new Contract[contracts.length - 1];
        final int newSize = size - 1;
        if (newSize >= pos) {
            System.arraycopy(contracts, 0, temp, 0, pos);
            System.arraycopy(contracts, pos + 1, temp, pos, newSize - pos);
        }
       this.contracts = temp;
       this.size = newSize;

    }

    /**
     * Method of obtaining contacts by id
     * @param id - contract id
     * @param expectedClacc - the type of the class in which the element is searched
     * @return array element if it exists
     */
    public Contract getById(int id, Class expectedClacc){
        final Contract[] contracts = this.contracts;
        for (int i = 0; i < contracts.length; i++){
            if (contracts[i].getId() == id && contracts[i].getClass().equals(expectedClacc))
                return contracts[i];
        }
        return null;
    }

    /**
     * method to expand the original array when it overflows
     * new array size equal old size + 1
     */
    private void expansion(){
        this.size += 1;
        if(size > this.contracts.length){
            Contract[] temp = new Contract[size];
            System.arraycopy(this.contracts, 0, temp, 0, this.contracts.length);
            this.contracts = temp;
        }
    }

    /**
     * method of search by predicate
     * @param pred
     * @return Contract if found, otherwise return empty
     */
    public Optional<Contract> search(Predicate<Contract> pred){
        for (Contract i : this.contracts){
            if (pred.test(i)){
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public void sort(Comparator<Contract> comp){
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(comp, contracts);
    }
}
