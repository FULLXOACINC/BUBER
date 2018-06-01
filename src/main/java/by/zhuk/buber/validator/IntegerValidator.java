package by.zhuk.buber.validator;

public class IntegerValidator {
    private static final String INTEGER_REGEX = "\\d+";

    public static boolean isInteger(String integer) {
        return integer != null && integer.matches(INTEGER_REGEX);
    }
}