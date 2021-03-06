package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
/**
 * Class include info how to react to a FindUserProfile
 */
public class FindUserProfileCommand implements Command {
    private static Logger logger = LogManager.getLogger(FindUserProfileCommand.class);
    /**
     * Expected parameters: -
     */
    @Override
    public Router execute(HttpServletRequest request) {
        UserReceiver userReceiver = new UserReceiver();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        try {
            Optional<User> optionalUser = userReceiver.findUserByLogin(login);
            optionalUser.ifPresent(user -> request.setAttribute(UserConstant.USER, user));
            return new Router(TransitionType.FORWARD, PagesConstant.USER_PROFILE_VIEW_PAGE);
        } catch (ReceiverException e) {
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
        }
    }
}