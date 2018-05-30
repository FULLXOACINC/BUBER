package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.ComplaintReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class RefuseRideDiverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(RefuseRideDiverCommand.class);
    private static final String LANG = "lang";
    private static final String DEFAULT_DRIVER_REFUSE_COMPLAINT = "DEFAULT_DRIVER_REFUSE_COMPLAINT";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        String lang = (String) session.getAttribute(LANG);
        RideReceiver rideReceiver = new RideReceiver();
        UserReceiver userReceiver = new UserReceiver();
        ComplaintReceiver complaintReceiver = new ComplaintReceiver();
        try {
            Optional<Ride> rideOptional = rideReceiver.findCurrentDriverRide(login);

            if (rideOptional.isPresent()) {
                Driver driver = new Driver();
                driver.setLogin(login);
                Ride ride = rideOptional.get();
                Optional<User> optionalPassenger = userReceiver.findUserInfoForRide(ride.getPassenger().getLogin());
                User passenger = optionalPassenger.get();
                ride.setPassenger(passenger);
                ride.setDriver(driver);
                rideReceiver.driverRefuse(ride);
                session.setAttribute(UserConstant.COMPLAINT_DRIVER_TOKEN, UserConstant.COMPLAINT_DRIVER_TOKEN);
                complaintReceiver.createComplaint(login, ride.getRideId(), DEFAULT_DRIVER_REFUSE_COMPLAINT);
                json.put(ALL_CORRECT, ALL_CORRECT);
                rideReceiver.sendRefuseDriverMail(passenger.getLogin(), lang);

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
