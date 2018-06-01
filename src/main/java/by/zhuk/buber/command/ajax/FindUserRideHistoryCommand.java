package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.GeoConstant;
import by.zhuk.buber.constant.RideConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.receiver.RideReceiver;
import by.zhuk.buber.validator.IntegerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindUserRideHistoryCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindUsersCommand.class);

    private static final String DRIVER_LOGIN = "driverLogin";
    private static final String DRIVER_PHONE_NUMBER = "driverPhoneNumber";
    private static final String PRICE = "price";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        RideReceiver rideReceiver = new RideReceiver();
        String stringIndex = request.getParameter(UserConstant.INDEX);
        int index;
        if (!IntegerValidator.isInteger(stringIndex)) {
            index = 0;
        } else {
            index = Integer.parseInt(stringIndex);
        }
        try {
            List<Ride> rides = rideReceiver.findUserRideHistory(login);
            if (!rides.isEmpty()) {
                if (index > rides.size() - 1) {
                    index = rides.size() - 1;
                }
                Ride ride = rides.get(index);
                Driver driver = ride.getDriver();
                json.put(RideConstant.RIDE_ID, ride.getRideId());
                json.put(UserConstant.FIRST_NAME, driver.getFirstName());
                json.put(UserConstant.LAST_NAME, driver.getLastName());
                json.put(DRIVER_LOGIN, driver.getLogin());
                json.put(DRIVER_PHONE_NUMBER, driver.getPhoneNumber());
                json.put(PRICE, ride.getPrice());

                Coordinate startCoordinate = ride.getStartCoordinate();
                Coordinate endCoordinate = ride.getEndCoordinate();

                JSONObject startJSONObject = new JSONObject();
                startJSONObject.put(GeoConstant.LNG, startCoordinate.getLng());
                startJSONObject.put(GeoConstant.LAT, startCoordinate.getLat());

                JSONObject endJSONObject = new JSONObject();
                endJSONObject.put(GeoConstant.LNG, endCoordinate.getLng());
                endJSONObject.put(GeoConstant.LAT, endCoordinate.getLat());

                json.put(GeoConstant.START, startJSONObject);
                json.put(GeoConstant.END, endJSONObject);
            } else {
                json.put(RideConstant.RIDE_NOT_FOUND, RideConstant.RIDE_NOT_FOUND);
                index = 0;
            }
            json.put(UserConstant.INDEX, index);
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}