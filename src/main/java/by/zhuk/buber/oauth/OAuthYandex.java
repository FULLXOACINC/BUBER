package by.zhuk.buber.oauth;

import org.json.JSONObject;
/**
 * Class include methods and info to interaction with yandex oauth protocol
 */
public class OAuthYandex extends AbstractOAuth {

    private static final String TYPE = "yandex";
    private static final String EMAILS = "emails";

    OAuthYandex() {
        setupOAuth(TYPE);
    }

    @Override
    public String takeEmail(JSONObject json) {
        return (String) json.getJSONArray(EMAILS).get(0);
    }


}