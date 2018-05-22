package by.zhuk.buber.oauth;

import org.json.JSONObject;

public class OAuthYandex extends AbstractOAuth {

    private static final String TYPE = "yandex";
    private static final String EMAILS = "emails";

    public OAuthYandex() {
        setupOAuth(TYPE);
    }

    @Override
    public String takeEmail(JSONObject json) {
        return (String) json.getJSONArray(EMAILS).get(0);
    }

    @Override
    public boolean isHasError(String error) {
        return error != null && error.equals("invalid_request");
    }


}