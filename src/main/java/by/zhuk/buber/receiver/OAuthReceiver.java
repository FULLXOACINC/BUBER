package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.oauth.AbstractOAuth;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.find.driver.FindDriverByLoginSpecification;
import by.zhuk.buber.validator.JSONValidator;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Class include method to interaction with things connection with oauth protocol
 */
public class OAuthReceiver {

    /**
     * Method find email by code, token and oAuth
     *
     * @return true if exist,else is not exist
     * @throws ReceiverException throws when there are problems json and oauth protocol
     * @see Specification,HttpClient,HttpMethod,JSONValidator,JSONObject
     */
    public String findEmail(AbstractOAuth oAuth, String code) throws ReceiverException {

        HttpClient httpClient = new HttpClient();
        HttpMethod post = oAuth.takeTokenHttpMethod(code);
        try {
            httpClient.executeMethod(post);
            JSONObject jsonPostResult = parseResult(post);
            if (JSONValidator.isJSONHasError(jsonPostResult)) {
                throw new ReceiverException("jsonPostResult has error ");
            }
            GetMethod get = new GetMethod(oAuth.takeInfoUrl(jsonPostResult));
            httpClient.executeMethod(get);
            JSONObject jsonGetResult = parseResult(get);

            if (JSONValidator.isJSONHasError(jsonGetResult)) {
                throw new ReceiverException("jsonGetResult has error ");
            }

            return oAuth.takeEmail(jsonGetResult);

        } catch (IOException e) {
            throw new ReceiverException(e);
        }
    }
    /**
     * Method parse json
     *
     * @return parse JSONObject by HttpMethod
     * @throws IOException throws when there are problems json and oauth protocol
     * @see JSONObject,HttpMethod,InputStreamReader,JSONTokener
     */
    private JSONObject parseResult(HttpMethod method) throws IOException {
        return new JSONObject(
                new JSONTokener(new InputStreamReader(method.getResponseBodyAsStream())));
    }


}