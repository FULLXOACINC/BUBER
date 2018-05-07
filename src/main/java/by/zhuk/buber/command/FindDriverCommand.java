package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class FindDriverCommand implements Command {
    private static Logger logger = LogManager.getLogger(ViewUserCommand.class);
    private static final String DRIVER = "driver";
    private static final String NOT_FOUND = "notFound";

    @Override
    public Router execute(HttpServletRequest request) {
        String login = request.getParameter(DRIVER);
        DriverReceiver driverReceiver = new DriverReceiver();
        try {
            Optional<Driver> optionalUser = driverReceiver.findDriverByLogin(login);
            if (optionalUser.isPresent()) {
                request.setAttribute(DRIVER, optionalUser.get());
            } else {
                request.setAttribute(NOT_FOUND, true);
            }
            return new Router(TransitionType.FORWARD, PagesConstant.USER_VIEW_PAGE);
        } catch (ReceiverException e) {
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
        }
    }
}