package by.zhuk.buber.validator;

import by.zhuk.buber.userpool.RestorePasswordUserPool;
import by.zhuk.buber.userpool.SignUpUserPool;

/**
 * Class include method to validate Coordinate info
 */
public class CoordinateValidator {
    private static final String COORDINATE_REGEXP = "\\-?\\d{1,3}\\.\\d+";

    public static boolean isCoordinateValid(String coordinate) {
        return coordinate != null && coordinate.matches(COORDINATE_REGEXP);
    }

}