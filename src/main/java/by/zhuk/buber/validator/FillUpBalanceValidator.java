package by.zhuk.buber.validator;

public class FillUpBalanceValidator {
    private static final String MONEY_FORMAT_REGEXP = "[1-9]\\d*\\.\\d{2}";
    private static final String CARD_NUMBER_REGEXP = "\\d{16}";
    private static final int MAX_MONEY_FORMAT_LENGTH = 8;

    public static boolean isMoneyFormatValid(String moneyAmount) {
        return moneyAmount != null && moneyAmount.matches(MONEY_FORMAT_REGEXP);
    }

    public static boolean isMoneyAmountInBoundValid(String moneyAmount) {
        return moneyAmount != null && moneyAmount.length() <= MAX_MONEY_FORMAT_LENGTH;
    }


    public static boolean isCardNumberValid(String cardNumber) {
        return cardNumber != null && cardNumber.matches(CARD_NUMBER_REGEXP);
    }
}