package by.zhuk.buber.validator;

public class AdminValidator {
    private static final String DISCOUNT_REGEXP = "0\\.\\d{1,2}";

    public static boolean isDiscountValid(String discount) {
        return discount != null && discount.matches(DISCOUNT_REGEXP);
    }

}