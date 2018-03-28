package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.receiver.LoginReceiver;
import by.zhuk.buber.validator.LoginValidator;

import javax.servlet.http.HttpServletRequest;

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
        if (!loginReceiver.isLoginExist(login)) {
            request.setAttribute("loginExistError", true);
            return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }
        if (!loginReceiver.isLoginExist(login)) {
            request.setAttribute("loginExistError", true);
            return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }
        if (!loginReceiver.checkPassword(login,password)) {
            request.setAttribute("loginExistError", true);
            return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }
        return null;
    }
}