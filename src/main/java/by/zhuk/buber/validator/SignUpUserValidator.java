package by.zhuk.buber.validator;

import java.time.LocalDate;
import java.time.Period;

public class SignUpUserValidator {
    private static final String PASSWORD_REGEXP = "[\\w\\d!@#$%^&*+_-~]{6,35}";
    private static final String DAY_REGEXP = "\\d{4}(-\\d{2}){2}";
    private static final String PHONE_NUMBER_REGEXP = "\\+\\d{10,12}";
    private static final String NAME_REGEXP = "[А-Я][а-я]+|[A-Z]\\w+";

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 255;

    public static boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_REGEXP);
    }

    public static boolean isBirthDayValid(String birthDay) {
        if (birthDay != null && birthDay.matches(DAY_REGEXP)) {
            LocalDate birthDayDate = LocalDate.parse(birthDay);
            int age = Period.between(birthDayDate, LocalDate.now()).getYears();
            return age >= MIN_AGE && age <= MAX_AGE;
        } else {
            return false;
        }

    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_NUMBER_REGEXP);
    }

    public static boolean isNameValid(String name) {
        return name != null && name.matches(NAME_REGEXP);
    }
}