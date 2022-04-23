package com.example.human;

import com.example.jaxb.LocalDateXMLAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * The class describes information about a person
 * @author  Alexanrd Spaskin
 */
@XmlRootElement(name = "contractOwner")
@XmlType(propOrder = {"id","firstName", "name", "middleName", "dateOfBirthday", "gender", "passportSeries", "passportID"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Human {

    @XmlElement
    private int id;

    @XmlElement
    private String firstName;

    @XmlElement
    private String name;

    @XmlElement
    private String middleName;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateXMLAdapter.class)
    private LocalDate dateOfBirthday;

    @XmlElement
    private String gender;

    @XmlElement
    private int passportSeries;

    @XmlElement
    private int passportID;

    public Human() {
    }

    public Human(int id, String firstName, String name, String middleName, String gender, LocalDate dateOfBirthday, int passportSeries, int passportID){
        this.id = id;
        this.firstName = firstName;
        this.name = name;
        this.middleName = middleName;
        this.dateOfBirthday = dateOfBirthday;
        this.gender = gender;
        this.passportSeries = passportSeries;
        this.passportID = passportID;
    }

    /**
     * Age calculation method
     * @return calculated age or null
     */
    public int calculateAge(){
        LocalDate currentDate = LocalDate.now();
        if (this.dateOfBirthday != null){
            return Period.between(this.dateOfBirthday, currentDate).getYears();
        }
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public LocalDate getDateOfBirthday() {
        return dateOfBirthday;
    }

    public String getGender() {
        return gender;
    }

    public int getPassportSeries() {
        return passportSeries;
    }

    public int getPassportID() {
        return passportID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setDateOfBirthday(LocalDate dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassportSeries(int passportSeries) {
        this.passportSeries = passportSeries;
    }

    public void setPassportID(int passportID) {
        this.passportID = passportID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return id == human.id && passportSeries == human.passportSeries && passportID == human.passportID && Objects.equals(firstName, human.firstName) && Objects.equals(name, human.name) && Objects.equals(middleName, human.middleName) && Objects.equals(dateOfBirthday, human.dateOfBirthday) && Objects.equals(gender, human.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, name, middleName, dateOfBirthday, gender, passportSeries, passportID);
    }

    @Override
    public String toString() {
        return "Human{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", dateOfBirthday=" + dateOfBirthday +
                ", gender='" + gender + '\'' +
                ", passportSeries=" + passportSeries +
                ", passportID=" + passportID +
                '}';
    }
}
