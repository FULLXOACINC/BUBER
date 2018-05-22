package by.zhuk.buber.command.ajax;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.receiver.GeoDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class GeoDecodeRaidStartEndCommand implements AJAXCommand{
    private static Logger logger = LogManager.getLogger(GeoDecodeRaidStartEndCommand.class);
    private static final String START_ADDRESS = "startAddress";
    private static final String END_ADDRESS = "endAddress";

    private static final String LNG = "lng";
    private static final String LAT = "lat";

    private static final String START = "start";
    private static final String END = "end";

    private static final String DECODE_PROBLEM = "decodeProblem";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String startAddress = request.getParameter(START_ADDRESS);
        String endAddress = request.getParameter(END_ADDRESS);
        GeoDecoder decoder = new GeoDecoder();
        try {
            Optional<Coordinate> optionalStartCoordinate = decoder.decodeCoordinate(startAddress);
            Optional<Coordinate> optionalEndCoordinate = decoder.decodeCoordinate(endAddress);
            if (optionalStartCoordinate.isPresent() && optionalEndCoordinate.isPresent()) {
                Coordinate startCoordinate = optionalStartCoordinate.get();
                Coordinate endCoordinate = optionalEndCoordinate.get();

                JSONObject startJSONObject= new JSONObject();
                startJSONObject.put(LNG,startCoordinate.getLng());
                startJSONObject.put(LAT,startCoordinate.getLat());

                JSONObject endJSONObject= new JSONObject();
                endJSONObject.put(LNG,endCoordinate.getLng());
                endJSONObject.put(LAT,endCoordinate.getLat());

                json.put(START,startJSONObject);
                json.put(END,endJSONObject);
            }else {
                json.put(DECODE_PROBLEM,DECODE_PROBLEM);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR,ERROR);
        }
        return json;
    }
}