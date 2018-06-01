package by.zhuk.buber.validator;

import by.zhuk.buber.constant.ErrorConstant;
import org.json.JSONObject;

public class JSONValidator {

    public static boolean isJSONHasError(JSONObject json) {
        return json != null && (json.has(ErrorConstant.ERROR) || json.has(ErrorConstant.ERROR_MESSAGE));
    }
}