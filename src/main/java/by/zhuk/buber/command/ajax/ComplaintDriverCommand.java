package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ComplaintConstant;
import by.zhuk.buber.constant.DriverConstant;
import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.RideConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.ComplaintReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import by.zhuk.buber.validator.IntegerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ComplaintDriverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(ComplaintDriverCommand.class);

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String rideId = request.getParameter(RideConstant.RIDE_ID);
        String complaint = request.getParameter(ComplaintConstant.COMPLAINT);
        String complaintDriverToken = (String) session.getAttribute(UserConstant.COMPLAINT_DRIVER_TOKEN);
        String login = (String) session.getAttribute(UserConstant.LOGIN);

        RideReceiver rideReceiver = new RideReceiver();
        ComplaintReceiver complaintReceiver = new ComplaintReceiver();
        if (complaintDriverToken == null) {
            json.put(ComplaintConstant.NOT_EXIST_COMPLAINT_TOKEN, ComplaintConstant.NOT_EXIST_COMPLAINT_TOKEN);
            return json;
        }
        if (!IntegerValidator.isInteger(rideId)) {
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
            return json;
        }
        try {
            int intRideId = Integer.parseInt(rideId);
            if (complaintReceiver.isPersonComplaintExist(intRideId, login)) {
                json.put(ComplaintConstant.COMPLAINT_EXIST, ComplaintConstant.COMPLAINT_EXIST);
                return json;
            }
            Optional<String> passengerLoginOptional = rideReceiver.findPassengerLoginByRide(intRideId);
            if (passengerLoginOptional.isPresent()) {
                String passengerLogin = passengerLoginOptional.get();
                complaintReceiver.createComplaint(passengerLogin, intRideId, complaint);
                session.removeAttribute(UserConstant.COMPLAINT_TOKEN);
                json.put(ALL_CORRECT, ALL_CORRECT);
            } else {
                json.put(DriverConstant.DRIVER_NOT_FOUND, DriverConstant.DRIVER_NOT_FOUND);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;

    }
}