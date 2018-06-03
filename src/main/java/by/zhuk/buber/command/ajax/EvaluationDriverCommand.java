package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.DriverConstant;
import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.EvaluationConstant;
import by.zhuk.buber.constant.RideConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import by.zhuk.buber.validator.IntegerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
/**
 * Class include info how to react to a ajax EvaluationDriver
 */
public class EvaluationDriverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(EvaluationDriverCommand.class);
    private static final String POSITIVE = "positive";
    /**
     * Expected parameters:
     * 1)rideId
     * 2)evaluationType
     */
    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String rideId = request.getParameter(RideConstant.RIDE_ID);
        String evaluationType = request.getParameter(EvaluationConstant.EVALUATION_TYPE);
        String evaluationToken = (String) session.getAttribute(UserConstant.EVALUATION_TOKEN);
        DriverReceiver driverReceiver = new DriverReceiver();
        RideReceiver rideReceiver = new RideReceiver();
        if (evaluationToken == null) {
            json.put(EvaluationConstant.NOT_EXIST_EVALUATION_TOKEN, EvaluationConstant.NOT_EXIST_EVALUATION_TOKEN);
            return json;
        }
        if (!IntegerValidator.isUnsignedInteger(rideId)) {
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
            return json;
        }
        try {
            Optional<String> driverLoginOptional = rideReceiver.findDriverLoginByRide(Integer.parseInt(rideId));
            if (driverLoginOptional.isPresent()) {
                String driver = driverLoginOptional.get();
                if (POSITIVE.equals(evaluationType)) {
                    driverReceiver.incrementPositiveMark(driver);
                } else {
                    driverReceiver.incrementNegativeMark(driver);
                }
                json.put(ALL_CORRECT, ALL_CORRECT);
                session.removeAttribute(UserConstant.EVALUATION_TOKEN);
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
