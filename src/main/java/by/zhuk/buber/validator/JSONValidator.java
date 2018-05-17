package by.zhuk.buber.validator;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONValidator {
    private static final String ERROR = "error";

    public static boolean isJSONHasError(JSONObject json) {
        try {
            json.get(ERROR);
            return true;
        } catch (JSONException e) {
            return false;
        }

    }
}