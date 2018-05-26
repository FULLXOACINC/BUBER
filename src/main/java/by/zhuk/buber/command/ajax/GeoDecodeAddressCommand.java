package by.zhuk.buber.command.ajax;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.receiver.GeoDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class GeoDecodeAddressCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(GeoDecodeAddressCommand.class);
    private static final String CURRENT_ADDRESS = "currentAddress";
    private static final String CURRENT = "current";

    private static final String LNG = "lng";
    private static final String LAT = "lat";

    private static final String DECODE_PROBLEM = "decodeProblem";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String currentAddress = request.getParameter(CURRENT_ADDRESS);
        GeoDecoder decoder = new GeoDecoder();
        try {
            Optional<Coordinate> optionalCurrentCoordinate = decoder.decodeCoordinate(currentAddress);
            if (optionalCurrentCoordinate.isPresent()) {
                Coordinate currentCoordinate = optionalCurrentCoordinate.get();

                JSONObject coordinateJSON = new JSONObject();
                coordinateJSON.put(LNG, currentCoordinate.getLng());
                coordinateJSON.put(LAT, currentCoordinate.getLat());

                json.put(CURRENT, coordinateJSON);
            } else {
                json.put(DECODE_PROBLEM, DECODE_PROBLEM);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}