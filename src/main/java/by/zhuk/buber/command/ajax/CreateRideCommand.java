package by.zhuk.buber.command.ajax;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import by.zhuk.buber.validator.CoordinateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class CreateRideCommand implements AJAXCommand{

    private static Logger logger = LogManager.getLogger(CreateRideCommand.class);

    private static final String START_LNG = "startLng";
    private static final String START_LAT = "startLat";
    private static final String END_LNG = "endLng";
    private static final String END_LAT = "endLat";

    private static final String DRIVER = "driver";

    private static final String DRIVER_NOT_EXIST = "driverNotExist";
    private static final String NOT_VALID_COORDINATE = "notValidCoordinate";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String driver = request.getParameter(DRIVER);
        String startLat = request.getParameter(START_LAT);
        String startLng = request.getParameter(START_LNG);
        String endLat = request.getParameter(END_LAT);
        String endLng = request.getParameter(END_LNG);

        if (!CoordinateValidator.isCoordinateValid(startLng) || !CoordinateValidator.isCoordinateValid(startLat) || !CoordinateValidator.isCoordinateValid(endLng) || !CoordinateValidator.isCoordinateValid(endLat)) {
            json.put(NOT_VALID_COORDINATE, NOT_VALID_COORDINATE);
        }
        RideReceiver rideReceiver = new RideReceiver();
        DriverReceiver driverReceiver = new DriverReceiver();
        try {
            if(!driverReceiver.isDriverExist(driver)){
                json.put(DRIVER_NOT_EXIST, DRIVER_NOT_EXIST);
            }
            if(json.length()==0){
                BigDecimal price = new BigDecimal("10.00");
//                rideReceiver.createRide(driver,Float.parseFloat(startLat),Float.parseFloat(startLng),Float.parseFloat(endLat),Float.parseFloat(startLng),price);
            }

        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}