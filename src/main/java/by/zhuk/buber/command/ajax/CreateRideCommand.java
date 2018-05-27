package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.CoordinateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class CreateRideCommand implements AJAXCommand {

    private static Logger logger = LogManager.getLogger(CreateRideCommand.class);

    private static final String START_LNG = "startLng";
    private static final String START_LAT = "startLat";
    private static final String END_LNG = "endLng";
    private static final String END_LAT = "endLat";

    private static final String DRIVER = "driver";

    private static final String DRIVER_NOT_EXIST = "driverNotExist";
    private static final String BALANCE_NEGATIVE = "balanceNegative";
    private static final String DRIVER_NOT_SUITABLE = "driverNotSuitable";
    private static final String NOT_VALID_COORDINATE = "notValidCoordinate";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String driver = request.getParameter(DRIVER);
        String startLatString = request.getParameter(START_LAT);
        String startLngString = request.getParameter(START_LNG);
        String endLatString = request.getParameter(END_LAT);
        String endLngString = request.getParameter(END_LNG);

        if (!CoordinateValidator.isCoordinateValid(startLngString) || !CoordinateValidator.isCoordinateValid(startLatString) || !CoordinateValidator.isCoordinateValid(endLngString) || !CoordinateValidator.isCoordinateValid(endLatString)) {
            json.put(NOT_VALID_COORDINATE, NOT_VALID_COORDINATE);
        }
        RideReceiver rideReceiver = new RideReceiver();
        UserReceiver userReceiver = new UserReceiver();
        DriverReceiver driverReceiver = new DriverReceiver();

        HttpSession session = request.getSession();
        String login=(String)session.getAttribute(UserConstant.LOGIN);
        try {
            float startLat =Float.parseFloat(startLatString);
            float startLng =Float.parseFloat(startLngString);
            float endLat =Float.parseFloat(endLatString);
            float endLng =Float.parseFloat(endLngString);
            if (!driverReceiver.isDriverExist(driver)) {
                json.put(DRIVER_NOT_EXIST, DRIVER_NOT_EXIST);
            }
            if (userReceiver.isBalanceNegative(login)) {
                json.put(BALANCE_NEGATIVE, BALANCE_NEGATIVE);
            }
            if (!driverReceiver.isDriverSuitable(startLat, startLng,driver)) {
                json.put(DRIVER_NOT_SUITABLE, DRIVER_NOT_SUITABLE);
            }
            //TODO проверить растояние


            if (json.length() == 0) {

                BigDecimal price = new BigDecimal("10.00");
                rideReceiver.createRide(driver,login, startLat, startLng, endLat, endLng, price);
            }

        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}