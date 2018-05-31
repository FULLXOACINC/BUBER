package by.zhuk.buber.validator;

import org.json.JSONObject;

public class JSONValidator {
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "error_message";

    public static boolean isJSONHasError(JSONObject json) {
        return json != null && (json.has(ERROR) || json.has(ERROR_MESSAGE));
    }
}