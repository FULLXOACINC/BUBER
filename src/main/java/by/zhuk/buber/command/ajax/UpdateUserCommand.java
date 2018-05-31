package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.SignUpUserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UpdateUserCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(UpdateUserCommand.class);

    private static final String PHONE_NUMBER_EXIST = "phoneNumberExist";
    private static final String FIRST_NAME_NOT_VALID = "firstNameNotValid";
    private static final String LAST_NAME_NOT_VALID = "lastNameNotValid";
    private static final String PHONE_NUMBER_NOT_VALID = "phoneNumberNotValid";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();

        String firstName = request.getParameter(UserConstant.FIRST_NAME);
        String lastName = request.getParameter(UserConstant.LAST_NAME);
        String phoneNumber = request.getParameter(UserConstant.PHONE_NUMBER);

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        UserReceiver userReceiver = new UserReceiver();


        try {

            Optional<User> userOptional = userReceiver.findUserByLogin(login);
            User user = userOptional.get();
            if (userReceiver.isPhoneNumberExist(phoneNumber) && !user.getPhoneNumber().equals(phoneNumber)) {
                json.put(PHONE_NUMBER_EXIST, PHONE_NUMBER_EXIST);
            }


            if (!SignUpUserValidator.isNameValid(firstName)) {
                json.put(FIRST_NAME_NOT_VALID, FIRST_NAME_NOT_VALID);
            }
            if (!SignUpUserValidator.isNameValid(lastName)) {
                json.put(LAST_NAME_NOT_VALID, LAST_NAME_NOT_VALID);
            }
            if (!SignUpUserValidator.isPhoneNumberValid(phoneNumber)) {
                json.put(PHONE_NUMBER_NOT_VALID, PHONE_NUMBER_NOT_VALID);
            }

            if (json.length() == 0) {
                userReceiver.updateUser(login, firstName, lastName, phoneNumber);
                json.put(ALL_CORRECT, ALL_CORRECT);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
            return json;
        }
        return json;
    }
}