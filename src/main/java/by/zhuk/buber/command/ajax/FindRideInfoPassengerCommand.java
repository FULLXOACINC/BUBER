package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.DriverConstant;
import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.GeoConstant;
import by.zhuk.buber.constant.RideConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class FindRideInfoPassengerCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindRideInfoPassengerCommand.class);


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        RideReceiver rideReceiver = new RideReceiver();
        DriverReceiver driverReceiver = new DriverReceiver();
        try {

            Optional<Ride> rideOptional = rideReceiver.findCurrentUserRide(login);
            if (rideOptional.isPresent()) {
                Ride ride = rideOptional.get();
                Optional<Driver> optionalDriver = driverReceiver.findDriverInfoForRide(ride.getDriver().getLogin());
                Driver driver = optionalDriver.get();
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
                json.put(UserConstant.FIRST_NAME, driver.getFirstName());
                json.put(UserConstant.LAST_NAME, driver.getLastName());
                json.put(DriverConstant.CAR_NUMBER, driver.getCarNumber());
                json.put(DriverConstant.CAR_MARK, driver.getCarMark().getMarkName());
                json.put(UserConstant.PHONE_NUMBER, driver.getPhoneNumber());
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
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}