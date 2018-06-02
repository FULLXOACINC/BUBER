package by.zhuk.buber.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CoordinateValidatorTest {

    @Test
    public void isCoordinateValidPositiveTest() {
        boolean result = CoordinateValidator.isCoordinateValid("38.00312");
        Assert.assertTrue(result);
    }

    @Test
    public void isCoordinateValidNegativeTest() {
        boolean result = CoordinateValidator.isCoordinateValid("test");
        Assert.assertFalse(result);
    }
}