package by.zhuk.buber.command;

import by.zhuk.buber.constant.OAuthConstant;
import by.zhuk.buber.constant.PagesConstant;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession().removeAttribute(OAuthConstant.ACCESS_TOKEN);
        return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
    }
}