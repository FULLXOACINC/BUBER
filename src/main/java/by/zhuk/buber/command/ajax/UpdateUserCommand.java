package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
/**
 * Class include info how to react to a ajax UpdateUser
 */
public class UpdateUserCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(UpdateUserCommand.class);
    /**
     * Expected parameters:
     * 1)firstName
     * 2)lastName
     * 3)phoneNumber
     */
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
                json.put(UserConstant.PHONE_NUMBER_EXIST, UserConstant.PHONE_NUMBER_EXIST);
            }


            if (!UserValidator.isNameValid(firstName)) {
                json.put(UserConstant.FIRST_NAME_NOT_VALID, UserConstant.FIRST_NAME_NOT_VALID);
            }
            if (!UserValidator.isNameValid(lastName)) {
                json.put(UserConstant.LAST_NAME_NOT_VALID, UserConstant.LAST_NAME_NOT_VALID);
            }
            if (!UserValidator.isPhoneNumberValid(phoneNumber)) {
                json.put(UserConstant.PHONE_NUMBER_NOT_VALID, UserConstant.PHONE_NUMBER_NOT_VALID);
            }

            if (json.length() == 0) {
                userReceiver.updateUser(login, firstName, lastName, phoneNumber);
                json.put(ALL_CORRECT, ALL_CORRECT);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
            return json;
        }
        return json;
    }
}