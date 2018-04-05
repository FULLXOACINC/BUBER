package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.oauth.OAuth;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;

import java.io.IOException;

public class OAuthReceiver {

    public String findEmail(OAuth oAuth, String code) throws ReceiverException {

        HttpClient httpclient = new HttpClient();
        PostMethod post = new PostMethod(oAuth.getTokenUrl());
        post.addParameter("grant_type", "authorization_code");
        post.addParameter("code", code);
        post.addParameter("client_id", oAuth.getClientId());
        post.addParameter("client_secret", oAuth.getClientSecret());
        post.addParameter("redirect_uri", oAuth.getRedirectUri());
        try {
            httpclient.executeMethod(post);
            JSONObject jsonPostResult = oAuth.parseResult(post);
            GetMethod get = new GetMethod(oAuth.takeLoginInfoUrl(jsonPostResult));
            httpclient.executeMethod(get);
            JSONObject jsonGetResult = oAuth.parseResult(get);

            return oAuth.takeEmail(jsonGetResult);

        } catch (IOException e) {
            throw new ReceiverException(e);
        }
    }
}