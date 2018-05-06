package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.oauth.OAuth;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;

public class OAuthReceiver {
    private static Logger logger = LogManager.getLogger(OAuthReceiver.class);

    public String findEmail(OAuth oAuth, String code) throws ReceiverException {

        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(oAuth.getTokenUrl());
        post.addParameter("grant_type", "authorization_code");
        post.addParameter("code", code);
        post.addParameter("client_id", oAuth.getClientId());
        post.addParameter("client_secret", oAuth.getClientSecret());
        post.addParameter("redirect_uri", oAuth.getRedirectUri());
        try {
            httpClient.executeMethod(post);
            JSONObject jsonPostResult = parseResult(post);
            GetMethod get = new GetMethod(oAuth.takeLoginInfoUrl(jsonPostResult));
            httpClient.executeMethod(get);
            JSONObject jsonGetResult = parseResult(get);

            return oAuth.takeEmail(jsonGetResult);

        } catch (IOException e) {
            throw new ReceiverException(e);
        }
    }

    private JSONObject parseResult(HttpMethod method) throws IOException {
        return new JSONObject(
                new JSONTokener(new InputStreamReader(method.getResponseBodyAsStream())));
    }
}