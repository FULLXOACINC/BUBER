package by.zhuk.buber.model;

import java.math.BigDecimal;
import java.util.Objects;

public class User {
    private String firstName;
    private String lastName;
    private int age;
    private boolean isBaned;
    private String phoneNumber;
    private BigDecimal balance;
    private UserType type;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public boolean isBaned() {
        return isBaned;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UserType getType() {
        return type;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBaned(boolean baned) {
        isBaned = baned;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return age == user.age &&
                isBaned == user.isBaned &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(balance, user.balance) &&
                type == user.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, isBaned, phoneNumber, balance, type);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", isBaned=" + isBaned +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                ", type=" + type +
                '}';
    }
}