package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class FindDriverEarnedMoneyCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindDriverEarnedMoneyCommand.class);
    private static final String EARNED_MONEY = "earnedMoney";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        DriverReceiver driverReceiver = new DriverReceiver();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        try {
            Optional<BigDecimal> optionalEarnedMoney = driverReceiver.findDriverEarnedMoney(login);
            optionalEarnedMoney.ifPresent(earnedMoney -> {
                json.put(EARNED_MONEY, earnedMoney);
                json.put(ALL_CORRECT, ALL_CORRECT);
            });

        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}