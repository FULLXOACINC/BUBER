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
 * Class include info how to react to a ajax GeoDecodeAddress
 */
public class GeoDecodeAddressCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(GeoDecodeAddressCommand.class);
    private static final String CURRENT_ADDRESS = "currentAddress";
    private static final String CURRENT = "current";
    /**
     * Expected parameters:
     * 1)currentAddress
     */
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
                coordinateJSON.put(GeoConstant.LNG, currentCoordinate.getLng());
                coordinateJSON.put(GeoConstant.LAT, currentCoordinate.getLat());

                json.put(CURRENT, coordinateJSON);
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