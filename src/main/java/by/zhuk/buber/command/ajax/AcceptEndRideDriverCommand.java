package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.RideConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.receiver.RideReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
/**
 * Class include info how to react to a ajax AcceptEndRideDriver
 * Driver command
 */
public class AcceptEndRideDriverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(AcceptEndRideDriverCommand.class);
    /**
     * Expected parameters:-
     */
    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        RideReceiver rideReceiver = new RideReceiver();
        try {
            Optional<Ride> rideOptional = rideReceiver.findCurrentDriverRide(login);
            if (rideOptional.isPresent()) {
                Ride ride = rideOptional.get();
                if (ride.isPassengerAcceptStart()) {
                    rideReceiver.driverAcceptEnd(ride.getRideId());
                    json.put(ALL_CORRECT, ALL_CORRECT);
                    session.setAttribute(UserConstant.COMPLAINT_DRIVER_TOKEN, UserConstant.COMPLAINT_DRIVER_TOKEN);
                } else {
                    json.put(RideConstant.PASSENGER_NOT_ACCEPT, RideConstant.PASSENGER_NOT_ACCEPT);
                }
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