package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.GeoConstant;
import by.zhuk.buber.constant.RideConstant;
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
                startJSONObject.put(GeoConstant.LNG, startCoordinate.getLng());
                startJSONObject.put(GeoConstant.LAT, startCoordinate.getLat());

                JSONObject endJSONObject = new JSONObject();
                endJSONObject.put(GeoConstant.LNG, endCoordinate.getLng());
                endJSONObject.put(GeoConstant.LAT, endCoordinate.getLat());

                json.put(GeoConstant.START, startJSONObject);
                json.put(GeoConstant.END, endJSONObject);
                json.put(UserConstant.FIRST_NAME, user.getFirstName());
                json.put(UserConstant.LAST_NAME, user.getLastName());
                json.put(UserConstant.PHONE_NUMBER, user.getPhoneNumber());
                json.put(RideConstant.RIDE_ID, ride.getRideId());
                json.put(RideConstant.IS_DRIVER_START, ride.isDriverAcceptStart());
                json.put(RideConstant.IS_DRIVER_END, ride.isDriverAcceptEnd());
                json.put(RideConstant.IS_PASSENGER_START, ride.isPassengerAcceptStart());
                json.put(RideConstant.IS_PASSENGER_END, ride.isPassengerAcceptEnd());

            } else {
                json.put(RideConstant.RIDE_NOT_FOUND, RideConstant.RIDE_NOT_FOUND);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}