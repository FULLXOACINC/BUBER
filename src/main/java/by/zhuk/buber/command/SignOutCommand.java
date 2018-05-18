package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;

import javax.servlet.http.HttpServletRequest;

public class SignOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.LOGIN);
        request.getSession().removeAttribute(UserConstant.TYPE);
        return new Router(TransitionType.REDIRECT, PagesConstant.SIGN_IN_PAGE);
    }
}