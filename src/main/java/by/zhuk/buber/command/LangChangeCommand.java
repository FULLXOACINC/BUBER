package by.zhuk.buber.command;

import by.zhuk.buber.constant.UserConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * Class include info how to react to a LangChange
 */
public class LangChangeCommand implements Command {
    /**
     * Expected parameters: -
     */
    @Override
    public Router execute(HttpServletRequest request) {
        String lang = request.getParameter(UserConstant.LANG);
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.LANG, lang);
        String lastPage = (String) session.getAttribute(UserConstant.LAST_PAGE);
        return new Router(TransitionType.REDIRECT, lastPage);
    }
}