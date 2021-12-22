package com.example.contracts;

import com.example.human.Human;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class describes the contract of the television
 * The class inherits from the Contract class and has a new field "channel package"
 * @author  Alexanrd Spaskin
 */
public class DTVContract extends Contract{
    private String channelPackage;

    public DTVContract(int id, LocalDate startDate, LocalDate endDate, int contractNumber, Human contractOwner, String channelPackage){
        super(id, startDate, endDate, contractNumber, contractOwner);
        this.channelPackage = channelPackage;
    }

    public String getChannelPackage() {
        return channelPackage;
    }

    public void setChannelPackage(String channelPackage) {
        this.channelPackage = channelPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTVContract that = (DTVContract) o;
        return id == that.id &&
                contractNumber == that.contractNumber &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(contractOwner, that.contractOwner) &&
                Objects.equals(channelPackage, that.channelPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelPackage);
    }

    @Override
    public String toString() {
        return "DTVContract{" +
                super.toString() +
                ", channelPackage='" + channelPackage + '\'' +
                '}';
    }
}
