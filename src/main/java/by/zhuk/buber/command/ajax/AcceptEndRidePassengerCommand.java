package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AcceptEndRidePassengerCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(AcceptEndRidePassengerCommand.class);

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
                User user = new User();
                user.setLogin(login);
                Ride ride = rideOptional.get();
                Optional<Driver> optionalDriver = driverReceiver.findDriverInfoForRide(ride.getDriver().getLogin());
                Driver driver = optionalDriver.get();
                ride.setPassenger(user);
                ride.setDriver(driver);
                if (ride.isDriverAcceptEnd()) {
                    rideReceiver.passengerAcceptEnd(ride);
                    session.setAttribute(UserConstant.EVALUATION_TOKEN, UserConstant.EVALUATION_TOKEN);
                    session.setAttribute(UserConstant.COMPLAINT_TOKEN, UserConstant.COMPLAINT_TOKEN);
                    json.put(ALL_CORRECT, ALL_CORRECT);
                    json.put("rideId", ride.getRideId());
                } else {
                    json.put("driverNotAccept", "driverNotAccept");
                }
            } else {
                json.put("notFoundRide", "notFoundRide");
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;

    }
}