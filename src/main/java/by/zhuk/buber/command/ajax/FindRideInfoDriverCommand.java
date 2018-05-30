package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.RideReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class FindRideInfoDriverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindRideInfoDriverCommand.class);

    private static final String LNG = "lng";
    private static final String LAT = "lat";
    private static final String START = "start";
    private static final String END = "end";

    private static final String RIDE_NOT_FOUND = "rideNotFound";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        RideReceiver rideReceiver = new RideReceiver();

        UserReceiver userReceiver = new UserReceiver();
        try {

            Optional<Ride> rideOptional = rideReceiver.findCurrentDriverRide(login);
            if (rideOptional.isPresent()) {
                Ride ride = rideOptional.get();
                Optional<User> userOptional = userReceiver.findUserInfoForRide(ride.getPassenger().getLogin());
                User user = userOptional.get();
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
                json.put("firstName", user.getFirstName());
                json.put("lastName", user.getLastName());
                json.put("phoneNumber", user.getPhoneNumber());
                json.put("rideId", ride.getRideId());
                json.put("isDriverStart", ride.isDriverAcceptStart());
                json.put("isDriverEnd", ride.isDriverAcceptEnd());
                json.put("isPassengerStart", ride.isPassengerAcceptStart());
                json.put("isPassengerEnd", ride.isPassengerAcceptEnd());

            } else {
                json.put(RIDE_NOT_FOUND, RIDE_NOT_FOUND);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}