package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.receiver.RideReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindUserRideHistoryCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindUsersCommand.class);
    private static final String INDEX = "index";
    private static final String INTEGER_REGEX = "\\d+";
    private static final String LNG = "lng";
    private static final String LAT = "lat";
    private static final String START = "start";
    private static final String END = "end";

    private static final String RIDE_ID = "rideId";
    private static final String DRIVER_LOGIN = "driverLogin";
    private static final String DRIVER_PHONE_NUMBER = "driverPhoneNumber";
    private static final String PRICE = "price";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String RIDE_NOT_FOUND = "rideNotFound";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        RideReceiver rideReceiver = new RideReceiver();
        String stringIndex = request.getParameter(INDEX);
        int index;
        if (!(stringIndex != null && stringIndex.matches(INTEGER_REGEX))) {
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
                json.put(RIDE_ID, ride.getRideId());
                json.put(FIRST_NAME, driver.getFirstName());
                json.put(LAST_NAME, driver.getLastName());
                json.put(DRIVER_LOGIN, driver.getLogin());
                json.put(DRIVER_PHONE_NUMBER, driver.getPhoneNumber());
                json.put(PRICE, ride.getPrice());

                Coordinate startCoordinate = ride.getStartCoordinate();
                Coordinate endCoordinate = ride.getEndCoordinate();

                JSONObject startJSONObject = new JSONObject();
                startJSONObject.put(LNG, startCoordinate.getLng());
                startJSONObject.put(LAT, startCoordinate.getLat());

                JSONObject endJSONObject = new JSONObject();
                endJSONObject.put(LNG, endCoordinate.getLng());
                endJSONObject.put(LAT, endCoordinate.getLat());

                json.put(START, startJSONObject);
                json.put(END, endJSONObject);
            } else {
                json.put(RIDE_NOT_FOUND, RIDE_NOT_FOUND);
                index = 0;
            }
            json.put(INDEX, index);
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}