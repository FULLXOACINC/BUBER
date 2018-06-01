package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.AdminReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.AdminValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class ChangeDiscountCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindUsersCommand.class);

    private static final String NOT_VALID_DISCOUNT = "notValidDiscount";
    private static final String USER_NOT_FOUND = "userNotFound";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String discount = request.getParameter(UserConstant.DISCOUNT);
        String login = request.getParameter(UserConstant.LOGIN);
        if (!AdminValidator.isDiscountValid(discount)) {
            json.put(NOT_VALID_DISCOUNT, NOT_VALID_DISCOUNT);
            return json;
        }
        AdminReceiver adminReceiver = new AdminReceiver();
        UserReceiver userReceiver = new UserReceiver();
        try {
            if (!userReceiver.isUserExist(login)) {
                json.put(USER_NOT_FOUND, USER_NOT_FOUND);
                return json;
            }
            adminReceiver.changeDiscount(Float.parseFloat(discount), login);
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}