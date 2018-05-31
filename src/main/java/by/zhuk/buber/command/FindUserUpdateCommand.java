package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class FindUserUpdateCommand implements Command {
    private static Logger logger = LogManager.getLogger(ViewUserCommand.class);


    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String)session.getAttribute(UserConstant.LOGIN);
        UserReceiver userReceiver = new UserReceiver();
        try {
            Optional<User> optionalUser = userReceiver.findUserByLogin(login);
            optionalUser.ifPresent(user -> request.setAttribute(UserConstant.USER, user));
            return new Router(TransitionType.FORWARD, PagesConstant.UPDATE_USER_PAGE);
        } catch (ReceiverException e) {
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
        }
    }
}