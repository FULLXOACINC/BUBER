package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.LoginReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.LoginValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LogInCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (!LoginValidator.isLoginValide(login)) {
            request.setAttribute("loginValidError", true);
            return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }
        LoginReceiver loginReceiver = new LoginReceiver();
        try {
            if (!loginReceiver.isLoginExist(login)) {
                request.setAttribute("loginExistError", true);
                return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
            }
            if (!loginReceiver.checkPassword(login, password)) {
                request.setAttribute("loginPasswordError", true);
                return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
            }
            UserReceiver userReceiver = new UserReceiver();
            Optional<User> userOptional =userReceiver.findUserByLogin(login);
            HttpSession session = request.getSession();
            if (!userOptional.isPresent()) {
                return new CommandResult(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
            }
            User user =userOptional.get();
            if (!loginReceiver.checkPassword(login, password)) {
                request.setAttribute("loginPasswordError", true);
                return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
            }
            session.setAttribute(UserConstant.LOGIN,user.getLogin());
            session.setAttribute(UserConstant.TYPE,user.getType().name());
        } catch (ReceiverException e) {
            //TODO go to error page
            return new CommandResult(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
        }
        return new CommandResult(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
    }
}