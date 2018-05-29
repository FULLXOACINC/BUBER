package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
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
import java.util.Optional;

public class FindDistanceInfoCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindDistanceInfoCommand.class);

    private static final String DISTANCE = "distance";
    private static final String DURATION = "duration";
    private static final String DISCOUNT = "discount";

    private static final String START_LNG = "startLng";
    private static final String START_LAT = "startLat";
    private static final String END_LNG = "endLng";
    private static final String END_LAT = "endLat";

    private static final String FIND_PROBLEM = "findProblem";
    private static final String NOT_VALID_COORDINATE = "notValidCoordinate";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String startLng = request.getParameter(START_LNG);
        String startLat = request.getParameter(START_LAT);
        String endLng = request.getParameter(END_LNG);
        String endLat = request.getParameter(END_LAT);

        if (!CoordinateValidator.isCoordinateValid(startLng) || !CoordinateValidator.isCoordinateValid(startLat) || !CoordinateValidator.isCoordinateValid(endLng) || !CoordinateValidator.isCoordinateValid(endLat)) {
            json.put(NOT_VALID_COORDINATE, NOT_VALID_COORDINATE);
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
                Optional<Float> discountOptional = userReceiver.findUserDiscount(login);

                if (discountOptional.isPresent()) {
                    Float discount = discountOptional.get();

                    json.put(DISTANCE, distanceInfo.getDistance());
                    json.put(DURATION, distanceInfo.getDuration());
                    json.put(DISCOUNT, discount);
                } else {
                    logger.log(Level.WARN, "Unknown problem with tariff or discount");
                    json.put(ERROR, ERROR);
                }

            } else {
                json.put(FIND_PROBLEM, FIND_PROBLEM);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}