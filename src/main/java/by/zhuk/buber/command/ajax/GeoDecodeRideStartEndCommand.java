package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.GeoConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.receiver.GeoDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
/**
 * Class include info how to react to a ajax GeoDecodeRideStartEnd
 */
public class GeoDecodeRideStartEndCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(GeoDecodeRideStartEndCommand.class);
    private static final String START_ADDRESS = "startAddress";
    private static final String END_ADDRESS = "endAddress";
    /**
     * Expected parameters:
     * 1)startAddress
     * 2)endAddress
     */
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

                JSONObject startJSONObject = new JSONObject();
                startJSONObject.put(GeoConstant.LNG, startCoordinate.getLng());
                startJSONObject.put(GeoConstant.LAT, startCoordinate.getLat());

                JSONObject endJSONObject = new JSONObject();
                endJSONObject.put(GeoConstant.LNG, endCoordinate.getLng());
                endJSONObject.put(GeoConstant.LAT, endCoordinate.getLat());

                json.put(GeoConstant.START, startJSONObject);
                json.put(GeoConstant.END, endJSONObject);
            } else {
                json.put(GeoConstant.DECODE_PROBLEM, GeoConstant.DECODE_PROBLEM);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}