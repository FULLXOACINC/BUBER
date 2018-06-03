package by.zhuk.buber.validator;
/**
 * Class include method to validate unsigned integer
 */
public class IntegerValidator {
    private static final String INTEGER_REGEX = "\\d+";

    public static boolean isUnsignedInteger(String integer) {
        return integer != null && integer.matches(INTEGER_REGEX);
    }
}