package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LangChangeCommand implements Command {
    private static final String LANG = "lang";
    private static final String LAST_PAGE_URL = "lastPageUrl";

    @Override
    public Router execute(HttpServletRequest request) {
        String lang = request.getParameter(LANG);
        HttpSession session = request.getSession();
        session.setAttribute(LANG, lang);
        String lastPageValue = (String) session.getAttribute(LAST_PAGE_URL);
        if (lastPageValue != null) {
            return new Router(TransitionType.REDIRECT, lastPageValue);
        } else {
            return new Router(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
        }

    }
}