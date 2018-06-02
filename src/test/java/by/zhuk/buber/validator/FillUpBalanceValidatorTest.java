package by.zhuk.buber.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FillUpBalanceValidatorTest {

    @Test
    public void isMoneyFormatValidPositiveTest() {
        boolean result = FillUpBalanceValidator.isMoneyFormatValid("38.00");
        Assert.assertTrue(result);
    }

    @Test
    public void isMoneyAmountInBoundValidPositiveTest() {
        boolean result = FillUpBalanceValidator.isMoneyAmountInBoundValid("123.00");
        Assert.assertTrue(result);
    }

    @Test
    public void isCardNumberValidPositiveTest() {
        boolean result = FillUpBalanceValidator.isCardNumberValid("1234567890123456");
        Assert.assertTrue(result);
    }

    @Test
    public void isMoneyFormatValidNegativeTest() {
        boolean result = FillUpBalanceValidator.isMoneyFormatValid("38,00");
        Assert.assertFalse(result);
    }

    @Test
    public void isMoneyAmountInBoundValidNegativeTest() {
        boolean result = FillUpBalanceValidator.isMoneyAmountInBoundValid("12332123123.00");
        Assert.assertFalse(result);
    }

    @Test
    public void isCardNumberValidNegativeTest() {
        boolean result = FillUpBalanceValidator.isCardNumberValid("12312412");
        Assert.assertFalse(result);
    }
}