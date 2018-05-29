package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DriverSetNotWorkingStatusCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(SignUpDriverCommand.class);
    private static final String DRIVER_NOT_EXIST_ERROR = "driverNotExistError";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        DriverReceiver driverReceiver = new DriverReceiver();

        try {
            if (!driverReceiver.isDriverExist(login)) {
                json.put(DRIVER_NOT_EXIST_ERROR, DRIVER_NOT_EXIST_ERROR);
            }
            if (json.length() == 0) {
                driverReceiver.updateDriverWorkingStatus(login, false);
                json.put(ALL_CORRECT, ALL_CORRECT);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, e.getMessage());
        }

        return json;
    }
}