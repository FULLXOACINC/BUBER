package by.zhuk.buber.command;

import by.zhuk.buber.constant.DriverConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.receiver.DriverReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class FindDriverUpdateCommand implements Command {
    private static Logger logger = LogManager.getLogger(FindUserCommand.class);


    @Override
    public Router execute(HttpServletRequest request) {
        String login = request.getParameter(DriverConstant.DRIVER);
        DriverReceiver driverReceiver = new DriverReceiver();
        try {
            Optional<Driver> optionalUser = driverReceiver.findDriverByLogin(login);
            optionalUser.ifPresent(driver -> request.setAttribute(DriverConstant.DRIVER, driver));
            return new Router(TransitionType.FORWARD, PagesConstant.UPDATE_DRIVER_PAGE);
        } catch (ReceiverException e) {
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
        }
    }
}