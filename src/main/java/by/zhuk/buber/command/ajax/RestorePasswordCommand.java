package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.RestorePasswordReceiver;
import by.zhuk.buber.receiver.SignInReceiver;
import by.zhuk.buber.validator.SignInValidator;
import by.zhuk.buber.validator.SignUpUserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RestorePasswordCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(RestorePasswordCommand.class);

    private static final String NOT_VALID_LOGIN = "notValidLogin";
    private static final String LOGIN_NOT_EXIST = "loginNotExist";
    private static final String NOT_VALID_PASSWORD = "notValidPassword";
    private static final String PASSWORD_NOT_EQ = "passwordNotEq";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String login = request.getParameter(UserConstant.LOGIN);
        String password = request.getParameter(UserConstant.PASSWORD);
        String repeatPassword = request.getParameter(UserConstant.REPEAT_PASSWORD);
        HttpSession session = request.getSession();
        String lang = (String) session.getAttribute(UserConstant.LANG);

        SignInReceiver signInReceiver = new SignInReceiver();
        RestorePasswordReceiver restorePasswordReceiver = new RestorePasswordReceiver();

        if (!SignInValidator.isLoginValid(login)) {
            json.put(NOT_VALID_LOGIN, NOT_VALID_LOGIN);
        }

        try {
            if (!signInReceiver.isLoginExist(login)) {
                json.put(LOGIN_NOT_EXIST, LOGIN_NOT_EXIST);
            }

        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
            return json;
        }

        if (!SignUpUserValidator.isPasswordValid(password)) {
            json.put(NOT_VALID_PASSWORD, NOT_VALID_PASSWORD);
        }
        if (!password.equals(repeatPassword)) {
            json.put(PASSWORD_NOT_EQ, PASSWORD_NOT_EQ);
        }

        if (json.length() == 0) {
            restorePasswordReceiver.sendRestorePasswordMail(login, password, lang);
            json.put(ALL_CORRECT, ALL_CORRECT);
        }
        return json;
    }
}