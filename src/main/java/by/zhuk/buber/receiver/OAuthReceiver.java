package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.oauth.AbstractOAuth;
import by.zhuk.buber.validator.JSONValidator;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;

public class OAuthReceiver {


    public String findEmail(AbstractOAuth oAuth, String code) throws ReceiverException {

        HttpClient httpClient = new HttpClient();
        HttpMethod post = oAuth.takeTokenHttpMethod(code);
        try {
            httpClient.executeMethod(post);
            JSONObject jsonPostResult = parseResult(post);
            if (JSONValidator.isJSONHasError(jsonPostResult)) {
                throw new ReceiverException("jsonPostResult has error " + jsonPostResult.get("error"));
            }
            GetMethod get = new GetMethod(oAuth.takeInfoUrl(jsonPostResult));
            httpClient.executeMethod(get);
            JSONObject jsonGetResult = parseResult(get);

            if (JSONValidator.isJSONHasError(jsonGetResult)) {
                throw new ReceiverException("jsonGetResult has error " + jsonPostResult.get("error"));
            }

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