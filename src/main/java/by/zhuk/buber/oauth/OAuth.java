package by.zhuk.buber.oauth;

import org.json.JSONObject;

public interface OAuth {

    boolean hasError(String request);

    String getClientId();

    String getClientSecret();

    String getRedirectUri();

    String getAuthUrl();

    String getTokenUrl();

    String takeLoginInfoUrl(JSONObject tokenUrl);

    String takeEmail(JSONObject json);
}