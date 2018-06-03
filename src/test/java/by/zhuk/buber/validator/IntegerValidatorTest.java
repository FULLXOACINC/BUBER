package by.zhuk.buber.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IntegerValidatorTest {

    @Test
    public void isIntegerPositiveTest() {
        boolean result = IntegerValidator.isUnsignedInteger("38");
        Assert.assertTrue(result);
    }

    @Test
    public void isIntegerNegativeTest() {
        boolean result = IntegerValidator.isUnsignedInteger("38.00");
        Assert.assertFalse(result);
    }
}