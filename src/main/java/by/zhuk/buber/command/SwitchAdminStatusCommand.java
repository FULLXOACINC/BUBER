package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.AdminReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SwitchAdminStatusCommand implements Command {
    private static Logger logger = LogManager.getLogger(SwitchAdminStatusCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userLogin = request.getParameter(UserConstant.USER);
        String adminLogin = (String) session.getAttribute(UserConstant.LOGIN);
        if (adminLogin.equals(userLogin)) {
            logger.log(Level.INFO, "Can't do this operation on yourself ,Admin:" + adminLogin);
        }

        UserReceiver userReceiver = new UserReceiver();
        AdminReceiver adminReceiver = new AdminReceiver();
        try {

            Optional<User> userOptional = userReceiver.findUserByLogin(userLogin);
            if (userOptional.isPresent()) {
                adminReceiver.switchAdminStatus(userOptional.get());
                logger.log(Level.INFO, "Admin:" + adminLogin + " switch admin status " + userLogin);
            }
        } catch (ReceiverException e) {
            //TODO error page
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ADMIN_PAGE);
        }


        return new Router(TransitionType.REDIRECT, PagesConstant.ADMIN_PAGE);
    }
}