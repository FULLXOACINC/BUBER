package by.zhuk.buber.validator;
/**
 * Class include method to validate driver info
 */
public class DriverValidator {
    private static final String CAR_NUMBER_REGEXP = "\\d{4}\\w{2}\\d";
    private static final String DOC_ID_REGEXP = "\\d\\w{2}\\d{6}";
    private static final String CAR_MARK_REGEXP = "[\\d\\w-']{2,30}";
    private static final String TARIFF_REGEXP = "\\d\\.\\d{2}";

    public static boolean isCarNumberValid(String carNumber) {
        return carNumber != null && carNumber.matches(CAR_NUMBER_REGEXP);
    }

    public static boolean isDocIdValid(String docId) {
        return docId != null && docId.matches(DOC_ID_REGEXP);
    }

    public static boolean isCarMarkValid(String carMark) {
        return carMark != null && carMark.matches(CAR_MARK_REGEXP);
    }

    public static boolean isTariffValid(String tariff) {
        return tariff != null && tariff.matches(TARIFF_REGEXP);
    }
}