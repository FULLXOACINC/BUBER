package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.FillUpBalanceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class FillUpBalanceCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FillUpBalanceCommand.class);
    private static final String Money_AMOUNT = "moneyAmount";

    private static final String FULL_BALANCE = "fullBalance";
    private static final String OUT_OF_BOUND_BALANCE= "outOfBoundBalance";
    private static final String UNKWON_MONEY_FORMAT = "unknownMoneyFormat";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String moneyAmount = request.getParameter(Money_AMOUNT);
        String login = (String) request.getSession().getAttribute(UserConstant.LOGIN);
        if (!FillUpBalanceValidator.isMoneyFormatValid(moneyAmount)) {
            json.put(UNKWON_MONEY_FORMAT,UNKWON_MONEY_FORMAT);
            return json;
        }
        if (!FillUpBalanceValidator.isMoneyAmountInBoundValid(moneyAmount)) {
            json.put(OUT_OF_BOUND_BALANCE,OUT_OF_BOUND_BALANCE);
            return json;
        }
        try {
            UserReceiver userReceiver = new UserReceiver();
            if (!userReceiver.isUserBalanceFull(login, moneyAmount)) {
                json.put(FULL_BALANCE,FULL_BALANCE);
                return json;
            }
            if (json.length() == 0) {
                userReceiver.fillUpBalance(login, moneyAmount);
                json.put(ALL_CORRECT, ALL_CORRECT);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR,ERROR);
        }
        return json;


    }
}