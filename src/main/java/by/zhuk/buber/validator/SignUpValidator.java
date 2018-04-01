package by.zhuk.buber.validator;

public class SignUpValidator {
    private static final String PASSWORD_REGEXP = "[\\w\\d!@#$%^&*+_-~]{6,35}";
    private static final String AGE_REGEXP = "\\d+";
    private static final String PHONE_NUMBER_REGEXP = "\\+\\d{10,12}";
    private static final String NAME_REGEXP = "[А-Я][а-я]+|[A-Z]\\w+";

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 255;

    public static boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_REGEXP);
    }

    public static boolean isAgeValid(String userAge) {
        if (!userAge.matches(AGE_REGEXP)) {
            return false;
        }
        int age = Integer.parseInt(userAge);
        return age >= MIN_AGE && age <= MAX_AGE;
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_REGEXP);
    }

    public static boolean isNameValid(String name) {
        return name.matches(NAME_REGEXP);
    }
}