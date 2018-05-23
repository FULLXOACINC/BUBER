package by.zhuk.buber.validator;

public class CoordinateValidator {
    private static final String COORDINATE_REGEXP = "\\-?\\d{1,3}\\.\\d+";

    public static boolean isCoordinateValid(String coordinate) {
        return coordinate.matches(COORDINATE_REGEXP);
    }

}