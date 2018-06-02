package by.zhuk.buber.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceValidatorTest {

    @Test
    public void isDistanceValidPositiveTest() {
        boolean result = DistanceValidator.isDistanceInRange(10000);
        Assert.assertTrue(result);
    }

    @Test
    public void isDistanceValidNegativeTest() {
        boolean result = DistanceValidator.isDistanceInRange(0);
        Assert.assertFalse(result);
    }
}