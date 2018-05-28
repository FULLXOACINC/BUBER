package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.DistanceInfo;
import by.zhuk.buber.receiver.DistanceReceiver;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.CoordinateValidator;
import by.zhuk.buber.validator.DistanceValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class CreateRideCommand implements AJAXCommand {

    private static Logger logger = LogManager.getLogger(CreateRideCommand.class);

    private static final String START_LNG = "startLng";
    private static final String START_LAT = "startLat";
    private static final String END_LNG = "endLng";
    private static final String END_LAT = "endLat";

    private static final String DRIVER = "driver";

    private static final String DRIVER_NOT_EXIST = "driverNotExist";
    private static final String BALANCE_NEGATIVE = "balanceNegative";
    private static final String DISTANCE_NOT_IN_RANGE = "distanceNotInRange";
    private static final String DRIVER_NOT_SUITABLE = "driverNotSuitable";
    private static final String NOT_VALID_COORDINATE = "notValidCoordinate";
    private static final String RIDE_EXIST = "rideExist";
    private static final String DRIVER_EQ_PASSENGER = "driverEqPassenger";

    private static final BigDecimal THOUSAND = new BigDecimal("1000.00");
    private static final int ROUND = 2;

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
        DistanceReceiver distanceReceiver = new DistanceReceiver();

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        try {
            float startLat = Float.parseFloat(startLatString);
            float startLng = Float.parseFloat(startLngString);
            float endLat = Float.parseFloat(endLatString);
            float endLng = Float.parseFloat(endLngString);
            if (!driverReceiver.isDriverExist(driver)) {
                json.put(DRIVER_NOT_EXIST, DRIVER_NOT_EXIST);
            }
            if (userReceiver.isBalanceNegative(login)) {
                json.put(BALANCE_NEGATIVE, BALANCE_NEGATIVE);
            }
            if (rideReceiver.isRideExist(login)) {
                json.put(RIDE_EXIST, RIDE_EXIST);
            }
            if (!driverReceiver.isDriverSuitable(startLat, startLng, driver)) {
                json.put(DRIVER_NOT_SUITABLE, DRIVER_NOT_SUITABLE);
            }
            if (driver.equals(login)) {
                json.put(DRIVER_EQ_PASSENGER, DRIVER_EQ_PASSENGER);
            }
            Optional<DistanceInfo> distanceInfoOptional = distanceReceiver.findDistanceInfo(startLatString, startLngString, endLatString, endLngString);
            if (distanceInfoOptional.isPresent()) {
                DistanceInfo distanceInfo = distanceInfoOptional.get();
                int distance = distanceInfo.getDistance();
                if (!DistanceValidator.isDistanceInRange(distance)) {
                    json.put(DISTANCE_NOT_IN_RANGE, DISTANCE_NOT_IN_RANGE);
                }
                if (json.length() == 0) {
                    BigDecimal bigDecimalDistance = new BigDecimal(distance);
                    Optional<BigDecimal> tariffOptional = driverReceiver.findDriverTariff(driver);
                    Optional<Float> discountOptional = userReceiver.findUserDiscount(login);
                    if (tariffOptional.isPresent() && discountOptional.isPresent()) {
                        BigDecimal tariff = tariffOptional.get();
                        Float discount = discountOptional.get();
                        BigDecimal bigDecimalDiscount=new BigDecimal(1-discount);
                        BigDecimal price = tariff.multiply(bigDecimalDistance).multiply(bigDecimalDiscount).divide(THOUSAND, ROUND, BigDecimal.ROUND_HALF_UP);
                        rideReceiver.createRide(driver, login, startLat, startLng, endLat, endLng, price);
                        json.put(ALL_CORRECT, ALL_CORRECT);
                    } else {
                        logger.log(Level.WARN, "Unknown problem with tariff or discount");
                        json.put(ERROR, ERROR);
                    }
                }
            } else {
                logger.log(Level.WARN, "Unknown problem with DistanceInfo");
                json.put(ERROR, ERROR);
            }


        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}