package by.zhuk.buber.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DriverValidatorTest {

    @Test
    public void isCarNumberValidPositiveTest() {
        boolean result = DriverValidator.isCarNumberValid("7511AT7");
        Assert.assertTrue(result);
    }

    @Test
    public void isDocIdValidPositiveTest() {
        boolean result = DriverValidator.isDocIdValid("7AB203315");
        Assert.assertTrue(result);
    }

    @Test
    public void isCarMarkValidPositiveTest() {
        boolean result = DriverValidator.isCarMarkValid("Skoda");
        Assert.assertTrue(result);
    }

    @Test
    public void isTariffValidPositiveTest() {
        boolean result = DriverValidator.isTariffValid("1.99");
        Assert.assertTrue(result);
    }

    @Test
    public void isCarNumberValidNegativeTest() {
        boolean result = DriverValidator.isCarNumberValid("7511AT799");
        Assert.assertFalse(result);
    }

    @Test
    public void isDocIdValidNegativeTest() {
        boolean result = DriverValidator.isDocIdValid("7ABytt5");
        Assert.assertFalse(result);
    }

    @Test
    public void isCarMarkValidNegativeTest() {
        boolean result = DriverValidator.isCarMarkValid("asdfghjkhytrewesdkokoko12345678911");
        Assert.assertFalse(result);
    }

    @Test
    public void isTariffValidNegativeTest() {
        boolean result = DriverValidator.isTariffValid("12312.00");
        Assert.assertFalse(result);
    }
}