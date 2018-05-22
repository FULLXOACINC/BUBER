package by.zhuk.buber.oauth;

import by.zhuk.buber.constant.CommandConstant;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Formatter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class AbstractOAuth {
    private static Logger logger = LogManager.getLogger(AbstractOAuth.class);
    private final static String ACCESS_TOKEN = "access_token";
    private final static String GRANT_TYPE = "grant_type";
    private final static String AUTHORIZATION_CODE = "authorization_code";
    private final static String CODE = "code";
    private final static String CLIENT_ID = "client_id";
    private final static String CLIENT_SECRET = "client_secret";
    private final static String REDIRECT_URI = "redirect_uri";

    private String clientId;
    private String redirectUri;
    private String clientSecret;
    private String authUrl;
    private String infoUrl;
    private String tokenUrl;

    public abstract boolean isHasError(String request);

    public String getAuthUrl() {
        return authUrl;
    }

    public String takeInfoUrl(JSONObject json) {
        String token = json.getString(ACCESS_TOKEN);
        return infoUrl + token;
    }

    public abstract String takeEmail(JSONObject json);

    public HttpMethod takeTokenHttpMethod(String code) {
        PostMethod post = new PostMethod(tokenUrl);
        post.addParameter(GRANT_TYPE, AUTHORIZATION_CODE);
        post.addParameter(CODE, code);
        post.addParameter(CLIENT_ID, clientId);
        post.addParameter(CLIENT_SECRET, clientSecret);
        post.addParameter(REDIRECT_URI, redirectUri);
        return post;
    }

    void setupOAuth(String type) {
        final String PROPERTY = "properties/" + type;
        final String CLIENT_ID = type + ".clientId";
        final String CLIENT_SECRET = type + ".clientSecret";
        final String REDIRECT_URI = type + ".redirectUri";
        final String AUTH_URI = type + ".authUrl";
        final String TOKEN_URL = type + ".tokenUrl";
        final String INFO_URL = type + ".infoUrl";

        ResourceBundle resourceBundle;

        try {
            resourceBundle = ResourceBundle.getBundle(PROPERTY);
            clientId = resourceBundle.getString(CLIENT_ID);
            clientSecret = resourceBundle.getString(CLIENT_SECRET);
            redirectUri = resourceBundle.getString(REDIRECT_URI);
            tokenUrl = resourceBundle.getString(TOKEN_URL);
            infoUrl = resourceBundle.getString(INFO_URL);

            StringBuilder stringBuilder = new StringBuilder();
            Formatter formatter = new Formatter(stringBuilder);

            formatter.format(resourceBundle.getString(AUTH_URI), clientId, URLEncoder.encode(redirectUri, CommandConstant.ENCODE_UTF8));
            authUrl = stringBuilder.toString();
        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, "Hasn't found bundle for " + type);
            throw new RuntimeException("Hasn't found bundle for " + type);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.WARN, "Unknown problem in OAuth (type=" + type + ")");
        }
    }
}