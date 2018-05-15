package by.zhuk.buber.validator;

public class FillUpBalanceValidator {
    private static final String MONEY_FORMAT_REGEXP = "[1-9]\\d*\\.\\d{2}";
    private static final int MAX_MONEY_FORMAT_LENGTH = 8;

    public static boolean isMoneyFormatValid(String moneyAmount) {
        return moneyAmount.matches(MONEY_FORMAT_REGEXP);
    }

    public static boolean isMoneyAmountInBoundValid(String moneyAmount) {
        return moneyAmount.length() <= MAX_MONEY_FORMAT_LENGTH;
    }


}