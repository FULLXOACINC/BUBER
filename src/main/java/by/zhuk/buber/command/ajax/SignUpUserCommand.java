package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.SignInReceiver;
import by.zhuk.buber.receiver.SignUpReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignUpUserCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(SignUpUserCommand.class);

    private static final String NOT_VALID_LOGIN = "notValidLogin";
    private static final String LOGIN_EXIST = "loginExist";
    private static final String BIRTH_DAY_NOT_VALID = "birthDayNotValid";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String login = request.getParameter(UserConstant.LOGIN);
        String firstName = request.getParameter(UserConstant.FIRST_NAME);
        String lastName = request.getParameter(UserConstant.LAST_NAME);
        String password = request.getParameter(UserConstant.PASSWORD);
        String repeatPassword = request.getParameter(UserConstant.REPEAT_PASSWORD);
        String birthDay = request.getParameter(UserConstant.BIRTH_DAY);
        String phoneNumber = request.getParameter(UserConstant.PHONE_NUMBER);

        HttpSession session = request.getSession();
        String lang = (String) session.getAttribute(UserConstant.LANG);

        UserReceiver userReceiver = new UserReceiver();
        SignInReceiver signInReceiver = new SignInReceiver();
        SignUpReceiver signUpReceiver = new SignUpReceiver();

        if (!UserValidator.isLoginValid(login)) {
            json.put(NOT_VALID_LOGIN, NOT_VALID_LOGIN);
        }

        try {
            if (signInReceiver.isLoginExist(login)) {
                json.put(LOGIN_EXIST, LOGIN_EXIST);
            }
            if (userReceiver.isPhoneNumberExist(phoneNumber)) {
                json.put(UserConstant.PHONE_NUMBER_EXIST, UserConstant.PHONE_NUMBER_EXIST);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
            return json;
        }

        if (!UserValidator.isNameValid(firstName)) {
            json.put(UserConstant.FIRST_NAME_NOT_VALID, UserConstant.FIRST_NAME_NOT_VALID);
        }
        if (!UserValidator.isNameValid(lastName)) {
            json.put(UserConstant.LAST_NAME_NOT_VALID, UserConstant.LAST_NAME_NOT_VALID);
        }
        if (!UserValidator.isPasswordValid(password)) {
            json.put(UserConstant.NOT_VALID_PASSWORD, UserConstant.NOT_VALID_PASSWORD);
        }
        if (!password.equals(repeatPassword)) {
            json.put(UserConstant.PASSWORD_NOT_EQ, UserConstant.PASSWORD_NOT_EQ);
        }
        if (!UserValidator.isBirthDayValid(birthDay)) {
            json.put(BIRTH_DAY_NOT_VALID, BIRTH_DAY_NOT_VALID);
        }
        if (!UserValidator.isPhoneNumberValid(phoneNumber)) {
            json.put(UserConstant.PHONE_NUMBER_NOT_VALID, UserConstant.PHONE_NUMBER_NOT_VALID);
        }


        if (json.length() == 0) {
            signUpReceiver.sendAcceptMail(login, firstName, lastName, password, birthDay, phoneNumber, lang);
            json.put(ALL_CORRECT, ALL_CORRECT);
        }
        return json;
    }
}