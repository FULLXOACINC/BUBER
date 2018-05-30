package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.ComplaintReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ComplaintDriverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(ComplaintDriverCommand.class);
    private static final String INTEGER_REGEX = "\\d+";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String rideId = request.getParameter("rideId");
        String complaint = request.getParameter("complaint");
        String complaintDriverToken = (String) session.getAttribute(UserConstant.COMPLAINT_DRIVER_TOKEN);
        String login = (String) session.getAttribute(UserConstant.LOGIN);

        RideReceiver rideReceiver = new RideReceiver();
        ComplaintReceiver complaintReceiver = new ComplaintReceiver();
        if (complaintDriverToken == null) {
            json.put(ERROR, "notExistComplaintToken");
            return json;
        }
        if (rideId == null || !rideId.matches(INTEGER_REGEX)) {
            json.put(ERROR, ERROR);
            return json;
        }
        try {
            int intRideId = Integer.parseInt(rideId);
            if (complaintReceiver.isPassengerComplaintExist(intRideId, login)) {
                json.put("complaintExist", "complaintExist");
                return json;
            }
            Optional<String> passengerLoginOptional = rideReceiver.findPassengerLoginByRide(intRideId);
            if (passengerLoginOptional.isPresent()) {
                String passengerLogin = passengerLoginOptional.get();
                complaintReceiver.createComplaint(passengerLogin, intRideId, complaint);
                json.put(ALL_CORRECT, ALL_CORRECT);
                session.removeAttribute(UserConstant.COMPLAINT_TOKEN);
            } else {
                json.put("driverNotFound", "driverNotFound");
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;

    }
}
