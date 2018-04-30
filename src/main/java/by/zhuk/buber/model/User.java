package by.zhuk.buber.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class User {
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private LocalDate birthDay;
    private boolean isBaned;
    private String phoneNumber;
    private BigDecimal balance;
    private UserType type;
    private float discount;


    public User() {
    }

    public User(String login, String firstName, String lastName, String password, LocalDate birthDay, boolean isBaned, String phoneNumber, BigDecimal balance, UserType type, float discount) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.birthDay = birthDay;
        this.isBaned = isBaned;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.type = type;
        this.discount = discount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isBaned() {
        return isBaned;
    }

    public void setBaned(boolean baned) {
        isBaned = baned;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
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
        return isBaned == user.isBaned &&
                Float.compare(user.discount, discount) == 0 &&
                Objects.equals(login, user.login) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(birthDay, user.birthDay) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(balance, user.balance) &&
                type == user.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, firstName, lastName, password, birthDay, isBaned, phoneNumber, balance, type, discount);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", isBaned=" + isBaned +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                ", type=" + type +
                ", discount=" + discount +
                '}';
    }
}