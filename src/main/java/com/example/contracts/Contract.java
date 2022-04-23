package com.example.contracts;

import com.example.human.Human;
import com.example.jaxb.LocalDateXMLAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The class describes the person who owns the contract
 * @author  Alexanrd Spaskin
 */
@XmlRootElement(name = "Contract")
@XmlType(propOrder = {
        "id",
        "startDate",
        "endDate",
        "contractNumber",
        "contractOwner"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({DTVContract.class, MCContract.class, WIContract.class})
public abstract class Contract {

    @XmlElement
    protected int id;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateXMLAdapter.class)
    protected LocalDate startDate;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateXMLAdapter.class)
    protected LocalDate endDate;

    @XmlElement
    protected int contractNumber;

    @XmlElement
    protected Human contractOwner;

    protected Contract() {}

    protected Contract(int id, LocalDate startDate, LocalDate endDate, int contractNumber, Human contractOwner) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractNumber = contractNumber;
        this.contractOwner = contractOwner;
    }

    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getContractNumber() {
        return contractNumber;
    }

    public Human getContractOwner() {
        return contractOwner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setContractNumber(int contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setContractOwner(Human contractOwner) {
        this.contractOwner = contractOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id == contract.id && contractNumber == contract.contractNumber && Objects.equals(startDate, contract.startDate) && Objects.equals(endDate, contract.endDate) && Objects.equals(contractOwner, contract.contractOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, contractNumber, contractOwner);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", contractNumber=" + contractNumber +
                ", contractOwner=" + contractOwner +
                '}';
    }
}
