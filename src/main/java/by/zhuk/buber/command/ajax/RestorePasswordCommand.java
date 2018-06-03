package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.RestorePasswordReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class include info how to react to a ajax RestorePassword
 */
public class RestorePasswordCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(RestorePasswordCommand.class);


    /**
     * Expected parameters:
     * 1)login
     * 2)password
     * 3)repeatPassword
     */
    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String login = request.getParameter(UserConstant.LOGIN);
        String password = request.getParameter(UserConstant.PASSWORD);
        String repeatPassword = request.getParameter(UserConstant.REPEAT_PASSWORD);
        HttpSession session = request.getSession();
        String lang = (String) session.getAttribute(UserConstant.LANG);

        UserReceiver userReceiver = new UserReceiver();
        RestorePasswordReceiver restorePasswordReceiver = new RestorePasswordReceiver();

        if (!UserValidator.isLoginValid(login)) {
            json.put(UserConstant.NOT_VALID_LOGIN, UserConstant.NOT_VALID_LOGIN);
        }

        try {
            if (!userReceiver.isLoginExist(login)) {
                json.put(UserConstant.LOGIN_NOT_EXIST, UserConstant.LOGIN_NOT_EXIST);
            }

        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
            return json;
        }

        if (!UserValidator.isPasswordValid(password)) {
            json.put(UserConstant.NOT_VALID_PASSWORD, UserConstant.NOT_VALID_PASSWORD);
        }
        if (!password.equals(repeatPassword)) {
            json.put(UserConstant.PASSWORD_NOT_EQ, UserConstant.PASSWORD_NOT_EQ);
        }

        if (json.length() == 0) {
            restorePasswordReceiver.sendRestorePasswordMail(login, password, lang);
            json.put(ALL_CORRECT, ALL_CORRECT);
        }
        return json;
    }
}