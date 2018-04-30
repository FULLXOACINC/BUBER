package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ViewUserCommand implements Command {
    private static Logger logger = LogManager.getLogger(ViewUserCommand.class);
    private static final String USER="user";
    private static final String NOT_FOUND="notFound";

    @Override
    public Router execute(HttpServletRequest request) {
        String user = request.getParameter(USER);
        UserReceiver userReceiver = new UserReceiver();
        try {
            Optional<User> optionalUser = userReceiver.findUserByLogin(user);
            if(optionalUser.isPresent()){
                request.setAttribute(USER,optionalUser.get());
            }else {
                request.setAttribute(NOT_FOUND,true);
            }
            return new Router(TransitionType.FORWARD, PagesConstant.USER_VIEW_PAGE);
        } catch (ReceiverException e) {
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
        }
    }
}