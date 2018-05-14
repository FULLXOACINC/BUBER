package by.zhuk.buber.command.ajax;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.AdminReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindUsersCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindUsersCommand.class);
    private static final String PATTERN = "pattern";
    private static final String USERS = "users";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String findPattern = request.getParameter(PATTERN);
        AdminReceiver receiver = new AdminReceiver();
        List<User> users;
        try {
            users = receiver.findUsersByPattern(findPattern);
            json.put(USERS, users);
            return json;
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, e.getMessage());
            return json;

        }


    }
}