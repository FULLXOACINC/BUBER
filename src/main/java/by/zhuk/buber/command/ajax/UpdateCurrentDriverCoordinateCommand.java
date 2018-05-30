package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.validator.CoordinateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UpdateCurrentDriverCoordinateCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(UpdateCurrentDriverCoordinateCommand.class);

    private static final String LNG = "lng";
    private static final String LAT = "lat";
    private static final String NOT_VALID_COORDINATE = "notValidCoordinate";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String lng = request.getParameter(LNG);
        String lat = request.getParameter(LAT);

        if (!CoordinateValidator.isCoordinateValid(lat) || !CoordinateValidator.isCoordinateValid(lng)) {
            json.put(NOT_VALID_COORDINATE, NOT_VALID_COORDINATE);
            return json;
        }

        DriverReceiver driverReceiver = new DriverReceiver();

        try {
            HttpSession session = request.getSession();
            String login = (String) session.getAttribute(UserConstant.LOGIN);
            driverReceiver.updateCurrentCoordinate(Float.parseFloat(lat), Float.parseFloat(lng), login);
            json.put(ALL_CORRECT, ALL_CORRECT);
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}