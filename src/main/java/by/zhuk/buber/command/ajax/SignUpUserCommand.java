package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.SignInReceiver;
import by.zhuk.buber.receiver.SignUpReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.SignInValidator;
import by.zhuk.buber.validator.SignUpUserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignUpUserCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(SignUpUserCommand.class);

    private static final String NOT_VALID_LOGIN_ERROR = "notValidLoginError";
    private static final String LOGIN_EXIST_ERROR = "loginExistError";
    private static final String PHONE_NUMBER_EXIST_ERROR = "phoneNumberExistError";
    private static final String FIRST_NAME_ERROR = "firstNameError";
    private static final String SECOND_NAME_ERROR = "secondNameError";
    private static final String NOT_VALID_PASSWORD_ERROR = "notValidPasswordError";
    private static final String PASSWORD_NOT_EQ_ERROR = "passwordNotEqError";
    private static final String BIRTH_DAY_ERROR = "birthDayError";
    private static final String NOT_VALID_PHONE_NUMBER_ERROR = "notValidPhoneNumberError";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String login = request.getParameter(UserConstant.LOGIN);
        String firstName = request.getParameter(UserConstant.FIRST_NAME);
        String secondName = request.getParameter(UserConstant.SECOND_NAME);
        String password = request.getParameter(UserConstant.PASSWORD);
        String repeatPassword = request.getParameter(UserConstant.REPEAT_PASSWORD);
        String birthDay = request.getParameter(UserConstant.BIRTH_DAY);
        String phoneNumber = request.getParameter(UserConstant.PHONE_NUMBER);

        HttpSession session = request.getSession();
        String lang = (String) session.getAttribute(UserConstant.LANG);

        UserReceiver userReceiver = new UserReceiver();
        SignInReceiver signInReceiver = new SignInReceiver();
        SignUpReceiver signUpReceiver = new SignUpReceiver();

        if (!SignInValidator.isLoginValid(login)) {
            json.put(NOT_VALID_LOGIN_ERROR, NOT_VALID_LOGIN_ERROR);
        }

        try {
            if (signInReceiver.isLoginExist(login)) {
                json.put(LOGIN_EXIST_ERROR, LOGIN_EXIST_ERROR);
            }
            if (userReceiver.isPhoneNumberExist(phoneNumber)) {
                json.put(PHONE_NUMBER_EXIST_ERROR, PHONE_NUMBER_EXIST_ERROR);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
            return json;
        }

        if (!SignUpUserValidator.isNameValid(firstName)) {
            json.put(FIRST_NAME_ERROR, FIRST_NAME_ERROR);
        }
        if (!SignUpUserValidator.isNameValid(secondName)) {
            json.put(SECOND_NAME_ERROR, SECOND_NAME_ERROR);
        }
        if (!SignUpUserValidator.isPasswordValid(password)) {
            json.put(NOT_VALID_PASSWORD_ERROR, NOT_VALID_PASSWORD_ERROR);
        }
        if (!password.equals(repeatPassword)) {
            json.put(PASSWORD_NOT_EQ_ERROR, PASSWORD_NOT_EQ_ERROR);
        }
        if (!SignUpUserValidator.isBirthDayValid(birthDay)) {
            json.put(BIRTH_DAY_ERROR, BIRTH_DAY_ERROR);
        }
        if (!SignUpUserValidator.isPhoneNumberValid(phoneNumber)) {
            json.put(NOT_VALID_PHONE_NUMBER_ERROR, NOT_VALID_PHONE_NUMBER_ERROR);
        }


        if (json.length() == 0) {
            signUpReceiver.sendAcceptMail(login, firstName, secondName, password, birthDay, phoneNumber, lang);
            json.put(ALL_CORRECT, ALL_CORRECT);
        }
        return json;
    }
}