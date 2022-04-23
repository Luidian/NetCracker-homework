package com.example.contracts;

import com.example.human.Human;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The class describes the mobile contract
 * The class is inherited from the Contract class and has new fields "minutes", "sms" and "traffic"
 * @author  Alexanrd Spaskin
 */
@XmlRootElement(name = "MCContract")
@XmlType(propOrder = {
        "minutes",
        "sms",
        "traffic"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class MCContract extends Contract{

    @XmlElement
    private int minutes;

    @XmlElement
    private int sms;

    @XmlElement
    private int traffic;

    public MCContract(){}

    public MCContract(int id, LocalDate startDate, LocalDate endDate, int contractNumber, Human contractOwner, int minutes, int sms, int traffic){
        super(id, startDate, endDate, contractNumber, contractOwner);
        this.minutes = minutes;
        this.sms = sms;
        this.traffic = traffic;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSms() {
        return sms;
    }

    public int getTraffic() {
        return traffic;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCContract that = (MCContract) o;
        return id == that.id &&
                contractNumber == that.contractNumber &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(contractOwner, that.contractOwner) &&
                minutes == that.minutes &&
                sms == that.sms &&
                traffic == that.traffic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutes, sms, traffic);
    }

    @Override
    public String toString() {
        return "MCContract{" +
                super.toString() +
                ", minutes=" + minutes +
                ", sms=" + sms +
                ", traffic=" + traffic +
                '}';
    }
}
