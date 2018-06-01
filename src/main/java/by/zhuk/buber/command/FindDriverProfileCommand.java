package by.zhuk.buber.command;

import by.zhuk.buber.constant.DriverConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.receiver.DriverReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class FindDriverProfileCommand implements Command {
    private static Logger logger = LogManager.getLogger(FindDriverProfileCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        DriverReceiver driverReceiver = new DriverReceiver();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        try {

            Optional<Driver> optionalDriver = driverReceiver.findDriverByLogin(login);
            optionalDriver.ifPresent(driver -> request.setAttribute(DriverConstant.DRIVER, driver));
            return new Router(TransitionType.FORWARD, PagesConstant.DRIVER_PROFILE_VIEW_PAGE);
        } catch (ReceiverException e) {
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
        }
    }
}