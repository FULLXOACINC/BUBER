package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.SignInReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.SignInValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SignInCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(SignInCommand.class);

    private static final String LOGIN_NOT_VALID = "loginNotValid";
    private static final String LOGIN_NOT_EXIST = "loginNotExist";
    private static final String PASSWORD_NOT_MATCH_LOGIN = "passwordNotMatchLogin";
    private static final String REDIRECT_PAGE = "redirectPage";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        String login = request.getParameter(UserConstant.LOGIN);
        String password = request.getParameter(UserConstant.PASSWORD);

        JSONObject json = new JSONObject();
        if (!SignInValidator.isLoginValid(login)) {
            json.put(LOGIN_NOT_VALID, LOGIN_NOT_VALID);
            return json;
        }
        SignInReceiver signInReceiver = new SignInReceiver();
        try {
            if (!signInReceiver.isLoginExist(login)) {
                json.put(LOGIN_NOT_EXIST, LOGIN_NOT_EXIST);
                return json;
            }
            if (!signInReceiver.checkPassword(login, password)) {
                json.put(PASSWORD_NOT_MATCH_LOGIN, PASSWORD_NOT_MATCH_LOGIN);
                return json;
            }
            UserReceiver userReceiver = new UserReceiver();
            Optional<User> userOptional = userReceiver.findUserByLogin(login);
            HttpSession session = request.getSession();
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                session.setAttribute(UserConstant.LOGIN, user.getLogin());
                session.setAttribute(UserConstant.TYPE, user.getType().name());
                json.put(REDIRECT_PAGE, PagesConstant.WELCOME_PAGE);

            }


        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}