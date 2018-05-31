package by.zhuk.buber.command.ajax;

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

public class UpdateUserCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(UpdateUserCommand.class);
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE_NUMBER = "phoneNumber";



    private static final String PHONE_NUMBER_EXIST_ERROR = "phoneNumberExistError";
    private static final String FIRST_NAME_ERROR = "firstNameError";
    private static final String SECOND_NAME_ERROR = "secondNameError";;
    private static final String NOT_VALID_PHONE_NUMBER_ERROR = "notValidPhoneNumberError";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();

        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);

        HttpSession session = request.getSession();
        String login = (String)session.getAttribute(UserConstant.LOGIN);
        UserReceiver userReceiver = new UserReceiver();


        try {
            if (userReceiver.isPhoneNumberExist(phoneNumber)) {
                json.put(PHONE_NUMBER_EXIST_ERROR, PHONE_NUMBER_EXIST_ERROR);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, e.getMessage());
            return json;
        }

        if (!SignUpUserValidator.isNameValid(firstName)) {
            json.put(FIRST_NAME_ERROR, FIRST_NAME_ERROR);
        }
        if (!SignUpUserValidator.isNameValid(lastName)) {
            json.put(SECOND_NAME_ERROR, SECOND_NAME_ERROR);
        }
        if (!SignUpUserValidator.isPhoneNumberValid(phoneNumber)) {
            json.put(NOT_VALID_PHONE_NUMBER_ERROR, NOT_VALID_PHONE_NUMBER_ERROR);
        }


        if (json.length() == 0) {
            userReceiver.updateUser(login,firstName,lastName,phoneNumber);
            json.put(ALL_CORRECT, ALL_CORRECT);
        }
        return json;
    }
}