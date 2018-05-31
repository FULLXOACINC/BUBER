package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.validator.FillUpBalanceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class WithdrawEarningDriverMoneyCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(WithdrawEarningDriverMoneyCommand.class);
    private static final String CARD_NUMBER = "cardNumber";
    private static final String NO_EARNED_MONEY = "noEarnedMoney";
    private static final String CARD_NUMBER_NOT_VALID = "cardNumberNotValid";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String cardNumber = request.getParameter(CARD_NUMBER);
        String login = (String) request.getSession().getAttribute(UserConstant.LOGIN);
        if (!FillUpBalanceValidator.isCardNumberValid(cardNumber)) {
            json.put(CARD_NUMBER_NOT_VALID, CARD_NUMBER_NOT_VALID);
            return json;
        }
        try {
            DriverReceiver driverReceiver = new DriverReceiver();
            Optional<BigDecimal> earnedMoneyOptional = driverReceiver.findDriverEarnedMoney(login);

            if (earnedMoneyOptional.isPresent()) {
                BigDecimal earnedMoney = earnedMoneyOptional.get();
                if (earnedMoney.signum() != 1) {
                    json.put(NO_EARNED_MONEY, NO_EARNED_MONEY);
                }
            }
            if (json.length() == 0) {
                driverReceiver.withdrawEarningMoney(login);
                json.put(ALL_CORRECT, ALL_CORRECT);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;


    }
}