package com.example.contracts;

import com.example.human.Human;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The class describes the contract of the wired Internet
 * The class is inherited from the Contract class and has new field speed Internet
 * @author  Alexanrd Spaskin
 */
@XmlRootElement(name = "WIContract")
@XmlType(propOrder = {
        "speed"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class WIContract extends Contract{

    @XmlElement
    private int speed;

    public WIContract() {}

    public WIContract(int id, LocalDate startDate, LocalDate endDate, int contractNumber, Human contractOwner, int speed){
        super(id, startDate, endDate, contractNumber, contractOwner);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WIContract that = (WIContract) o;

        return id == that.id &&
                contractNumber == that.contractNumber &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(contractOwner, that.contractOwner) &&
                speed == that.speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(speed);
    }

    @Override
    public String toString() {
        return  "WIContract{" +
                super.toString() +
                ", speed=" + speed +
                '}';
    }
}
