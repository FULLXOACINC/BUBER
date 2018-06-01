package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.GeoConstant;
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


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String lng = request.getParameter(GeoConstant.LNG);
        String lat = request.getParameter(GeoConstant.LAT);

        if (!CoordinateValidator.isCoordinateValid(lat) || !CoordinateValidator.isCoordinateValid(lng)) {
            json.put(GeoConstant.NOT_VALID_COORDINATE, GeoConstant.NOT_VALID_COORDINATE);
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
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}