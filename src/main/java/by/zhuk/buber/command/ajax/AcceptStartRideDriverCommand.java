package by.zhuk.buber.command.ajax;

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

public class AcceptStartRideDriverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(AcceptStartRideDriverCommand.class);

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
                rideReceiver.driverAcceptStart(ride.getRideId());
                json.put(ALL_CORRECT, ALL_CORRECT);
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