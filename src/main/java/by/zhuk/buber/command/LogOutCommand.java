package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.LOGIN);
        request.getSession().removeAttribute(UserConstant.TYPE);
        return new CommandResult(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
    }
}