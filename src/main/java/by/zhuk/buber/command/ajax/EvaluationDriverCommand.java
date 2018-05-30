package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class EvaluationDriverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(EvaluationDriverCommand.class);
    private static final String INTEGER_REGEX = "\\d+";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String rideId = request.getParameter("rideId");
        String evaluationType = request.getParameter("evaluationType");
        String evaluationToken = (String) session.getAttribute(UserConstant.EVALUATION_TOKEN);
        DriverReceiver driverReceiver = new DriverReceiver();
        RideReceiver rideReceiver = new RideReceiver();
        if (evaluationToken == null) {
            json.put("notExistEvaluationToken", "notExistEvaluationToken");
            return json;
        }
        if (rideId == null || !rideId.matches(INTEGER_REGEX)) {
            json.put(ERROR, ERROR);
            return json;
        }
        try {

            Optional<String> driverLoginOptional = rideReceiver.findDriverLoginByRide(Integer.parseInt(rideId));
            if (driverLoginOptional.isPresent()) {
                String driver = driverLoginOptional.get();
                if ("positive".equals(evaluationType)) {
                    driverReceiver.incrementPositiveMark(driver);
                } else {
                    driverReceiver.incrementNegativeMark(driver);
                }
                json.put(ALL_CORRECT, ALL_CORRECT);
                session.removeAttribute(UserConstant.EVALUATION_TOKEN);
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
