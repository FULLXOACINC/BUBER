package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
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
    private static final String RESULTS = "results";
    private static final String GEOMETRY = "geometry";
    private static final String LOCATION = "location";
    private static final String LAT = "lat";
    private static final String LNG = "lng";

    public Optional<DistanceInfo> findDistanceInfo(String startLat, String startLng, String endLat, String endLng) throws ReceiverException {
        HttpClient httpClient = new HttpClient();
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        try {
            formatter.format(DISTANCE_INFO_URL, URLEncoder.encode(startLat, "UTF-8"), URLEncoder.encode(startLng, "UTF-8"), URLEncoder.encode(endLat, "UTF-8"), URLEncoder.encode(endLng, "UTF-8"));

            GetMethod getMethod = new GetMethod(stringBuilder.toString());

            httpClient.executeMethod(getMethod);
            JSONObject jsonObject = parseResult(getMethod);
            Optional<DistanceInfo> optionalDistanceInfo = Optional.empty();

            if (JSONValidator.isJSONHasError(jsonObject)) {
                return optionalDistanceInfo;
            }
            JSONArray jsonArray = (JSONArray) jsonObject.get("routes");

            if (jsonArray.length() == 0) {
                return optionalDistanceInfo;
            }
            JSONObject routeObject = (JSONObject)jsonArray.get(0);
            JSONArray legsJSONArray = (JSONArray) routeObject.get("legs");
            JSONObject legsJSON = (JSONObject) legsJSONArray.get(0);
            JSONObject distanceJSON =(JSONObject)legsJSON.get("distance");
            JSONObject durationJSON =(JSONObject)legsJSON.get("duration");

            DistanceInfo distanceInfo = new DistanceInfo();
            distanceInfo.setDistance(distanceJSON.getInt("value"));
            distanceInfo.setDuration(durationJSON.getString("text"));

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