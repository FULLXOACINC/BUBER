package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.AdminValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindUsersCommand implements Command {
    private static Logger logger = LogManager.getLogger(FindUsersCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (!AdminValidator.isAdmin(session)) {
            return new CommandResult(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
        }
        String findPattern = request.getParameter("pattern");
        UserReceiver receiver = new UserReceiver();
        List<User> users;
        try {
            users = receiver.findAllUser(findPattern);
        } catch (ReceiverException e) {
            //TODO error page
            logger.catching(e);
            return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);

        }
        request.setAttribute("users", users);

        return new CommandResult(TransitionType.FORWARD, PagesConstant.ADMIN_PAGE);

    }
}