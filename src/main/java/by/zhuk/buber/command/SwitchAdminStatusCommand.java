package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SwitchAdminStatusCommand implements Command {
    private static Logger logger = LogManager.getLogger(SwitchAdminStatusCommand.class);
    private static final String PART_USER_FIND_URL = "?command=find-user&user=";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userLogin = request.getParameter(UserConstant.USER);
        String adminLogin = (String) session.getAttribute(UserConstant.LOGIN);
        if (adminLogin.equals(userLogin)) {
            logger.log(Level.INFO, "Can't do this operation on yourself ,Admin:" + adminLogin);
            return new Router(TransitionType.REDIRECT, request.getRequestURI() + PART_USER_FIND_URL + userLogin);
        }

        UserReceiver userReceiver = new UserReceiver();
        DriverReceiver driverReceiver = new DriverReceiver();
        try {

            Optional<User> userOptional = userReceiver.findUserByLogin(userLogin);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                boolean isExDriver = driverReceiver.isDriverExist(userLogin);
                userReceiver.switchAdminStatus(userLogin, user.getType(), isExDriver);
                logger.log(Level.INFO, "Admin:" + adminLogin + " switch admin status " + userLogin);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
        }
        return new Router(TransitionType.REDIRECT, request.getRequestURI() + PART_USER_FIND_URL + userLogin);
    }
}