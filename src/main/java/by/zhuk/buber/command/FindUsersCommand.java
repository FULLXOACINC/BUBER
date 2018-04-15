package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindUsersCommand implements Command {
    private static Logger logger = LogManager.getLogger(FindUsersCommand.class);
    private static final String PATTERN = "pattern";
    private static final String USERS = "users";

    @Override
    public Router execute(HttpServletRequest request) {
        String findPattern = request.getParameter(PATTERN);
        UserReceiver receiver = new UserReceiver();
        List<User> users;
        try {
            users = receiver.findAllUser(findPattern);
        } catch (ReceiverException e) {
            //TODO error page
            logger.catching(e);
            return new Router(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);

        }
        request.setAttribute(USERS, users);

        return new Router(TransitionType.FORWARD, PagesConstant.ADMIN_PAGE);

    }
}