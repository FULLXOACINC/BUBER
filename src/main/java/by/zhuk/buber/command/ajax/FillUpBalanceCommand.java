package by.zhuk.buber.command.ajax;

import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class FillUpBalanceCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FillUpBalanceCommand.class);
    private static final String AMOUNT = "amount";
    private static final String USERS = "users";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        return json;


    }
}