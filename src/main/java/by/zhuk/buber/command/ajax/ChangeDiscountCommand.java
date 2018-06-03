package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
/**
 * Class include info how to react to a ajax ChangeDiscount
 * Admin command
 */
public class ChangeDiscountCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindUsersCommand.class);

    private static final String NOT_VALID_DISCOUNT = "notValidDiscount";
    private static final String USER_NOT_FOUND = "userNotFound";
    /**
     * Expected parameters:
     * 1)login
     * 2)discount
     */
    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String discount = request.getParameter(UserConstant.DISCOUNT);
        String login = request.getParameter(UserConstant.LOGIN);
        UserReceiver userReceiver = new UserReceiver();
        if (!UserValidator.isDiscountValid(discount)) {
            json.put(NOT_VALID_DISCOUNT, NOT_VALID_DISCOUNT);
            return json;
        }

        try {
            if (!userReceiver.isLoginExist(login)) {
                json.put(USER_NOT_FOUND, USER_NOT_FOUND);
                return json;
            }
            userReceiver.changeDiscount(new BigDecimal(discount), login);
            json.put(ALL_CORRECT, ALL_CORRECT);
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}