package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.SignInReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.SignInValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SignInCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignInCommand.class);
    private static String LOGIN="login";
    private static String PASSWORD="password";

    private static String SIGN_IN_VLIDE_ERROR="signInValidError";
    private static String SIGN_IN_EXIST_ERROR="signInExistError";
    private static String SIGN_IN_PASSWORD_ERROR="signInPasswordError";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        if (!SignInValidator.isLoginValid(login)) {
            request.setAttribute(SIGN_IN_VLIDE_ERROR, true);
            return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }
        SignInReceiver signInReceiver = new SignInReceiver();
        try {
            if (!signInReceiver.isLoginExist(login)) {
                request.setAttribute(SIGN_IN_EXIST_ERROR, true);
                return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
            }
            if (!signInReceiver.checkPassword(login, password)) {
                request.setAttribute(SIGN_IN_PASSWORD_ERROR, true);
                return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
            }
            UserReceiver userReceiver = new UserReceiver();
            Optional<User> userOptional =userReceiver.findUserByLogin(login);
            HttpSession session = request.getSession();
            if (!userOptional.isPresent()) {
                return new CommandResult(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
            }
            User user =userOptional.get();
            session.setAttribute(UserConstant.LOGIN,user.getLogin());
            session.setAttribute(UserConstant.TYPE,user.getType().name());
        } catch (ReceiverException e) {
            //TODO go to error page
            logger.catching(e);
            return new CommandResult(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
        }
        return new CommandResult(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
    }
}