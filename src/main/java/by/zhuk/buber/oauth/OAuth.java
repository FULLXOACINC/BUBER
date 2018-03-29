package by.zhuk.buber.oauth;

import org.apache.commons.httpclient.HttpMethod;
import org.json.JSONObject;

public interface OAuth {

    boolean hasError(String request);

    String getClientId();

    String getClientSecret();

    String getRedirectUri();

    String getAuthUrl();

    String getTokenUrl();

    String takeLoginInfoUrl(JSONObject tokenUrl);

    JSONObject parseResult(HttpMethod method);

    String takeEmail(JSONObject json);
}