package by.zhuk.buber.validator;

import org.testng.Assert;
import org.testng.annotations.Test;


public class UserValidatorTest {

    @Test
    public void isDiscountValidPositiveTest() {
        boolean result = UserValidator.isDiscountValid("0.34");
        Assert.assertTrue(result);
    }

    @Test
    public void isLoginValidPositiveTest() {
        boolean result = UserValidator.isLoginValid("san91130324@gmailw.com");
        Assert.assertTrue(result);
    }

    @Test
    public void isPasswordValidPositiveTest() {
        boolean result = UserValidator.isPasswordValid("1130324");
        Assert.assertTrue(result);
    }

    @Test
    public void isBirthDayValidPositiveTest() {
        boolean result = UserValidator.isBirthDayValid("1998-01-01");
        Assert.assertTrue(result);
    }

    @Test
    public void isPhoneNumberValidPositiveTest() {
        boolean result = UserValidator.isPhoneNumberValid("+375291713221");
        Assert.assertTrue(result);
    }

    @Test
    public void isNameValidPositiveTest() {
        boolean result = UserValidator.isNameValid("Саша");
        Assert.assertTrue(result);
    }

    @Test
    public void isDiscountValidNegativeTest() {
        boolean result = UserValidator.isDiscountValid("1.00");
        Assert.assertFalse(result);
    }

    @Test
    public void isLoginValidNegativeTest() {
        boolean result = UserValidator.isLoginValid("ыфьщзьвыфд@");
        Assert.assertFalse(result);
    }

    @Test
    public void isPasswordValidNegativeTest() {
        boolean result = UserValidator.isPasswordValid("32");
        Assert.assertFalse(result);
    }

    @Test
    public void isBirthDayValidBadFormatNegativeTest() {
        boolean result = UserValidator.isBirthDayValid("23-09-12");
        Assert.assertFalse(result);
    }

    @Test
    public void isPhoneNumberValidNegativeTest() {
        boolean result = UserValidator.isPhoneNumberValid("2008-01-01");
        Assert.assertFalse(result);
    }

    @Test
    public void isNameValidNegativeTest() {
        boolean result = UserValidator.isNameValid("38.0031");
        Assert.assertFalse(result);
    }
}