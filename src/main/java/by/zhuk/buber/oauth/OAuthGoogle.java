package by.zhuk.buber.oauth;

import org.json.JSONObject;

public class OAuthGoogle extends AbstractOAuth {
    private static final String TYPE = "google";
    private static final String EMAIL = "email";

    OAuthGoogle() {
        super.setupOAuth(TYPE);
    }

    @Override
    public String takeEmail(JSONObject json) {
        return (String) json.get(EMAIL);
    }


}