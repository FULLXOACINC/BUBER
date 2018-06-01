package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.DriverConstant;
import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DriverSetWorkingStatusCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(SignUpDriverCommand.class);


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        DriverReceiver driverReceiver = new DriverReceiver();

        try {
            if (!driverReceiver.isDriverExist(login)) {
                json.put(DriverConstant.DRIVER_NOT_EXIST, DriverConstant.DRIVER_NOT_EXIST);
            }
            if (json.length() == 0) {
                driverReceiver.updateDriverWorkingStatus(login, true);
                json.put(ALL_CORRECT, ALL_CORRECT);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }

        return json;
    }
}