package com.example.Human;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Human {
    private int id;
    private String firstName;
    private String name;
    private String middleName;
    private LocalDate dateOfBirthday;
    private String gender;

    public Human(int id, String firstName, String name, String middleName, LocalDate dateOfBirthday, String gender){
        this.id = id;
        this.firstName = firstName;
        this.name = name;
        this.middleName = middleName;
        this.dateOfBirthday = dateOfBirthday;
        this.gender = gender;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return id == human.id && firstName.equals(human.firstName) && name.equals(human.name) && middleName.equals(human.middleName) && dateOfBirthday.equals(human.dateOfBirthday) && gender.equals(human.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, name, middleName, dateOfBirthday, gender);
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
                '}';
    }
}
