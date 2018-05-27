package by.zhuk.buber.validator;

public class DistanceValidator {
    private static final int MAX_DISTANCE = 200000;
    private static final int MIN_DISTANCE = 2000;

    public static boolean isDistanceInRange(int distance) {
        return distance <= MAX_DISTANCE && distance >= MIN_DISTANCE;
    }
}