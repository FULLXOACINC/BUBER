package by.zhuk.buber.receiver;

import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.GeoConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.DistanceInfo;
import by.zhuk.buber.validator.JSONValidator;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Formatter;
import java.util.Optional;

public class DistanceReceiver {
    private static final String DISTANCE_INFO_URL = "https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s&destination=%s,%s&key=AIzaSyDCd_w_dcctv7LlPuHYIn2dbpA74JSyaVY";
    private static final String ROUTES = "routes";
    private static final String LEGS = "legs";
    private static final String VALUE = "value";
    private static final String TEXT = "text";

    public Optional<DistanceInfo> findDistanceInfo(String startLat, String startLng, String endLat, String endLng) throws ReceiverException {
        HttpClient httpClient = new HttpClient();
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        try {
            formatter.format(DISTANCE_INFO_URL, URLEncoder.encode(startLat, CommandConstant.ENCODE_UTF8), URLEncoder.encode(startLng, CommandConstant.ENCODE_UTF8), URLEncoder.encode(endLat, CommandConstant.ENCODE_UTF8), URLEncoder.encode(endLng, CommandConstant.ENCODE_UTF8));

            GetMethod getMethod = new GetMethod(stringBuilder.toString());

            httpClient.executeMethod(getMethod);
            JSONObject jsonObject = parseResult(getMethod);
            if(JSONValidator.isJSONHasError(jsonObject)){
                throw new ReceiverException("JSON has error");
            }
            Optional<DistanceInfo> optionalDistanceInfo = Optional.empty();

            if (JSONValidator.isJSONHasError(jsonObject)) {
                return optionalDistanceInfo;
            }
            JSONArray jsonArray = (JSONArray) jsonObject.get(ROUTES);

            if (jsonArray.length() == 0) {
                return optionalDistanceInfo;
            }
            JSONObject routeObject = (JSONObject) jsonArray.get(0);
            JSONArray legsJSONArray = (JSONArray) routeObject.get(LEGS);
            JSONObject legsJSON = (JSONObject) legsJSONArray.get(0);
            JSONObject distanceJSON = (JSONObject) legsJSON.get(GeoConstant.DISTANCE);
            JSONObject durationJSON = (JSONObject) legsJSON.get(GeoConstant.DURATION);

            DistanceInfo distanceInfo = new DistanceInfo();
            distanceInfo.setDistance(distanceJSON.getInt(VALUE));
            distanceInfo.setDuration(durationJSON.getString(TEXT));

            optionalDistanceInfo = Optional.of(distanceInfo);
            return optionalDistanceInfo;
        } catch (IOException e) {
            throw new ReceiverException(e);
        }
    }

    private JSONObject parseResult(HttpMethod method) throws IOException {
        return new JSONObject(
                new JSONTokener(new InputStreamReader(method.getResponseBodyAsStream())));
    }
}