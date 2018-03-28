package by.zhuk.buber.model;

import java.math.BigDecimal;
import java.util.Objects;

public class User {
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private int age;
    private boolean isBaned;
    private String phoneNumber;
    private BigDecimal balance;
    private UserType type;

    public User() {
    }

    public User(String login, String firstName, String lastName, String password, int age, boolean isBaned, String phoneNumber, BigDecimal balance, UserType type) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.isBaned = isBaned;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.type = type;
    }

    public String getLogin() {
        return login;
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

    public void setLogin(String login) {
        this.login = login;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                Objects.equals(login, user.login) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(password, user.password) &&
                type == user.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, firstName, lastName, age, isBaned, phoneNumber, balance, type, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", isBaned=" + isBaned +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                ", type=" + type +
                '}';
    }
}