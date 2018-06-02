package by.zhuk.buber.validator;

import by.zhuk.buber.constant.ErrorConstant;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JSONValidatorTest {

    @Test
    public void isJSONHasErrorPositiveTest() {
        JSONObject json = new JSONObject();
        json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        boolean result = JSONValidator.isJSONHasError(json);
        Assert.assertTrue(result);
    }

    @Test
    public void isJSONHasErrorMessagePositiveTest() {
        JSONObject json = new JSONObject();
        json.put(ErrorConstant.ERROR_MESSAGE, ErrorConstant.ERROR);
        boolean result = JSONValidator.isJSONHasError(json);
        Assert.assertTrue(result);
    }

    @Test
    public void isJSONHasErrorNegativeTest() {
        JSONObject json = new JSONObject();
        boolean result = JSONValidator.isJSONHasError(json);
        Assert.assertFalse(result);
    }
}