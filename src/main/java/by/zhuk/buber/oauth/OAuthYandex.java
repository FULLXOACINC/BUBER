package by.zhuk.buber.oauth;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class OAuthYandex implements OAuth {
    private static Logger logger = LogManager.getLogger(OAuthYandex.class);

    private static OAuthYandex instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private String clientId = null;
    private String clientSecret = null;
    private String redirectUri = null;
    private String authUrl = null;
    private String tokenUrl = null;

    public static OAuthYandex getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new OAuthYandex();

                    instance.init();

                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private void init() {
        final String FACEBOOK_PROPERTY = "yandex";
        final String FACEBOOK_CLIENT_ID = "yandex.clientId";
        final String FACEBOOK_CLIENT_SECRET = "yandex.clientSecret";
        final String FACEBOOK_REDIRECT_URI = "yandex.redirectUri";
        final String FACEBOOK_AUTH_URI = "yandex.authUrl";
        final String FACEBOOK_TOKEN_URL = "yandex.tokenUrl";

        ResourceBundle resourceBundle;
        String authURI;

        try {
            resourceBundle = ResourceBundle.getBundle(FACEBOOK_PROPERTY);

            clientId = resourceBundle.getString(FACEBOOK_CLIENT_ID);
            clientSecret = resourceBundle.getString(FACEBOOK_CLIENT_SECRET);
            redirectUri = resourceBundle.getString(FACEBOOK_REDIRECT_URI);
            authURI = resourceBundle.getString(FACEBOOK_AUTH_URI);
            tokenUrl = resourceBundle.getString(FACEBOOK_TOKEN_URL);

        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, "Hasn't found bundle for facebook");
            throw new RuntimeException("Hasn't found bundle for facebook");
        }
        try {
            authUrl = authURI + "authorize?client_id=" + clientId +
                    "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") +
                    "&response_type=code" +
                    "&force_confirm=yes";
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.WARN, "Unknown problem in OAuthYandex");
        }
    }

    @Override
    public JSONObject parseResult(HttpMethod method) {
        JSONObject result = null;
        try {
            result = new JSONObject(
                    new JSONTokener(new InputStreamReader(method.getResponseBodyAsStream())));
        } catch (JSONException | IOException e) {
            logger.catching(e);
        }
        return result;
    }

    @Override
    public String takeDisplayName(JSONObject json) {
        return json.getString("display_name");
    }

    @Override
    public boolean hasError(String error) {
        return error != null && error.equals("invalid_request");
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    @Override
    public String getAuthUrl() {
        return authUrl;
    }

    @Override
    public String getTokenUrl() {
        return tokenUrl;
    }

    @Override
    public String takeLoginInfoUrl(JSONObject json) {
        String token = json.getString("access_token");
        return "https://login.yandex.ru/info?format=json&oauth_token=" + token;
    }
}