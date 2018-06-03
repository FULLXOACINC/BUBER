package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.GeoConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.DistanceInfo;
import by.zhuk.buber.receiver.DistanceReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.CoordinateValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;
/**
 * Class include info how to react to a ajax FindDistanceInfo
 */
public class FindDistanceInfoCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindDistanceInfoCommand.class);
    /**
     * Expected parameters:
     * 1)startLat
     * 2)startLng
     * 3)endLat
     * 4)endLng
     */
    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String startLng = request.getParameter(GeoConstant.START_LNG);
        String startLat = request.getParameter(GeoConstant.START_LAT);
        String endLng = request.getParameter(GeoConstant.END_LNG);
        String endLat = request.getParameter(GeoConstant.END_LAT);

        if (!CoordinateValidator.isCoordinateValid(startLng) || !CoordinateValidator.isCoordinateValid(startLat) || !CoordinateValidator.isCoordinateValid(endLng) || !CoordinateValidator.isCoordinateValid(endLat)) {
            json.put(GeoConstant.NOT_VALID_COORDINATE, GeoConstant.NOT_VALID_COORDINATE);
            return json;
        }

        DistanceReceiver distanceReceiver = new DistanceReceiver();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        try {
            Optional<DistanceInfo> optionalDistanceInfo = distanceReceiver.findDistanceInfo(startLat, startLng, endLat, endLng);
            if (optionalDistanceInfo.isPresent()) {
                DistanceInfo distanceInfo = optionalDistanceInfo.get();
                UserReceiver userReceiver = new UserReceiver();
                Optional<BigDecimal> discountOptional = userReceiver.findUserDiscount(login);

                if (discountOptional.isPresent()) {
                    BigDecimal discount = discountOptional.get();

                    json.put(GeoConstant.DISTANCE, distanceInfo.getDistance());
                    json.put(GeoConstant.DURATION, distanceInfo.getDuration());
                    json.put(UserConstant.DISCOUNT, discount);
                } else {
                    logger.log(Level.WARN, "Unknown problem with tariff or discount");
                    json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
                }

            } else {
                json.put(GeoConstant.FIND_PROBLEM, GeoConstant.FIND_PROBLEM);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}