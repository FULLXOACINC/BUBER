package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;

import javax.servlet.http.HttpServletRequest;
/**
 * Class include info how to react to a SignOut
 */
public class SignOutCommand implements Command {
    /**
     * Expected parameters: -
     */
    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.LOGIN);
        request.getSession().removeAttribute(UserConstant.TYPE);
        request.getSession().removeAttribute(UserConstant.EVALUATION_TOKEN);
        request.getSession().removeAttribute(UserConstant.COMPLAINT_TOKEN);
        request.getSession().removeAttribute(UserConstant.COMPLAINT_DRIVER_TOKEN);
        return new Router(TransitionType.REDIRECT, PagesConstant.SIGN_IN_PAGE);
    }
}